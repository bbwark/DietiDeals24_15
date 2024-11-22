package com.CioffiDeVivo.dietideals.utils

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.CioffiDeVivo.dietideals.data.AppContainer
import com.CioffiDeVivo.dietideals.data.UserPreferencesRepository
import com.CioffiDeVivo.dietideals.presentation.ui.home.HomeViewModel
import com.CioffiDeVivo.dietideals.presentation.ui.login.LogInViewModel
import com.CioffiDeVivo.dietideals.presentation.ui.loginCredentials.LogInCredentialsViewModel

class AppViewModelFactory(
    private val userPreferencesRepository: UserPreferencesRepository,
    private val appContainer: AppContainer
): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(LogInViewModel::class.java) ->
                LogInViewModel(userPreferencesRepository) as T

            modelClass.isAssignableFrom(LogInCredentialsViewModel::class.java) ->
                LogInCredentialsViewModel(userPreferencesRepository, appContainer.authRepository) as T

            modelClass.isAssignableFrom(HomeViewModel::class.java) ->
                HomeViewModel(userPreferencesRepository, appContainer.userRepository) as T

            else -> throw IllegalArgumentException("Unknown ViewModel Class!")
        }
    }
}

class GenericViewModelFactory(private val application: Application) : ViewModelProvider.Factory {
    /*override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(RegisterCredentialsViewModel::class.java) ->
                RegisterCredentialsViewModel(application) as T

            /*modelClass.isAssignableFrom(LogInCredentialsViewModel::class.java) ->
                LogInCredentialsViewModel(application) as T*/

            modelClass.isAssignableFrom(CreateAuctionViewModel::class.java) ->
                CreateAuctionViewModel(application) as T

            modelClass.isAssignableFrom(MakeABidViewModel::class.java) ->
                MakeABidViewModel(application) as T

            modelClass.isAssignableFrom(BidHistoryViewModel::class.java) ->
                BidHistoryViewModel(application) as T

            modelClass.isAssignableFrom(EditProfileViewModel::class.java) ->
                EditProfileViewModel(application) as T

            modelClass.isAssignableFrom(AddCardViewModel::class.java) ->
                AddCardViewModel(application) as T

            modelClass.isAssignableFrom(AuctionViewModel::class.java) ->
                AuctionViewModel(application) as T

            modelClass.isAssignableFrom(EditContactInfoViewModel::class.java) ->
                EditContactInfoViewModel(application) as T

            modelClass.isAssignableFrom(FavouritesViewModel::class.java) ->
                FavouritesViewModel(application) as T

            /*modelClass.isAssignableFrom(HomeViewModel::class.java) ->
                HomeViewModel(application) as T*/

            modelClass.isAssignableFrom(ManageCardsViewModel::class.java) ->
                ManageCardsViewModel(application) as T

            modelClass.isAssignableFrom(SearchViewModel::class.java) ->
                SearchViewModel(application) as T

            modelClass.isAssignableFrom(SellViewModel::class.java) ->
                SellViewModel(application) as T

            modelClass.isAssignableFrom(AccountViewModel::class.java) ->
                AccountViewModel(application) as T

            modelClass.isAssignableFrom(BecomeSellerViewModel::class.java) ->
                BecomeSellerViewModel(application) as T

            modelClass.isAssignableFrom(LogInViewModel::class.java) ->
                LogInViewModel(application) as T

            else -> throw IllegalArgumentException("Unknown ViewModel class")
        }
    }*/
}