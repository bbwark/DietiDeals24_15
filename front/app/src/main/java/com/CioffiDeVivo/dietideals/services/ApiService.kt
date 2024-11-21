package com.CioffiDeVivo.dietideals.services


import android.content.Context
import com.CioffiDeVivo.dietideals.R
import com.CioffiDeVivo.dietideals.data.requestModels.Auction
import com.CioffiDeVivo.dietideals.data.requestModels.Bid
import com.CioffiDeVivo.dietideals.data.requestModels.CreditCard
import com.CioffiDeVivo.dietideals.data.requestModels.Item
import com.CioffiDeVivo.dietideals.data.requestModels.User
import com.google.gson.Gson
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.request.delete
import io.ktor.client.request.forms.MultiPartFormDataContent
import io.ktor.client.request.forms.formData
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.post
import io.ktor.client.request.put
import io.ktor.client.request.setBody
import io.ktor.client.request.url
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.bodyAsText
import io.ktor.http.ContentType
import io.ktor.http.Headers
import io.ktor.http.HttpHeaders
import io.ktor.http.isSuccess
import java.io.File
import java.io.IOException
import java.lang.IllegalStateException
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

object ApiService {

    private var _TOKEN: String? = null
    private var _URL: String? = null

    private class InitCheck<T>(private val initializer: () -> T) : ReadOnlyProperty<Any?, T> {
        private var value: T? = null
        override fun getValue(thisRef: Any?, property: KProperty<*>): T {
            return value ?: initializer().also { value = it }
        }
    }

    private val TOKEN by InitCheck { _TOKEN ?: throw IllegalStateException("ApiService not initialized correctly")}
    private val URL by InitCheck { _URL ?: throw IllegalStateException("ApiService not initialized correctly")}

    fun initialize(token: String, context: Context) {
        _TOKEN = token

        val baseUrl = context.getString(R.string.base_url)
        val port = context.getString(R.string.port)

        _URL = "${baseUrl}:${port}";
    }

/*
* ================== USER APIs ==================
*
* */

    //put mapping update user /users/{id}
    suspend fun updateUser(user: User): HttpResponse {
        var resultResponse: HttpResponse
        HttpClient(CIO).use {
            val gson = Gson()
            val userToUpdate = gson.toJson(user)
            val response = it.put {
                url("$URL/users/${user.id}")
                header(HttpHeaders.Authorization, "Bearer $TOKEN")
                header(HttpHeaders.ContentType, ContentType.Application.Json)
                setBody(userToUpdate)
            }
            resultResponse = response
        }
        return resultResponse
    }

    //get mapping get user /users/{id}
    suspend fun getUser(id: String): HttpResponse {
        var result: HttpResponse
        HttpClient(CIO).use {
            val response = it.get {
                url("$URL/users/$id")
                header(HttpHeaders.Authorization, "Bearer $TOKEN")
            }
            result = response
        }
        return result
    }

    //delete mapping delete user /users/{id}
    suspend fun deleteUser(id: String): HttpResponse {
        var result: HttpResponse
        HttpClient(CIO).use {
            val response = it.delete {
                url("$URL/users/${id}")
                header(HttpHeaders.Authorization, "Bearer $TOKEN")
            }
            result = response
        }
        return result
    }

    suspend fun getUserInfo(id: String): HttpResponse {
        var result: HttpResponse
        HttpClient(CIO).use {
            val response = it.get {
                url("$URL/users/info/${id}")
                header(HttpHeaders.Authorization, "Bearer $TOKEN")
            }
            result = response
        }
        return result
    }

/*
* ================== ITEM APIs ==================
*
* */


    //post mapping create item /items
    suspend fun createItem(item: Item): HttpResponse {
        var resultResponse: HttpResponse
        HttpClient(CIO).use {
            val gson = Gson()
            val postedItem = gson.toJson(item)
            val response = it.post {
                url("$URL/items")
                header(HttpHeaders.Authorization, "Bearer $TOKEN")
                header(HttpHeaders.ContentType, ContentType.Application.Json)
                setBody(postedItem)
            }
            resultResponse = response
        }
        return resultResponse;
    }

    //delete mapping item /items/{id}
    suspend fun deleteItem(id: String): HttpResponse {
        var result: HttpResponse
        HttpClient(CIO).use {
            val response = it.delete {
                url("$URL/items/${id}")
                header(HttpHeaders.Authorization, "Bearer $TOKEN")
            }
            result = response
        }
        return result
    }

/*
* ================== CREDIT CARD APIs ==================
*
* */


