package com.CioffiDeVivo.dietideals

import android.net.Uri
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.CioffiDeVivo.dietideals.DataModels.Auction
import com.CioffiDeVivo.dietideals.DataModels.AuctionType
import com.CioffiDeVivo.dietideals.DataModels.Bid
import com.CioffiDeVivo.dietideals.DataModels.CreditCard
import com.CioffiDeVivo.dietideals.DataModels.Item
import com.CioffiDeVivo.dietideals.DataModels.ObservedUser
import com.CioffiDeVivo.dietideals.DataModels.User
import com.CioffiDeVivo.dietideals.utils.DateUtils
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.time.ZonedDateTime
import java.util.UUID

@RequiresApi(Build.VERSION_CODES.O)
class DietiDealsViewModel : ViewModel() {

    private val _userState = MutableStateFlow(User())
    val userState: StateFlow<User> = _userState.asStateFlow()

    private val _creditCardState = MutableStateFlow(CreditCard())
    val creditCardState: StateFlow<CreditCard> = _creditCardState.asStateFlow()

    private val _auctionState = MutableStateFlow(Auction())
    val auctionState: StateFlow<Auction> = _auctionState.asStateFlow()

    private val _itemState = MutableStateFlow(Item())
    val itemState: StateFlow<Item> = _itemState.asStateFlow()

    private val _bidState = MutableStateFlow(Bid())
    val bidState: StateFlow<Bid> = _bidState.asStateFlow()

    var auctionOpenByOwner by mutableStateOf(false)
    var user by mutableStateOf(
        User(
            UUID.randomUUID(),
            "Nametest Surnametest",
            "",
            "passwordtest",
            creditCards = arrayOf(
                CreditCard("556666666666", LocalDate.now().plusYears(1),"222"),
                CreditCard("456666666666", LocalDate.now().plusYears(2), "222"),
                CreditCard("356666666666", LocalDate.now().plusYears(2), "222")
            )
        )
    )
    var selectedNavBarItem: MutableState<Int> = mutableStateOf(0)
    var selectedAuction by mutableStateOf(
        Auction(
            UUID.randomUUID(),
            UUID.randomUUID(),
            Item(id = UUID.randomUUID(), name = ""),
            bids = arrayOf(
                Bid(
                    UUID.randomUUID(),
                    11f,
                    UUID.randomUUID(),
                    ZonedDateTime.now().minusDays(5)
                )
            ),
            endingDate = LocalDate.now(),
            expired = false,
            auctionType = AuctionType.English
        )
    )
    var selectedAuctionBidders: List<ObservedUser> = listOf() //need to write a function that takes all the users ID available in selectedAuction.bids and then make requests to the server based on those IDs to fill this list

    var auctionSearchResult: Array<Auction> = arrayOf()
    var auctionCreatedByUser: Array<Auction> = arrayOf(
        Auction(
            UUID.randomUUID(),
            UUID.randomUUID(),
            Item(id = UUID.randomUUID(), name = "First Auction"),
            endingDate = LocalDate.now().plusMonths(2),
            expired = false,
            auctionType = AuctionType.English
        ),
        Auction(
            UUID.randomUUID(),
            UUID.randomUUID(),
            Item(id = UUID.randomUUID(), name = "Second Auction"),
            endingDate = LocalDate.now().plusMonths(2),
            expired = false,
            auctionType = AuctionType.English
        ),
        Auction(
            UUID.randomUUID(),
            UUID.randomUUID(),
            Item(id = UUID.randomUUID(), name = "Third Auction"),
            endingDate = LocalDate.now().plusMonths(2),
            expired = false,
            auctionType = AuctionType.English
        )
    )

    /*Update & Delete for User*/

    fun updateName(name: String){
        _userState.value = _userState.value.copy(
            name = name
        )
    }

    fun deleteName(){
        _userState.value = _userState.value.copy(
            name = ""
        )
    }

