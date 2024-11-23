package com.CioffiDeVivo.dietideals.utils

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.CioffiDeVivo.dietideals.data.AppContainer
import com.CioffiDeVivo.dietideals.data.UserPreferencesRepository
import com.CioffiDeVivo.dietideals.presentation.ui.account.AccountViewModel
import com.CioffiDeVivo.dietideals.presentation.ui.addCard.AddCardViewModel
import com.CioffiDeVivo.dietideals.presentation.ui.auction.AuctionViewModel
import com.CioffiDeVivo.dietideals.presentation.ui.becomeSeller.BecomeSellerViewModel
import com.CioffiDeVivo.dietideals.presentation.ui.bidHistory.BidHistoryViewModel
import com.CioffiDeVivo.dietideals.presentation.ui.createAuction.CreateAuctionViewModel
import com.CioffiDeVivo.dietideals.presentation.ui.editContactInfo.EditContactInfoViewModel
import com.CioffiDeVivo.dietideals.presentation.ui.editProfile.EditProfileViewModel
import com.CioffiDeVivo.dietideals.presentation.ui.favourites.FavouritesViewModel
import com.CioffiDeVivo.dietideals.presentation.ui.home.HomeViewModel
import com.CioffiDeVivo.dietideals.presentation.ui.login.LogInViewModel
import com.CioffiDeVivo.dietideals.presentation.ui.loginCredentials.LogInCredentialsViewModel
import com.CioffiDeVivo.dietideals.presentation.ui.makeBid.MakeABidViewModel
import com.CioffiDeVivo.dietideals.presentation.ui.manageCards.ManageCardsViewModel
import com.CioffiDeVivo.dietideals.presentation.ui.registerCredentials.RegisterCredentialsViewModel
import com.CioffiDeVivo.dietideals.presentation.ui.search.SearchViewModel
import com.CioffiDeVivo.dietideals.presentation.ui.sell.SellViewModel

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
                HomeViewModel(userPreferencesRepository, appContainer.auctionRepository) as T

            modelClass.isAssignableFrom(RegisterCredentialsViewModel::class.java) ->
                RegisterCredentialsViewModel(userPreferencesRepository, appContainer.authRepository) as T

            modelClass.isAssignableFrom(CreateAuctionViewModel::class.java) ->
                CreateAuctionViewModel(userPreferencesRepository, appContainer.auctionRepository, appContainer.imageRepository) as T

            modelClass.isAssignableFrom(MakeABidViewModel::class.java) ->
                MakeABidViewModel(userPreferencesRepository, appContainer.auctionRepository, appContainer.bidRepository) as T

            modelClass.isAssignableFrom(BidHistoryViewModel::class.java) ->
                BidHistoryViewModel(userPreferencesRepository, appContainer.auctionRepository, appContainer.bidRepository) as T

            modelClass.isAssignableFrom(EditProfileViewModel::class.java) ->
                EditProfileViewModel(userPreferencesRepository, appContainer.userRepository) as T

            modelClass.isAssignableFrom(AddCardViewModel::class.java) ->
                AddCardViewModel(userPreferencesRepository, appContainer.creditCardRepository) as T

            modelClass.isAssignableFrom(AuctionViewModel::class.java) ->
                AuctionViewModel(userPreferencesRepository, appContainer.auctionRepository, appContainer.userRepository) as T

            modelClass.isAssignableFrom(EditContactInfoViewModel::class.java) ->
                EditContactInfoViewModel(userPreferencesRepository, appContainer.userRepository) as T

            modelClass.isAssignableFrom(FavouritesViewModel::class.java) ->
                FavouritesViewModel(userPreferencesRepository, appContainer.userRepository) as T

            modelClass.isAssignableFrom(ManageCardsViewModel::class.java) ->
                ManageCardsViewModel(userPreferencesRepository, appContainer.userRepository, appContainer.creditCardRepository) as T

            modelClass.isAssignableFrom(SearchViewModel::class.java) ->
                SearchViewModel(appContainer.auctionRepository) as T

            modelClass.isAssignableFrom(SellViewModel::class.java) ->
                SellViewModel(userPreferencesRepository, appContainer.userRepository) as T

            modelClass.isAssignableFrom(AccountViewModel::class.java) ->
                AccountViewModel(userPreferencesRepository) as T

            modelClass.isAssignableFrom(BecomeSellerViewModel::class.java) ->
                BecomeSellerViewModel(userPreferencesRepository, appContainer.userRepository) as T

            else -> throw IllegalArgumentException("Unknown ViewModel Class!")
        }
    }
}