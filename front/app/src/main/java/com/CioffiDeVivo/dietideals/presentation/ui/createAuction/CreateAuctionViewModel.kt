package com.CioffiDeVivo.dietideals.presentation.ui.createAuction

import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.CioffiDeVivo.dietideals.data.UserPreferencesRepository
import com.CioffiDeVivo.dietideals.data.models.AuctionType
import com.CioffiDeVivo.dietideals.data.repositories.AuctionRepository
import com.CioffiDeVivo.dietideals.data.repositories.ImageRepository
import com.CioffiDeVivo.dietideals.data.validations.ValidateCreateAuctionForm
import com.CioffiDeVivo.dietideals.data.validations.ValidationState
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import java.time.Instant
import java.time.ZoneId

class CreateAuctionViewModel(
    private val userPreferencesRepository: UserPreferencesRepository,
    private val auctionRepository: AuctionRepository,
    private val imageRepository: ImageRepository,
    private val validateCreateAuctionForm: ValidateCreateAuctionForm = ValidateCreateAuctionForm()
): ViewModel() {

    private val _createAuctionUiState = MutableStateFlow<CreateAuctionUiState>(CreateAuctionUiState.CreateAuctionParams())
    val createAuctionUiState: StateFlow<CreateAuctionUiState> = _createAuctionUiState.asStateFlow()
    private val validationEventChannel = Channel<ValidationState>()
    val validationCreateAuctionEvent = validationEventChannel.receiveAsFlow()

    fun createAuctionOnAction(createAuctionEvents: CreateAuctionEvents){
        try {
            val currentState = _createAuctionUiState.value
            if(currentState is CreateAuctionUiState.CreateAuctionParams){
                when(createAuctionEvents){
                    is CreateAuctionEvents.ItemNameChanged -> {
                        _createAuctionUiState.value = currentState.copy(auction = currentState.auction.copy(item = currentState.auction.item.copy(name = createAuctionEvents.itemName)))
                    }
                    is CreateAuctionEvents.ItemNameDeleted -> {
                        _createAuctionUiState.value = currentState.copy(auction = currentState.auction.copy(item = currentState.auction.item.copy(name = "")))
                    }
                    is CreateAuctionEvents.ImagesChanged -> {
                        updateImagesUri(createAuctionEvents.image)
                    }
                    is CreateAuctionEvents.ImagesDeleted -> {
                        deleteImageUri(createAuctionEvents.index)
                    }
                    is CreateAuctionEvents.AuctionTypeChanged -> {
                        _createAuctionUiState.value = currentState.copy(auction = currentState.auction.copy(type = createAuctionEvents.auctionType))
                    }
                    is CreateAuctionEvents.AuctionCategoryChanged -> {
                        _createAuctionUiState.value = currentState.copy(auction = currentState.auction.copy(category = createAuctionEvents.auctionCategory))
                    }
                    is CreateAuctionEvents.IntervalChanged -> {
                        _createAuctionUiState.value = currentState.copy(auction = currentState.auction.copy(interval = createAuctionEvents.interval))
                    }
                    is CreateAuctionEvents.IntervalDeleted -> {
                        _createAuctionUiState.value = currentState.copy(auction = currentState.auction.copy(interval = ""))
                    }
                    is CreateAuctionEvents.MinStepChanged -> {
                        updateMinStep(createAuctionEvents.minStep)
                    }
                    is CreateAuctionEvents.EndingDateChanged -> {
                        updateEndingDate(createAuctionEvents.endingDate)
                    }
                    is CreateAuctionEvents.DescriptionChanged -> {
                        _createAuctionUiState.value = currentState.copy(auction = currentState.auction.copy(description = createAuctionEvents.description))
                    }
                    is CreateAuctionEvents.DescriptionDeleted -> {
                        _createAuctionUiState.value = currentState.copy(auction = currentState.auction.copy(description = ""))
                    }
                    is CreateAuctionEvents.MinAcceptedChanged -> {
                        updateMinAccepted(createAuctionEvents.minAccepted)
                    }
                    is CreateAuctionEvents.MaxBidChanged -> {
                        updateMaxBid(createAuctionEvents.maxBid)
                    }
                }
            }
        } catch (e: Exception){
            _createAuctionUiState.value = CreateAuctionUiState.Error
        }
    }

    fun submitCreateAuction(context: Context) {
        if (validationBlock()) {
            val currentState = _createAuctionUiState.value
            _createAuctionUiState.value = CreateAuctionUiState.Loading
            if(currentState is CreateAuctionUiState.CreateAuctionParams){
                viewModelScope.launch {
                    _createAuctionUiState.value = try {
                        val userId = userPreferencesRepository.getUserIdPreference()
                        val imageUrls = currentState.auction.item.imageUrl.map { uri ->
                            async {
                                imageRepository.uploadImage(
                                    Uri.parse(uri),
                                    "image_${System.currentTimeMillis()}.jpg",
                                    context = context
                                )
                            }
                        }.awaitAll()
                        val updatedAuction = currentState.auction.copy(
                            ownerId = userId,
                            item = currentState.auction.item.copy(
                                imageUrl = emptyList()
                            )
                        )
                        auctionRepository.createAuction(updatedAuction)
                        CreateAuctionUiState.Success
                    } catch (e: Exception){
                        Log.e("Error", "Error: ${e.message}")
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
                val minAcceptedValidation = validateCreateAuctionForm.validateMinAccepted(currentState.auction.startingPrice)
                val maxBidValidation = validateCreateAuctionForm.validateMaxBid(currentState.auction.buyoutPrice)

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

    private fun updateImagesUri(imagesUri: String){
        try {
            val currentState = _createAuctionUiState.value
            if(currentState is CreateAuctionUiState.CreateAuctionParams){
                val updatedImagesUri = currentState.auction.item.imageUrl.toMutableList()
                updatedImagesUri += imagesUri
                _createAuctionUiState.value = currentState.copy(
                    auction = currentState.auction.copy(
                        item = currentState.auction.item.copy(
                            imageUrl = updatedImagesUri.distinct()
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
                val updatedImagesUri = currentState.auction.item.imageUrl.toMutableList()
                updatedImagesUri.removeAt(index)
                _createAuctionUiState.value = currentState.copy(
                    auction = currentState.auction.copy(
                        item = currentState.auction.item.copy(
                            imageUrl = updatedImagesUri.distinct()
                        )
                    )
                )
            }
        } catch (e: Exception){
            _createAuctionUiState.value = CreateAuctionUiState.Error
        }
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
                            startingPrice = "0"
                        )
                    )
                } else{
                    _createAuctionUiState.value = currentState.copy(
                        auction = currentState.auction.copy(
                            startingPrice = minAccepted
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
                            buyoutPrice = "0"
                        )
                    )
                } else{
                    _createAuctionUiState.value = currentState.copy(
                        auction = currentState.auction.copy(
                            buyoutPrice = maxBid
                        )
                    )
                }
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