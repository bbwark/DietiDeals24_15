package com.CioffiDeVivo.dietideals.presentation.ui.createAuction

import android.app.Application
import android.content.Context
import android.net.Uri
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.CioffiDeVivo.dietideals.services.ApiService
import com.CioffiDeVivo.dietideals.domain.models.AuctionCategory
import com.CioffiDeVivo.dietideals.domain.models.AuctionType
import com.CioffiDeVivo.dietideals.domain.mappers.toRequestModel
import com.CioffiDeVivo.dietideals.domain.validations.ValidateCreateAuctionForm
import com.CioffiDeVivo.dietideals.domain.validations.ValidationState
import io.ktor.client.statement.bodyAsText
import io.ktor.http.isSuccess
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import java.io.File
import java.time.Instant
import java.time.ZoneId

class CreateAuctionViewModel(
    application: Application,
    private val validateCreateAuctionForm: ValidateCreateAuctionForm = ValidateCreateAuctionForm()
): AndroidViewModel(application) {

    private val _createAuctionUiState = MutableStateFlow<CreateAuctionUiState>(CreateAuctionUiState.CreateAuctionParams())
    val createAuctionUiState: StateFlow<CreateAuctionUiState> = _createAuctionUiState.asStateFlow()
    private val validationEventChannel = Channel<ValidationState>()
    val validationCreateAuctionEvent = validationEventChannel.receiveAsFlow()

    fun createAuctionOnAction(createAuctionEvents: CreateAuctionEvents){
        when(createAuctionEvents){
            is CreateAuctionEvents.ItemNameChanged -> {
                updateItemName(createAuctionEvents.itemName)
            }
            is CreateAuctionEvents.ItemNameDeleted -> {
                deleteItemName()
            }
            is CreateAuctionEvents.ImagesChanged -> {
                updateImagesUri(createAuctionEvents.image)
            }
            is CreateAuctionEvents.ImagesDeleted -> {
                deleteImageUri(createAuctionEvents.index)
            }
            is CreateAuctionEvents.AuctionTypeChanged -> {
                updateAuctionType(createAuctionEvents.auctionType)
            }
            is CreateAuctionEvents.AuctionCategoryChanged -> {
                updateAuctionCategory(createAuctionEvents.auctionCategory)
            }
            is CreateAuctionEvents.IntervalChanged -> {
                updateInterval(createAuctionEvents.interval)
            }
            is CreateAuctionEvents.IntervalDeleted -> {
                deleteInterval()
            }
            is CreateAuctionEvents.MinStepChanged -> {
                updateMinStep(createAuctionEvents.minStep)
            }
            is CreateAuctionEvents.EndingDateChanged -> {
                updateEndingDate(createAuctionEvents.endingDate)
            }
            is CreateAuctionEvents.DescriptionChanged -> {
                updateDescriptionAuction(createAuctionEvents.description)
            }
            is CreateAuctionEvents.DescriptionDeleted -> {
                deleteDescriptionAuction()
            }
            is CreateAuctionEvents.MinAcceptedChanged -> {
                updateMinAccepted(createAuctionEvents.minAccepted)
            }
            is CreateAuctionEvents.MaxBidChanged -> {
                updateMaxBid(createAuctionEvents.maxBid)
            }
            is CreateAuctionEvents.Submit -> {
                submitCreateAuction()
            }
        }
    }

    private fun submitCreateAuction() {
        if (validationBlock()) {
            val currentState = _createAuctionUiState.value
            if(currentState is CreateAuctionUiState.CreateAuctionParams){
                _createAuctionUiState.value = CreateAuctionUiState.Loading
                viewModelScope.launch {
                    _createAuctionUiState.value = try {
                        val sharedPreferences = getApplication<Application>().getSharedPreferences(
                            "AppPreferences",
                            Context.MODE_PRIVATE
                        )
                        val userId = sharedPreferences.getString("userId", null)

                        _createAuctionUiState.value = currentState.copy(
                            auction = currentState.auction.copy(
                                ownerId = userId ?: ""
                            )
                        )

                        val item = currentState.auction.item
                        val uploadedUrls = item.imagesUri.map { uriString ->
                            val file = File(Uri.parse(uriString).path ?: throw IllegalArgumentException("Invalid URI"))
                            ApiService.uploadImage(file, "img").bodyAsText()
                        }

                        _createAuctionUiState.value = currentState.copy(
                            auction = currentState.auction.copy(
                                item = currentState.auction.item.copy(
                                    imagesUri = uploadedUrls
                                )
                            )
                        )

                        val auctionRequest: com.CioffiDeVivo.dietideals.domain.requestModels.Auction =
                            currentState.auction.toRequestModel()
                        val response = ApiService.createAuction(auctionRequest)
                        if (response.status.isSuccess()) {
                            CreateAuctionUiState.Success
                        } else{
                            CreateAuctionUiState.Error
                        }
                    } catch (e: Exception){
                        CreateAuctionUiState.Error
                    }
                }
            }

        }
    }

    private fun validationBlock(): Boolean {
        val currentState = _createAuctionUiState.value
        if(currentState is CreateAuctionUiState.CreateAuctionParams){
            try {
                val itemNameValidation = validateCreateAuctionForm.validateItemName(currentState.auction.item.name)
                val auctionTypeValidation = validateCreateAuctionForm.validateAuctionType(currentState.auction.type)
                val intervalValidation = validateCreateAuctionForm.validateInterval(currentState.auction.interval)
                val minStepValidation = validateCreateAuctionForm.validateMinStep(currentState.auction.minStep)
                val minAcceptedValidation = validateCreateAuctionForm.validateMinAccepted(currentState.auction.minAccepted)
                val maxBidValidation = validateCreateAuctionForm.validateMaxBid(currentState.auction.maxBid)

                val hasErrorAuctionSilent = listOf(
                    itemNameValidation,
                    minAcceptedValidation,
                    maxBidValidation
                ).any { !it.positiveResult }

                val hasErrorAuctionEnglish = listOf(
                    itemNameValidation,
                    minStepValidation,
                    intervalValidation,
                ).any { !it.positiveResult }

                if(currentState.auction.type == AuctionType.None){
                    _createAuctionUiState.value = currentState.copy(
                        auctionTypeErrorMsg = auctionTypeValidation.errorMessage
                    )
                    return false
                }

                if(hasErrorAuctionSilent && currentState.auction.type == AuctionType.Silent){
                    _createAuctionUiState.value = currentState.copy(
                        itemNameErrorMsg = itemNameValidation.errorMessage,
                        minAcceptedErrorMsg = minAcceptedValidation.errorMessage,
                        maxBidErrorMsg = maxBidValidation.errorMessage
                    )
                    return false
                }
                if(hasErrorAuctionEnglish && currentState.auction.type == AuctionType.English){
                    _createAuctionUiState.value = currentState.copy(
                        itemNameErrorMsg = itemNameValidation.errorMessage,
                        minStepErrorMsg = minStepValidation.errorMessage,
                        intervalErrorMsg = intervalValidation.errorMessage
                    )
                    return false
                }
                viewModelScope.launch {
                    validationEventChannel.send(ValidationState.Success)
                }
                return true
            } catch (e: Exception){
                _createAuctionUiState.value = CreateAuctionUiState.Error
                return false
            }
        } else{
            return false
        }
    }

    private fun updateItemName(itemName: String) {
        try {
            val currentState = _createAuctionUiState.value
            if(currentState is CreateAuctionUiState.CreateAuctionParams){
                _createAuctionUiState.value = currentState.copy(
                    auction = currentState.auction.copy(
                        item = currentState.auction.item.copy(
                            name = itemName
                        )
                    )
                )
            }
        } catch (e: Exception){
            _createAuctionUiState.value = CreateAuctionUiState.Error
        }
    }

    private fun deleteItemName(){
        updateItemName("")
    }

    private fun updateImagesUri(imagesUri: String){
        try {
            val currentState = _createAuctionUiState.value
            if(currentState is CreateAuctionUiState.CreateAuctionParams){
                val updatedImagesUri = currentState.auction.item.imagesUri.toMutableList()
                updatedImagesUri += imagesUri
                _createAuctionUiState.value = currentState.copy(
                    auction = currentState.auction.copy(
                        item = currentState.auction.item.copy(
                            imagesUri = updatedImagesUri.distinct()
                        )
                    )
                )
            }
        } catch (e: Exception){
            _createAuctionUiState.value = CreateAuctionUiState.Error
        }
    }

    private fun deleteImageUri(index: Int){
        try {
            val currentState = _createAuctionUiState.value
            if(currentState is CreateAuctionUiState.CreateAuctionParams){
                val updatedImagesUri = currentState.auction.item.imagesUri.toMutableList()
                updatedImagesUri.removeAt(index)
                _createAuctionUiState.value = currentState.copy(
                    auction = currentState.auction.copy(
                        item = currentState.auction.item.copy(
                            imagesUri = updatedImagesUri.distinct()
                        )
                    )
                )
            }
        } catch (e: Exception){
            _createAuctionUiState.value = CreateAuctionUiState.Error
        }
    }

    fun updateDescriptionAuction(description: String){
        try {
            val currentState = _createAuctionUiState.value
            if(currentState is CreateAuctionUiState.CreateAuctionParams){
                _createAuctionUiState.value = currentState.copy(
                    auction = currentState.auction.copy(
                        description = description
                    )
                )
            }
        } catch (e: Exception){
            _createAuctionUiState.value = CreateAuctionUiState.Error
        }
    }

    fun deleteDescriptionAuction(){
        updateDescriptionAuction("")
    }

    fun updateEndingDate(endingDate: Long){
        try {
            val currentState = _createAuctionUiState.value
            if(currentState is CreateAuctionUiState.CreateAuctionParams){
                _createAuctionUiState.value = currentState.copy(
                    auction = currentState.auction.copy(
                        endingDate = Instant
                            .ofEpochMilli(endingDate)
                            .atZone(ZoneId.of("UTC"))
                            .toLocalDateTime()
                    )
                )
            }
        } catch (e: Exception){
            _createAuctionUiState.value = CreateAuctionUiState.Error
        }
    }

    private fun updateInterval(interval: String){
        try {
            val currentState = _createAuctionUiState.value
            if(currentState is CreateAuctionUiState.CreateAuctionParams){
                _createAuctionUiState.value = currentState.copy(
                    auction = currentState.auction.copy(
                        interval = interval
                    )
                )
            }
        } catch (e: Exception){
            _createAuctionUiState.value = CreateAuctionUiState.Error
        }
    }

    private fun deleteInterval(){
        updateInterval("")
    }

    private fun updateMinStep(minStep: String){
        try {
            val currentState = _createAuctionUiState.value
            if(currentState is CreateAuctionUiState.CreateAuctionParams){
                if(minStep.isEmpty()){
                    _createAuctionUiState.value = currentState.copy(
                        auction = currentState.auction.copy(
                            minStep = "0"
                        )
                    )
                } else{
                    _createAuctionUiState.value = currentState.copy(
                        auction = currentState.auction.copy(
                            minStep = minStep
                        )
                    )
                }
            }
        } catch (e: Exception){
            _createAuctionUiState.value = CreateAuctionUiState.Error
        }
    }

    private fun updateMinAccepted(minAccepted: String){
        try {
            val currentState = _createAuctionUiState.value
            if(currentState is CreateAuctionUiState.CreateAuctionParams){
                if(minAccepted.isEmpty()){
                    _createAuctionUiState.value = currentState.copy(
                        auction = currentState.auction.copy(
                            minAccepted = "0"
                        )
                    )
                } else{
                    _createAuctionUiState.value = currentState.copy(
                        auction = currentState.auction.copy(
                            minAccepted = minAccepted
                        )
                    )
                }
            }
        } catch (e: Exception){
            _createAuctionUiState.value = CreateAuctionUiState.Error
        }
    }

    private fun updateMaxBid(maxBid: String){
        try {
            val currentState = _createAuctionUiState.value
            if(currentState is CreateAuctionUiState.CreateAuctionParams){
                if(maxBid.isEmpty()){
                    _createAuctionUiState.value = currentState.copy(
                        auction = currentState.auction.copy(
                            maxBid = "0"
                        )
                    )
                } else{
                    _createAuctionUiState.value = currentState.copy(
                        auction = currentState.auction.copy(
                            maxBid = maxBid
                        )
                    )
                }
            }
        } catch (e: Exception){
            _createAuctionUiState.value = CreateAuctionUiState.Error
        }
    }

    private fun updateAuctionType(auctionType: AuctionType){
        try {
            val currentState = _createAuctionUiState.value
            if(currentState is CreateAuctionUiState.CreateAuctionParams){
                _createAuctionUiState.value = currentState.copy(
                    auction = currentState.auction.copy(
                        type = auctionType
                    )
                )
            }
        } catch (e: Exception){
            _createAuctionUiState.value = CreateAuctionUiState.Error
        }
    }

    private fun updateAuctionCategory(auctionCategory: AuctionCategory){
        try {
            val currentState = _createAuctionUiState.value
            if(currentState is CreateAuctionUiState.CreateAuctionParams){
                _createAuctionUiState.value = currentState.copy(
                    auction = currentState.auction.copy(
                        category = auctionCategory
                    )
                )
            }
        } catch (e: Exception){
            _createAuctionUiState.value = CreateAuctionUiState.Error
        }
    }

    fun removeErrorMsgAuctionType(){
        val currentState = _createAuctionUiState.value
        if(currentState is CreateAuctionUiState.CreateAuctionParams){
            _createAuctionUiState.value = currentState.copy(
                auctionTypeErrorMsg = null
            )
        }
    }

}