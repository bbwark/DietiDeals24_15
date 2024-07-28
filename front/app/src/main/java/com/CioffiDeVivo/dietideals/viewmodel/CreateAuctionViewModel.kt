package com.CioffiDeVivo.dietideals.viewmodel

import android.net.Uri
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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



    fun updateItemName(itemName: String){
        _auctionState.value = _auctionState.value.copy(
            itemName = itemName
        )
    }

    fun deleteItemName(){
        _auctionState.value = _auctionState.value.copy(
            itemName = ""
        )
    }

    fun updateImagesUri(imagesUri: Uri?){
        val updatedImagesUri = _auctionState.value.imagesUri.toMutableList()
        updatedImagesUri += imagesUri
        _auctionState.value = _auctionState.value.copy(
            imagesUri  = updatedImagesUri.distinct()
        )
    }

    fun deleteImageUri(index: Int){
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

    @RequiresApi(Build.VERSION_CODES.O)
    fun updateEndingDate(endingDate: Long){
        _auctionState.value = _auctionState.value.copy(
            endingDate = Instant
                .ofEpochMilli(endingDate)
                .atZone(ZoneId.of("UTC"))
                .toLocalDate()
        )
    }

    fun deleteEndingDate(){
        _auctionState.value = _auctionState.value.copy(
            endingDate = null
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

    fun deleteMinStep(){
        _auctionState.value = _auctionState.value.copy(
            minStep = 0.0f
        )
    }

    fun updateMinAccepted(minAccepted: String){
        _auctionState.value = _auctionState.value.copy(
            minAccepted = minAccepted.toFloat()
        )
    }

    fun deleteMinAccepted(){
        _auctionState.value = _auctionState.value.copy(
            minAccepted = 0.0f
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

    private fun submitAuction(){
        val itemNameValidation = validateCreateAuctionForm.validateItemName(auctionState.value.itemName)
        val minAcceptedValidation = validateCreateAuctionForm.validateMinAccepted(auctionState.value.minAccepted)
        val minStepValidation = validateCreateAuctionForm.validateMinStep(auctionState.value.minStep)
        val intervalValidation = validateCreateAuctionForm.validateInterval(auctionState.value.interval)

        val hasErrorAuctionSilent = listOf(
            itemNameValidation,
            minAcceptedValidation,
        ).any { it.positiveResult }

        val hasErrorAuctionEnglish = listOf(
            itemNameValidation,
            minStepValidation,
            intervalValidation
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

}