    fun updateSurname(surname: String){
        _userState.value = _userState.value.copy(
            surname = surname
        )
    }

    fun deleteSurname(){
        _userState.value = _userState.value.copy(
            surname = ""
        )
    }

    fun updateEmail(email: String){
        _userState.value = _userState.value.copy(
            email = email
        )
    }

    fun deleteEmail(){
        _userState.value = _userState.value.copy(
            email = ""
        )
    }

    fun updatePassword(password: String){
        _userState.value = _userState.value.copy(
            password = password
        )
    }

    fun updateNewPassword(newPassword: String){
        _userState.value = _userState.value.copy(
            newPassword = newPassword
        )
    }

    fun updateIsSeller(){
        _userState.value = _userState.value.copy(
            isSeller = !_userState.value.isSeller
        )
    }

    fun updateBio(bio: String){
        _userState.value = _userState.value.copy(
            bio = bio
        )
    }

    fun deleteBio(){
        _userState.value = _userState.value.copy(
            bio = ""
        )
    }

    fun updateAddress(address: String){
        _userState.value = _userState.value.copy(
            address = address
        )
    }

    fun deleteAddress(){
        _userState.value = _userState.value.copy(
            address = ""
        )
    }

    fun updateZipCode(zipCode: String){
        _userState.value = _userState.value.copy(
            zipCode = zipCode
        )
    }

    fun deleteZipCode(){
        _userState.value = _userState.value.copy(
            zipCode = ""
        )
    }

    fun updateCountry(country: String){
        _userState.value = _userState.value.copy(
            country = country
        )
    }

    fun updatePhoneNumber(phoneNumber: String){
        _userState.value = _userState.value.copy(
            phoneNumber = phoneNumber
        )
    }

    fun deletePhoneNumber(){
        _userState.value = _userState.value.copy(
            phoneNumber = ""
        )
    }

    /* Update & Delete CreditCard */

    fun updateCreditCardNumber(creditCardNumber: String){
        _creditCardState.value = _creditCardState.value.copy(
            creditCardNumber = creditCardNumber
        )
    }

    fun deleteCreditCardNumber(){
        _creditCardState.value = _creditCardState.value.copy(
            creditCardNumber = ""
        )
    }

    fun updateExpirationDate(expirationDate: LocalDate){
        _creditCardState.value = _creditCardState.value.copy(
            expirationDate = expirationDate
        )
    }

    fun deleteExpirationDate(){
        _creditCardState.value = _creditCardState.value.copy(
            expirationDate = LocalDate.now()
        )
    }

    fun updateCvv(cvv: String){
        _creditCardState.value = _creditCardState.value.copy(
            cvv = cvv
        )
    }

    fun deleteCvv(){
        _creditCardState.value = _creditCardState.value.copy(
            cvv = ""
        )
    }

    fun updateIban(iban: String){
        _creditCardState.value = _creditCardState.value.copy(
            iban = iban
        )
    }

    fun deleteIban(){
        _creditCardState.value = _creditCardState.value.copy(
            iban = ""
        )
    }

    /* Update & Delete Item */

    fun updateItemName(itemName: String){
        _itemState.value = _itemState.value.copy(
            name = itemName
        )
    }

    fun deleteItemName(){
        _itemState.value = _itemState.value.copy(
            name = ""
        )
    }

    fun updateImagesUri(imagesUri: Uri?){
        val updatedImagesUri = _itemState.value.imagesUri.toMutableList()
        updatedImagesUri += imagesUri
        _itemState.value = _itemState.value.copy(
            imagesUri  = updatedImagesUri.distinct()
        )
    }

    fun deleteImageUri(index: Int){
        val updatedImagesUri = _itemState.value.imagesUri.toMutableList()
        updatedImagesUri.removeAt(index)
        _itemState.value = _itemState.value.copy(
            imagesUri = updatedImagesUri.distinct()
        )
    }