    //post mapping create credit card /credit_cards
    suspend fun createCreditCard(creditCard: CreditCard): HttpResponse {
        var resultResponse: HttpResponse
        HttpClient(CIO).use {
            val gson = Gson()
            val postedCreditCard = gson.toJson(creditCard)
            val response = it.post {
                url("$URL/credit_cards")
                header(HttpHeaders.Authorization, "Bearer $TOKEN")
                header(HttpHeaders.ContentType, ContentType.Application.Json)
                setBody(postedCreditCard)
            }
            resultResponse = response
        }
        return resultResponse;
    }


    //delete mapping delete credit card /credit_cards/{creditCardNumber}
    suspend fun deleteCreditCard(cardNumber: String): HttpResponse {
        var result: HttpResponse
        HttpClient(CIO).use {
            val response = it.delete {
                url("$URL/credit_cards/${cardNumber}")
                header(HttpHeaders.Authorization, "Bearer $TOKEN")
            }
            result = response
        }
        return result
    }

    //get mapping get credit cards by user id /credit_cards/user/{userId}
    suspend fun getCreditCardsByUserId(userId: String): Array<CreditCard> {
        var resultCreditCards: Array<CreditCard> = emptyArray()
        HttpClient(CIO).use {
            val gson = Gson()
            val response = it.get {
                url("$URL/credit_cards/user/$userId")
                header(HttpHeaders.Authorization, "Bearer $TOKEN")
            }
            if (response.status.isSuccess()) {
                resultCreditCards =
                    gson.fromJson(response.bodyAsText(), Array<CreditCard>::class.java)
            } else {
                throw IOException("HttpRequest Failed: $response")
            }
        }
        return resultCreditCards
    }

/*
* ================== BID APIs ==================
*
* */


    //post mapping create bid /bids
    suspend fun createBid(bid: Bid): HttpResponse {
        var resultResponse: HttpResponse
        HttpClient(CIO).use {
            val gson = Gson()
            val postedBid = gson.toJson(bid)
            val response = it.post {
                url("$URL/bids")
                header(HttpHeaders.Authorization, "Bearer $TOKEN")
                header(HttpHeaders.ContentType, ContentType.Application.Json)
                setBody(postedBid)
            }
            resultResponse = response
        }
        return resultResponse;
    }

    //delete mapping delete bid /bids/{id}
    suspend fun deleteBid(id: String): HttpResponse {
        var result: HttpResponse
        HttpClient(CIO).use {
            val response = it.delete {
                url("$URL/bids/${id}")
                header(HttpHeaders.Authorization, "Bearer $TOKEN")
            }
            result = response
        }
        return result
    }

    //get mapping get bid by auction id /bids/auction/{auctionId}
    suspend fun getBidsByAuctionId(auctionId: String): Array<Bid> {
        var resultBids: Array<Bid> = emptyArray()
        HttpClient(CIO).use {
            val gson = Gson()
            val response = it.get {
                url("$URL/bids/auction/$auctionId")
                header(HttpHeaders.Authorization, "Bearer $TOKEN")
            }
            if (response.status.isSuccess()) {
                resultBids = gson.fromJson(response.bodyAsText(), Array<Bid>::class.java)
            } else {
                throw IOException("HttpRequest Failed: $response")
            }
        }
        return resultBids
    }

    //post mapping choose winning bid /bids/chooseWinningBid
    suspend fun chooseWinningBid(bid: Bid): HttpResponse {
        var resultResponse: HttpResponse
        HttpClient(CIO).use {
            val gson = Gson()
            val postedBid = gson.toJson(bid)
            val response = it.post {
                url("${URL}/bids/chooseWinningBid")
                header(HttpHeaders.Authorization, "Bearer $TOKEN")
                header(HttpHeaders.ContentType, ContentType.Application.Json)
                setBody(postedBid)
            }
            resultResponse = response
        }
        return resultResponse;
    }


/*
* ================== AUCTION APIs ==================
*
* */


    //post mapping create auction /auctions
    suspend fun createAuction(auction: Auction): HttpResponse {
        var resultResponse: HttpResponse
        HttpClient(CIO).use {
            val gson = Gson()
            val postedAuction = gson.toJson(auction)
            val response = it.post {
                url("$URL/auctions")
                header(HttpHeaders.Authorization, "Bearer $TOKEN")
                header(HttpHeaders.ContentType, ContentType.Application.Json)
                setBody(postedAuction)
            }
            resultResponse = response
        }
        return resultResponse;
    }

