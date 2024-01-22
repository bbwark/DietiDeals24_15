package com.CioffiDeVivo.dietideals

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.CioffiDeVivo.dietideals.DataModels.Auction
import com.CioffiDeVivo.dietideals.DataModels.AuctionType
import com.CioffiDeVivo.dietideals.DataModels.CreditCard
import com.CioffiDeVivo.dietideals.DataModels.CreditCardTest
import com.CioffiDeVivo.dietideals.DataModels.Item
import com.CioffiDeVivo.dietideals.DataModels.RegistrationEvent
import com.CioffiDeVivo.dietideals.DataModels.User
import com.CioffiDeVivo.dietideals.DataModels.UserTest
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.time.LocalDate
import java.util.UUID

@RequiresApi(Build.VERSION_CODES.O)
class DietiDealsViewModel : ViewModel() {

    private val _userState = MutableStateFlow(UserTest())
    val userState: StateFlow<UserTest> = _userState.asStateFlow()
    private val _creditCardState = MutableStateFlow(CreditCardTest())
    val creditCardState: StateFlow<CreditCardTest> = _creditCardState.asStateFlow()

    var auctionOpenByOwner by mutableStateOf(false)
    var user by mutableStateOf(
        User(
            UUID.randomUUID(),
            "Nametest Surnametest",
            "",
            "passwordtest",
            creditCards = arrayOf(
                CreditCard("556666666666", "222", LocalDate.now().plusYears(1)),
                CreditCard("456666666666", "222", LocalDate.now().plusYears(2)),
                CreditCard("356666666666", "222", LocalDate.now().plusYears(2))
            )
        )
    )
    var selectedNavBarItem: MutableState<Int> = mutableStateOf(0)
    var selectedAuction by mutableStateOf(
        Auction(
            UUID.randomUUID(),
            UUID.randomUUID(),
            Item(id = UUID.randomUUID(), name = ""),
            endingDate = LocalDate.now(),
            expired = false,
            auctionType = AuctionType.English
        )
    )
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

    fun onAction(registrationEvent: RegistrationEvent){
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

            else -> {
                validateUserRegistration()
            }
        }
    }
    fun validateUserRegistration(){
        /*ToDO a validation for every camp*/
    }
}