    /* Update & Delete Auction */

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
            minStep = minStep
        )
    }

    fun deleteMinStep(){
        _auctionState.value = _auctionState.value.copy(
            minStep = ""
        )
    }

    fun updateMinAccepted(minAccepted: String){
        _auctionState.value = _auctionState.value.copy(
            minAccepted = minAccepted
        )
    }

    fun deleteMinAccepted(){
        _auctionState.value = _auctionState.value.copy(
            minAccepted = ""
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

    /*fun deleteFieldAction(registrationEvent: RegistrationEvent){
        when(registrationEvent){
            is RegistrationEvent.EmailChanged -> {
                _userState.value = _userState.value.copy(
                    email = ""
                )
            }
            is RegistrationEvent.NameChanged -> {
                _userState.value = _userState.value.copy(
                    name = ""
                )
            }
            is RegistrationEvent.SurnameChanged -> {
                _userState.value = _userState.value.copy(
                    surname = ""
                )
            }
            is RegistrationEvent.ZipCodeChanged -> {
                _userState.value = _userState.value.copy(
                    zipCode = ""
                )
            }
            is RegistrationEvent.PhoneNumberChanged -> {
                _userState.value = _userState.value.copy(
                    phoneNumber = ""
                )
            }
            is RegistrationEvent.CreditCardNumberChanged -> {
                _creditCardState.value = _creditCardState.value.copy(
                    creditCardNumber = ""
                )
            }
            is RegistrationEvent.IbanChanged -> {
                _creditCardState.value = _creditCardState.value.copy(
                    creditCardNumber = ""
                )
            }
            else -> {
                validateUserRegistration()
            }
        }
    }

    fun createAuctionAction(createAuctionEvents: CreateAuctionEvents){
        when(createAuctionEvents){
            is CreateAuctionEvents.ItemNameChanged -> {
                _itemState.value = _itemState.value.copy(
                    name = createAuctionEvents.itemName
                )
            }
            is CreateAuctionEvents.AuctionTypeChanged -> {
                _auctionState.value = _auctionState.value.copy(
                    auctionType = createAuctionEvents.auctionType
                )
            }
            is CreateAuctionEvents.DescriptionChanged -> {
                _auctionState.value = _auctionState.value.copy(
                    description = createAuctionEvents.description
                )
            }
            is CreateAuctionEvents.MinStepChanged -> {
                _auctionState.value = _auctionState.value.copy(
                    minStep = createAuctionEvents.minStep
                )
            }
            is CreateAuctionEvents.IntervalChanged -> {
                _auctionState.value = _auctionState.value.copy(
                    interval = createAuctionEvents.interval
                )
            }
            is CreateAuctionEvents.EndingDateChanged -> {
                _auctionState.value = _auctionState.value.copy(
                    endingDate = createAuctionEvents.endingDate
                )
            }
            is CreateAuctionEvents.MinAcceptedChanged ->{
                _auctionState.value = _auctionState.value.copy(
                    minAccepted = createAuctionEvents.minAccepted
                )
            }
            else -> {
                validateCreateAuction()
            }
        }
    }

    fun editContactInfoAction(editContactInfoEvents: EditContactInfoEvents){
        when(editContactInfoEvents){
            is EditContactInfoEvents.AddressChanged -> {
                _userState.value = _userState.value.copy(
                    address = editContactInfoEvents.address
                )
            }
            is EditContactInfoEvents.ZipCodeChanged -> {
                _userState.value = _userState.value.copy(
                    zipCode = editContactInfoEvents.zipcode
                )
            }
            is EditContactInfoEvents.CountryChanged -> {
                _userState.value = _userState.value.copy(
                    country = editContactInfoEvents.country
                )
            }
            is EditContactInfoEvents.PhoneNumberChanged -> {
                _userState.value = _userState.value.copy(
                    phoneNumber = editContactInfoEvents.phoneNumber
                )
            }
            else -> {
                validateEditContactInfo()
            }
        }
    }

    fun editProfileAction(editProfileEvent: EditProfileEvent){
        when(editProfileEvent){
            is EditProfileEvent.NameChanged -> {
                _userState.value = _userState.value.copy(
                    email = editProfileEvent.name
                )
            }
            is EditProfileEvent.PasswordChanged -> {
                _userState.value = _userState.value.copy(
                    password = editProfileEvent.password
                )
            }
            is EditProfileEvent.NewPasswordChanged -> {
                _userState.value = _userState.value.copy(
                    newPassword = editProfileEvent.newPassword
                )
            }
            is EditProfileEvent.DescriptionChanged -> {
                _userState.value = _userState.value.copy(
                    bio = editProfileEvent.description
                )
            }
            else -> {
                validateEditProfile()
            }
        }
    }

    fun loginAction(loginEvent: LoginEvent){
        when(loginEvent){
            is LoginEvent.EmailChanged -> {
                _userState.value = _userState.value.copy(
                    email = loginEvent.email
                )
            }
            is LoginEvent.PasswordChanged -> {
                _userState.value = _userState.value.copy(
                    password = loginEvent.password
                )
            }
            else -> {
                validateUserLogin()
            }
        }
    }

    fun registrationAction(registrationEvent: RegistrationEvent){
        when(registrationEvent){
            is RegistrationEvent.EmailChanged -> {
                _userState.value = _userState.value.copy(
                    email = registrationEvent.email
                )
            }
            is RegistrationEvent.NameChanged -> {
                _userState.value = _userState.value.copy(
                    name = registrationEvent.name
                )
            }
            is RegistrationEvent.SurnameChanged -> {
                _userState.value = _userState.value.copy(
                    surname = registrationEvent.surname
                )
            }
            is RegistrationEvent.PasswordChanged -> {
                _userState.value = _userState.value.copy(
                    password = registrationEvent.password
                )
            }
            is RegistrationEvent.NewPasswordChanged -> {
                _userState.value = _userState.value.copy(
                    newPassword = registrationEvent.newPassword
                )
            }
            is RegistrationEvent.AddressChanged -> {
                _userState.value = _userState.value.copy(
                    address = registrationEvent.address
                )
            }
            is RegistrationEvent.ZipCodeChanged -> {
                _userState.value = _userState.value.copy(
                    zipCode = registrationEvent.zipCode
                )
            }
            is RegistrationEvent.CountryChanged -> {
                _userState.value = _userState.value.copy(
                    country = registrationEvent.country
                )
            }
            is RegistrationEvent.PhoneNumberChanged -> {
                _userState.value = _userState.value.copy(
                    phoneNumber = registrationEvent.phoneNumber
                )
            }
            is RegistrationEvent.CreditCardNumberChanged -> {
                _creditCardState.value = _creditCardState.value.copy(
                    creditCardNumber = registrationEvent.creditCardNumber
                )
            }
            is RegistrationEvent.ExpirationDateChanged -> {
                _creditCardState.value = _creditCardState.value.copy(
                    creditCardNumber = registrationEvent.expirationDate
                )
            }
            is RegistrationEvent.CvvChanged -> {
                _creditCardState.value = _creditCardState.value.copy(
                    creditCardNumber = registrationEvent.cvv
                )
            }
            is RegistrationEvent.IbanChanged -> {
                _creditCardState.value = _creditCardState.value.copy(
                    creditCardNumber = registrationEvent.iban
                )
            }
            is RegistrationEvent.SellerChange -> {
                _userState.value = _userState.value.copy(
                    isSeller = registrationEvent.isSeller
                )
            }
            else -> {
                validateUserRegistration()
            }
        }
    }*/

    fun validateCreateAuction(){

    }

    fun validateEditContactInfo(){

    }

    fun validateEditProfile(){

    }

    fun validateUserLogin(){

    }

    fun validateUserRegistration(){
        /*ToDO a validation for every camp*/
    }
}