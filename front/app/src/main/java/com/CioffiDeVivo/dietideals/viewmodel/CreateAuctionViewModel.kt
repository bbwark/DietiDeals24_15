package com.CioffiDeVivo.dietideals.viewmodel

import android.net.Uri
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.CioffiDeVivo.dietideals.Events.CreateAuctionEvents
import com.CioffiDeVivo.dietideals.Events.RegistrationEvent
import com.CioffiDeVivo.dietideals.domain.DataModels.AuctionCategory
import com.CioffiDeVivo.dietideals.domain.DataModels.AuctionType
import com.CioffiDeVivo.dietideals.domain.DataModels.Item
import com.CioffiDeVivo.dietideals.domain.use_case.ValidateCreateAuctionForm
import com.CioffiDeVivo.dietideals.domain.use_case.ValidationState
import com.CioffiDeVivo.dietideals.viewmodel.state.CreateAuctionState
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import java.time.Instant
import java.time.ZoneId

class CreateAuctionViewModel( private val validateCreateAuctionForm: ValidateCreateAuctionForm = ValidateCreateAuctionForm() ): ViewModel() {

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
            is CreateAuctionEvents.AuctionTypeChangedToEnglish -> {
                updateAuctionTypeToEnglish()
            }
            is CreateAuctionEvents.AuctionTypeChangedToSilent -> {
                updateAuctionTypeToSilent()
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

        val itemNameValidation = validateCreateAuctionForm.validateItemName(auctionState.value.itemName)
        val intervalValidation = validateCreateAuctionForm.validateInterval(auctionState.value.interval)
        val minStepValidation = validateCreateAuctionForm.validateMinStep(auctionState.value.minStep)
        val minAcceptedValidation = validateCreateAuctionForm.validateMinAccepted(auctionState.value.minAccepted)
        val descriptionValidation = validateCreateAuctionForm.validateDescription(auctionState.value.description)

        val hasErrorAuctionSilent = listOf(
            itemNameValidation,
            minAcceptedValidation,
            descriptionValidation
        ).any { it.positiveResult }

        val hasErrorAuctionEnglish = listOf(
            itemNameValidation,
            minStepValidation,
            intervalValidation,
            descriptionValidation
        ).any { it.positiveResult }

        if(hasErrorAuctionSilent && auctionState.value.auctionType == AuctionType.Silent){
            _auctionState.value = _auctionState.value.copy(
                itemNameErrorMsg = itemNameValidation.errorMessage,
                minAcceptedErrorMsg = minAcceptedValidation.errorMessage
            )
            return
        }
        if(hasErrorAuctionEnglish && auctionState.value.auctionType == AuctionType.English){
            _auctionState.value = _auctionState.value.copy(
                itemNameErrorMsg = itemNameValidation.errorMessage,
                minStepErrorMsg = minStepValidation.errorMessage,
                intervalErrorMsg = intervalValidation.errorMessage
            )
            return
        }

        viewModelScope.launch {
            validationEventChannel.send(ValidationState.Success)
        }
    }

    private fun updateItemName(itemName: String){
        _auctionState.value = _auctionState.value.copy(
            itemName = itemName
        )
    }

    private fun deleteItemName(){
        _auctionState.value = _auctionState.value.copy(
            itemName = ""
        )
    }

    private fun updateImagesUri(imagesUri: Uri?){
        val updatedImagesUri = _auctionState.value.imagesUri.toMutableList()
        updatedImagesUri += imagesUri
        _auctionState.value = _auctionState.value.copy(
            imagesUri  = updatedImagesUri.distinct()
        )
    }

    private fun deleteImageUri(index: Int){
        val updatedImagesUri = _auctionState.value.imagesUri.toMutableList()
        updatedImagesUri.removeAt(index)
        _auctionState.value = _auctionState.value.copy(
            imagesUri = updatedImagesUri.distinct()
        )
    }

    fun updateDescriptionAuction(description: String){
        _auctionState.value = _auctionState.value.copy(
            description = description
        )
    }

    fun deleteDescriptionAuction(){
        _auctionState.value = _auctionState.value.copy(
            description = ""
        )
    }

    fun updateEndingDate(endingDate: Long){
        _auctionState.value = _auctionState.value.copy(
            endingDate = Instant
                .ofEpochMilli(endingDate)
                .atZone(ZoneId.of("UTC"))
                .toLocalDate()
        )
    }

    fun updateInterval(interval: String){
        _auctionState.value = _auctionState.value.copy(
            interval = interval
        )
    }

    fun deleteInterval(){
        _auctionState.value = _auctionState.value.copy(
            interval = ""
        )
    }

    fun updateMinStep(minStep: String){
        _auctionState.value = _auctionState.value.copy(
            minStep = minStep.toFloat()
        )
    }

    fun updateMinAccepted(minAccepted: String){
        _auctionState.value = _auctionState.value.copy(
            minAccepted = minAccepted.toFloat()
        )
    }

    fun updateAuctionTypeToEnglish(){
        _auctionState.value = _auctionState.value.copy(
            auctionType = AuctionType.English
        )
    }

    fun updateAuctionTypeToSilent(){
        _auctionState.value = _auctionState.value.copy(
            auctionType = AuctionType.Silent
        )
    }

    fun updateAuctionCategory(auctionCategory: AuctionCategory){
        _auctionState.value = _auctionState.value.copy(
            auctionCategory = auctionCategory
        )
    }

}