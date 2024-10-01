package com.CioffiDeVivo.dietideals.presentation.ui.createAuction

import android.app.Application
import android.content.Context
import android.net.Uri
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.CioffiDeVivo.dietideals.utils.ApiService
import com.CioffiDeVivo.dietideals.domain.models.AuctionCategory
import com.CioffiDeVivo.dietideals.domain.models.AuctionType
import com.CioffiDeVivo.dietideals.domain.models.Item
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

class CreateAuctionViewModel(application: Application, private val validateCreateAuctionForm: ValidateCreateAuctionForm = ValidateCreateAuctionForm() ): AndroidViewModel(application) {

    private val _auctionState = MutableStateFlow(CreateAuctionState())
    val auctionState: StateFlow<CreateAuctionState> = _auctionState.asStateFlow()
    private val _itemState = MutableStateFlow(Item())
    val itemState: StateFlow<Item> = _itemState.asStateFlow()
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
            is CreateAuctionEvents.Submit -> {
                submitCreateAuction()
            }
        }
    }

    private fun submitCreateAuction() {
        if (validationBlock()) {
            viewModelScope.launch {
                try {
                    val sharedPreferences = getApplication<Application>().getSharedPreferences(
                        "AppPreferences",
                        Context.MODE_PRIVATE
                    )
                    val userId = sharedPreferences.getString("userId", null)

                    _auctionState.value = _auctionState.value.copy(
                        auction = _auctionState.value.auction.copy(
                            ownerId = userId ?: ""
                        )
                    )

                    val item = auctionState.value.auction.item
                    val uploadedUrls = item.imagesUri.map { uriString ->
                        val file = File(Uri.parse(uriString).path ?: throw IllegalArgumentException("Invalid URI"))
                        ApiService.uploadImage(file, "img").bodyAsText()
                    }

                    _auctionState.value = _auctionState.value.copy(
                        auction = _auctionState.value.auction.copy(
                            item = _auctionState.value.auction.item.copy(
                                imagesUri = uploadedUrls
                            )
                        )
                    )

                    val auctionRequest: com.CioffiDeVivo.dietideals.domain.requestModels.Auction =
                        _auctionState.value.auction.toRequestModel()
                    val response = ApiService.createAuction(auctionRequest)
                    if (response.status.isSuccess()) {

                        //TODO effettua navigazione da qualche altra parte


                    }
                } catch (e: Exception) {
                    //TODO ERROR HANDLING
                }
            }
        }
    }

    private fun validationBlock(): Boolean {
        val itemNameValidation = validateCreateAuctionForm.validateItemName(auctionState.value.auction.item.name)
        val intervalValidation = validateCreateAuctionForm.validateInterval(auctionState.value.auction.interval)
        val minStepValidation = validateCreateAuctionForm.validateMinStep(auctionState.value.auction.minStep)
        val minAcceptedValidation = validateCreateAuctionForm.validateMinAccepted(auctionState.value.auction.minAccepted)
        val descriptionValidation = validateCreateAuctionForm.validateDescription(auctionState.value.auction.description)

        val hasErrorAuctionSilent = listOf(
            itemNameValidation,
            minAcceptedValidation,
            descriptionValidation
        ).any { !it.positiveResult }

        val hasErrorAuctionEnglish = listOf(
            itemNameValidation,
            minStepValidation,
            intervalValidation,
            descriptionValidation
        ).any { !it.positiveResult }

        if(hasErrorAuctionSilent && auctionState.value.auction.type == AuctionType.Silent){
            _auctionState.value = _auctionState.value.copy(
                itemNameErrorMsg = itemNameValidation.errorMessage,
                minAcceptedErrorMsg = minAcceptedValidation.errorMessage
            )
            return false
        }
        if(hasErrorAuctionEnglish && auctionState.value.auction.type == AuctionType.English){
            _auctionState.value = _auctionState.value.copy(
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
    }

    private fun updateItemName(itemName: String) {
        _auctionState.value = _auctionState.value.copy(
            auction = _auctionState.value.auction.copy(
                item = _auctionState.value.auction.item.copy(
                    name = itemName
                )
            )
        )
    }

    private fun deleteItemName(){
        updateItemName("")
    }

    private fun updateImagesUri(imagesUri: String){
        val updatedImagesUri = _auctionState.value.auction.item.imagesUri.toMutableList()
        updatedImagesUri += imagesUri
        _auctionState.value = _auctionState.value.copy(
            auction = _auctionState.value.auction.copy(
                item = _auctionState.value.auction.item.copy(
                    imagesUri = updatedImagesUri.distinct()
                )
            )
        )
    }

    private fun deleteImageUri(index: Int){
        val updatedImagesUri = _auctionState.value.auction.item.imagesUri.toMutableList()
        updatedImagesUri.removeAt(index)
        _auctionState.value = _auctionState.value.copy(
            auction = _auctionState.value.auction.copy(
                item = _auctionState.value.auction.item.copy(
                    imagesUri = updatedImagesUri.distinct()
                )
            )
        )
    }

    fun updateDescriptionAuction(description: String){
        _auctionState.value = _auctionState.value.copy(
            auction = _auctionState.value.auction.copy(
                description = description
            )
        )
    }

    fun deleteDescriptionAuction(){
        updateDescriptionAuction("")
    }

    fun updateEndingDate(endingDate: Long){
        _auctionState.value = _auctionState.value.copy(
            auction = _auctionState.value.auction.copy(
                endingDate = Instant
                    .ofEpochMilli(endingDate)
                    .atZone(ZoneId.of("UTC"))
                    .toLocalDate()
            )
        )
    }

    private fun updateInterval(interval: String){
        _auctionState.value = _auctionState.value.copy(
            auction = _auctionState.value.auction.copy(
                interval = interval
            )
        )
    }

    private fun deleteInterval(){
        updateInterval("")
    }

    private fun updateMinStep(minStep: String){
        if(minStep.isEmpty()){
            _auctionState.value = _auctionState.value.copy(
                auction = _auctionState.value.auction.copy(
                    minStep = "0"
                )
            )
        } else{
            _auctionState.value = _auctionState.value.copy(
                auction = _auctionState.value.auction.copy(
                    minStep = minStep
                )
            )
        }
    }

    private fun updateMinAccepted(minAccepted: String){
        if(minAccepted.isEmpty()){
            _auctionState.value = _auctionState.value.copy(
                auction = _auctionState.value.auction.copy(
                    minAccepted = "0"
                )
            )
        } else{
            _auctionState.value = _auctionState.value.copy(
                auction = _auctionState.value.auction.copy(
                    minAccepted = minAccepted
                )
            )
        }
    }

    private fun updateAuctionType(auctionType: AuctionType){
        _auctionState.value = _auctionState.value.copy(
            auction = _auctionState.value.auction.copy(
                type = auctionType
            )
        )
    }

    private fun updateAuctionCategory(auctionCategory: String){
        _auctionState.value = _auctionState.value.copy(
            auction = _auctionState.value.auction.copy(
                category = AuctionCategory.valueOf(auctionCategory)
            )
        )
    }

}