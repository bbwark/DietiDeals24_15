package com.CioffiDeVivo.dietideals.data

import com.CioffiDeVivo.dietideals.data.network.apiServices.AuctionApiService
import com.CioffiDeVivo.dietideals.data.network.apiServices.AuthApiService
import com.CioffiDeVivo.dietideals.data.network.apiServices.BidApiService
import com.CioffiDeVivo.dietideals.data.network.apiServices.CreditCardApiService
import com.CioffiDeVivo.dietideals.data.network.apiServices.ItemApiService
import com.CioffiDeVivo.dietideals.data.network.interceptors.AuthInterceptor
import com.CioffiDeVivo.dietideals.data.network.apiServices.UserApiService
import com.CioffiDeVivo.dietideals.data.repositories.AuctionRepository
import com.CioffiDeVivo.dietideals.data.repositories.AuthRepository
import com.CioffiDeVivo.dietideals.data.repositories.BidRepository
import com.CioffiDeVivo.dietideals.data.repositories.CreditCardRepository
import com.CioffiDeVivo.dietideals.data.repositories.ItemRepository
import com.CioffiDeVivo.dietideals.data.repositories.NetworkAuctionRepository
import com.CioffiDeVivo.dietideals.data.repositories.NetworkAuthRepository
import com.CioffiDeVivo.dietideals.data.repositories.NetworkBidRepository
import com.CioffiDeVivo.dietideals.data.repositories.NetworkCreditCardRepository
import com.CioffiDeVivo.dietideals.data.repositories.NetworkItemRepository
import com.CioffiDeVivo.dietideals.data.repositories.NetworkUserRepository
import com.CioffiDeVivo.dietideals.data.repositories.UserRepository
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

interface AppContainer {
    val userRepository: UserRepository
    val authRepository: AuthRepository
    val itemRepository: ItemRepository
    val creditCardRepository: CreditCardRepository
    val bidRepository: BidRepository
    val auctionRepository: AuctionRepository
}

class DefaultAppContainer(userPreferencesRepository: UserPreferencesRepository): AppContainer{

    private val baseUrl = "http://16.171.206.112:8181"

    private val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    private val client = OkHttpClient.Builder()
        .addInterceptor(AuthInterceptor(userPreferencesRepository))
        .addInterceptor(loggingInterceptor)
        .build()

    private val retrofit = Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(GsonConverterFactory.create())
        .client(client)
        .build()

    // API SERVICES
    private val userApiService: UserApiService by lazy {
        retrofit.create(UserApiService::class.java)
    }

    private val authApiService: AuthApiService by lazy {
        retrofit.create(AuthApiService::class.java)
    }

    private val itemApiService: ItemApiService by lazy {
        retrofit.create(ItemApiService::class.java)
    }

    private val creditCardApiService: CreditCardApiService by lazy {
        retrofit.create(CreditCardApiService::class.java)
    }

    private val bidApiService: BidApiService by lazy {
        retrofit.create(BidApiService::class.java)
    }

    private val auctionApiService: AuctionApiService by lazy {
        retrofit.create(AuctionApiService::class.java)
    }

    //REPOSITORIES
    override val userRepository: UserRepository by lazy {
        NetworkUserRepository(userApiService)
    }

    override val authRepository: AuthRepository by lazy {
        NetworkAuthRepository(authApiService)
    }

    override val itemRepository: ItemRepository by lazy {
        NetworkItemRepository(itemApiService)
    }

    override val creditCardRepository: CreditCardRepository by lazy {
        NetworkCreditCardRepository(creditCardApiService)
    }

    override val bidRepository: BidRepository by lazy {
        NetworkBidRepository(bidApiService)
    }
    override val auctionRepository: AuctionRepository by lazy {
        NetworkAuctionRepository(auctionApiService)
    }

}