    //put mapping update auction /auctions/{id}
    suspend fun updateAuction(auction: Auction): HttpResponse {
        var resultResponse: HttpResponse
        HttpClient(CIO).use {
            val gson = Gson()
            val updatedAuction = gson.toJson(auction)
            val response = it.put {
                url("$URL/auctions/${auction.id}")
                header(HttpHeaders.Authorization, "Bearer $TOKEN")
                header(HttpHeaders.ContentType, ContentType.Application.Json)
                setBody(updatedAuction)
            }
            resultResponse = response
        }
        return resultResponse
    }

    //get mapping get auction /auctions/{id}
    suspend fun getAuction(id: String): HttpResponse {
        var result: HttpResponse
        HttpClient(CIO).use {
            val response = it.get {
                url("$URL/auctions/$id")
                header(HttpHeaders.Authorization, "Bearer $TOKEN")
            }
            result = response
        }
        return result
    }

    //get mapping get auctionsByItemName /auctions/item/{name}
    suspend fun getAuctionsByItemName(name: String): Array<Auction> {
        var resultAuctions: Array<Auction> = emptyArray()
        HttpClient(CIO).use {
            val gson = Gson()
            val response = it.get {
                url("$URL/auctions/item/$name")
                header(HttpHeaders.Authorization, "Bearer $TOKEN")
            }
            if (response.status.isSuccess()) {
                resultAuctions = gson.fromJson(response.bodyAsText(), Array<Auction>::class.java)
            } else {
                throw IOException("HttpRequest Failed: $response")
            }
        }
        return resultAuctions
    }

    //get mapping get randomAuctions /auctions/owner/{id}
    suspend fun getRandomAuctions(ownerId: String): Array<Auction> {
        var resultAuctions: Array<Auction> = emptyArray()
        HttpClient(CIO).use {
            val gson = Gson()
            val response = it.get {
                url("$URL/auctions/owner/$ownerId")
                header(HttpHeaders.Authorization, "Bearer $TOKEN")
            }
            if (response.status.isSuccess()) {
                resultAuctions = gson.fromJson(response.bodyAsText(), Array<Auction>::class.java)
            } else {
                throw IOException("HttpRequest Failed: $response")
            }
        }
        return resultAuctions
    }

    //get mapping get auction bidders /auctions/bidders/{id}
    suspend fun getAuctionBidders(auctionId: String): Array<User> {
        var resultUsers: Array<User> = emptyArray()
        HttpClient(CIO).use {
            val gson = Gson()
            val response = it.get {
                url("$URL/auctions/bidders/$auctionId")
                header(HttpHeaders.Authorization, "Bearer $TOKEN")
            }
            if (response.status.isSuccess()) {
                resultUsers = gson.fromJson(response.bodyAsText(), Array<User>::class.java)
            } else {
                throw IOException("HttpRequest Failed: $response")
            }
        }
        return resultUsers
    }

    //get mapping get endingAuctions /auctions/ending/{userId}
    suspend fun getEndingAuctions(userId: String): Array<Auction> {
        var resultAuctions: Array<Auction> = emptyArray()
        HttpClient(CIO).use {
            val gson = Gson()
            val response = it.get {
                url("${URL}/auctions/ending/$userId")
                header(HttpHeaders.Authorization, "Bearer $TOKEN")
            }
            resultAuctions = gson.fromJson(response.bodyAsText(), Array<Auction>::class.java)
        }
        return resultAuctions
    }

    //get mapping get participatedAuctions /auctions/participated/{userId}
    suspend fun getParticipatedAuctions(userId: String): Array<Auction> {
        var resultAuctions: Array<Auction> = emptyArray()
        HttpClient(CIO).use {
            val gson = Gson()
            val response = it.get {
                url("${URL}/auctions/participated/$userId")
                header(HttpHeaders.Authorization, "Bearer $TOKEN")
            }
            resultAuctions = gson.fromJson(response.bodyAsText(), Array<Auction>::class.java)
        }
        return resultAuctions
    }

/*
* ================== IMAGES APIs ==================
*
* */

    //post mapping upload image /images/upload
    suspend fun uploadImage(file: File, fileName: String): HttpResponse {
        return HttpClient(CIO).use { client ->
            client.post {
                url("$URL/images/upload")
                header(HttpHeaders.Authorization, "Bearer $TOKEN")

                setBody(MultiPartFormDataContent(
                    formData {
                        append("file", file.readBytes(), Headers.build {
                            append(HttpHeaders.ContentDisposition, "filename=$fileName")
                            append(HttpHeaders.ContentType, "image/jpeg")
                        })
                    }
                ))
            }
        }
    }
}