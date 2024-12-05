package com.CioffiDeVivo.dietideals.data

import android.content.Context
import com.CioffiDeVivo.dietideals.R
import com.CioffiDeVivo.dietideals.data.network.apiServices.AuctionApiService
import com.CioffiDeVivo.dietideals.data.network.apiServices.AuthApiService
import com.CioffiDeVivo.dietideals.data.network.apiServices.BidApiService
import com.CioffiDeVivo.dietideals.data.network.apiServices.CreditCardApiService
import com.CioffiDeVivo.dietideals.data.network.apiServices.ImageApiService
import com.CioffiDeVivo.dietideals.data.network.apiServices.ItemApiService
import com.CioffiDeVivo.dietideals.data.network.interceptors.AuthInterceptor
import com.CioffiDeVivo.dietideals.data.network.apiServices.UserApiService
import com.CioffiDeVivo.dietideals.data.repositories.AuctionRepository
import com.CioffiDeVivo.dietideals.data.repositories.AuthRepository
import com.CioffiDeVivo.dietideals.data.repositories.BidRepository
import com.CioffiDeVivo.dietideals.data.repositories.CreditCardRepository
import com.CioffiDeVivo.dietideals.data.repositories.ImageRepository
import com.CioffiDeVivo.dietideals.data.repositories.ItemRepository
import com.CioffiDeVivo.dietideals.data.repositories.NetworkAuctionRepository
import com.CioffiDeVivo.dietideals.data.repositories.NetworkAuthRepository
import com.CioffiDeVivo.dietideals.data.repositories.NetworkBidRepository
import com.CioffiDeVivo.dietideals.data.repositories.NetworkCreditCardRepository
import com.CioffiDeVivo.dietideals.data.repositories.NetworkImageRepository
import com.CioffiDeVivo.dietideals.data.repositories.NetworkItemRepository
import com.CioffiDeVivo.dietideals.data.repositories.NetworkUserRepository
import com.CioffiDeVivo.dietideals.data.repositories.UserRepository
import com.CioffiDeVivo.dietideals.utils.LocalDateTimeAdapter
import com.CioffiDeVivo.dietideals.utils.ZonedDateTimeAdapter
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.security.KeyStore
import java.security.cert.CertificateFactory
import java.time.LocalDateTime
import java.time.ZonedDateTime
import java.util.concurrent.TimeUnit
import javax.net.ssl.SSLContext
import javax.net.ssl.SSLSocketFactory
import javax.net.ssl.TrustManagerFactory
import javax.net.ssl.X509TrustManager

interface AppContainer {
    val userRepository: UserRepository
    val authRepository: AuthRepository
    val itemRepository: ItemRepository
    val creditCardRepository: CreditCardRepository
    val bidRepository: BidRepository
    val auctionRepository: AuctionRepository
    val imageRepository: ImageRepository
}

class DefaultAppContainer(
    userPreferencesRepository: UserPreferencesRepository,
    private val context: Context
): AppContainer{

    private val baseUrl = "https://16.171.206.112:8181"

    private val customGson: Gson = GsonBuilder()
        .registerTypeAdapter(LocalDateTime::class.java, LocalDateTimeAdapter())
        .registerTypeAdapter(ZonedDateTime::class.java, ZonedDateTimeAdapter())
        .create()

    private val sslSocketFactory = createSSLSocketFactory()

    private val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    private val client = OkHttpClient.Builder()
        .sslSocketFactory(sslSocketFactory.first, sslSocketFactory.second)
        .hostnameVerifier { hostname, _ ->
            hostname == "16.171.206.112"
        }
        .addInterceptor(AuthInterceptor(userPreferencesRepository))
        .addInterceptor(loggingInterceptor)
        .connectTimeout(30, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .build()

    private val retrofit = Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(ScalarsConverterFactory.create())
        .addConverterFactory(GsonConverterFactory.create(customGson))
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

    private val imageApiService: ImageApiService by lazy {
        retrofit.create(ImageApiService::class.java)
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
    override val imageRepository: ImageRepository by lazy {
        NetworkImageRepository(imageApiService)
    }

    private fun createSSLSocketFactory(): Pair<SSLSocketFactory, X509TrustManager> {
        val certificateFactory = CertificateFactory.getInstance("X.509")
        context.resources.openRawResource(R.raw.dieti_deals).use { certificateInput ->
            val certificate = certificateFactory.generateCertificate(certificateInput)
            val keyStore = KeyStore.getInstance(KeyStore.getDefaultType()).apply {
                load(null, null)
                setCertificateEntry("dieti_deals", certificate)
            }
            val trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm()).apply {
                init(keyStore)
            }
            val trustManager = trustManagerFactory.trustManagers[0] as X509TrustManager
            val sslContext = SSLContext.getInstance("TLS").apply {
                init(null, arrayOf(trustManager), null)
            }
            return Pair(sslContext.socketFactory, trustManager)
        }
    }

}