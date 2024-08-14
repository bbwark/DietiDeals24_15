package com.CioffiDeVivo.dietideals.utils

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.CioffiDeVivo.dietideals.viewmodel.AddCardViewModel
import com.CioffiDeVivo.dietideals.viewmodel.BidHistoryViewModel
import com.CioffiDeVivo.dietideals.viewmodel.CreateAuctionViewModel
import com.CioffiDeVivo.dietideals.viewmodel.EditProfileViewModel
import com.CioffiDeVivo.dietideals.viewmodel.LogInCredentialsViewModel
import com.CioffiDeVivo.dietideals.viewmodel.MakeABidViewModel
import com.CioffiDeVivo.dietideals.viewmodel.RegisterCredentialsViewModel


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

            else -> throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}