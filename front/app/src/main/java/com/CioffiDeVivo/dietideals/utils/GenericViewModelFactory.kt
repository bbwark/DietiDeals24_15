package com.CioffiDeVivo.dietideals.utils

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.CioffiDeVivo.dietideals.presentation.ui.addCard.AddCardViewModel
import com.CioffiDeVivo.dietideals.presentation.ui.auction.AuctionViewModel
import com.CioffiDeVivo.dietideals.presentation.ui.bidHistory.BidHistoryViewModel
import com.CioffiDeVivo.dietideals.presentation.ui.createAuction.CreateAuctionViewModel
import com.CioffiDeVivo.dietideals.presentation.ui.editContactInfo.EditContactInfoViewModel
import com.CioffiDeVivo.dietideals.presentation.ui.editProfile.EditProfileViewModel
import com.CioffiDeVivo.dietideals.presentation.ui.favourites.FavouritesViewModel
import com.CioffiDeVivo.dietideals.presentation.ui.home.HomeViewModel
import com.CioffiDeVivo.dietideals.presentation.ui.loginCredentials.LogInCredentialsViewModel
import com.CioffiDeVivo.dietideals.presentation.ui.makeBid.MakeABidViewModel
import com.CioffiDeVivo.dietideals.presentation.ui.manageCards.ManageCardsViewModel
import com.CioffiDeVivo.dietideals.presentation.ui.registerCredentials.RegisterCredentialsViewModel
import com.CioffiDeVivo.dietideals.presentation.ui.search.SearchViewModel
import com.CioffiDeVivo.dietideals.presentation.ui.sell.SellViewModel
import com.CioffiDeVivo.dietideals.presentation.common.sharedViewmodels.SharedViewModel
import com.CioffiDeVivo.dietideals.presentation.ui.account.AccountViewModel
import com.CioffiDeVivo.dietideals.presentation.ui.becomeSeller.BecomeSellerViewModel


class GenericViewModelFactory(private val application: Application) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(RegisterCredentialsViewModel::class.java) ->
                RegisterCredentialsViewModel(application) as T

            modelClass.isAssignableFrom(LogInCredentialsViewModel::class.java) ->
                LogInCredentialsViewModel(application) as T

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

            modelClass.isAssignableFrom(HomeViewModel::class.java) ->
                HomeViewModel(application) as T

            modelClass.isAssignableFrom(ManageCardsViewModel::class.java) ->
                ManageCardsViewModel(application) as T

            modelClass.isAssignableFrom(SearchViewModel::class.java) ->
                SearchViewModel(application) as T

            modelClass.isAssignableFrom(SharedViewModel::class.java) ->
                SharedViewModel(application) as T

            modelClass.isAssignableFrom(SellViewModel::class.java) ->
                SellViewModel(application) as T

            modelClass.isAssignableFrom(AccountViewModel::class.java) ->
                AccountViewModel(application) as T

            modelClass.isAssignableFrom(BecomeSellerViewModel::class.java) ->
                BecomeSellerViewModel(application) as T

            else -> throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}