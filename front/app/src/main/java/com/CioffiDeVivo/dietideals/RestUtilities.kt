package com.CioffiDeVivo.dietideals

import com.CioffiDeVivo.dietideals.domain.RequestModels.Auction
import com.CioffiDeVivo.dietideals.domain.RequestModels.Bid
import com.CioffiDeVivo.dietideals.domain.RequestModels.CreditCard
import com.CioffiDeVivo.dietideals.domain.RequestModels.Item
import com.CioffiDeVivo.dietideals.domain.RequestModels.User
import com.google.gson.Gson
import com.google.gson.JsonObject
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.post
import io.ktor.client.request.put
import io.ktor.client.request.setBody
import io.ktor.client.request.url
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.bodyAsText
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders


//TODO Import correct URL from APP properties
const val BASE_URL = "localhost"
const val PORT = "8181"
const val URL = "http://${BASE_URL}:${PORT}"

/*
* ================== USER APIs ==================
*
* */


//post mapping create user /users
suspend fun createUser(user: User, token: String): HttpResponse {
    var resultResponse: HttpResponse
    HttpClient(CIO).use {
        val gson = Gson()
        val postedUser = gson.toJson(user)
        val response = it.post {
            url("${URL}/users")
            header(HttpHeaders.Authorization, "Bearer $token")
            header(HttpHeaders.ContentType, ContentType.Application.Json)
            setBody(postedUser)
        }
        resultResponse = response
    }
    return resultResponse;
}

//put mapping update user /users/{id}
suspend fun updateUser(user: User, token: String): HttpResponse {
    var resultResponse: HttpResponse
    HttpClient(CIO).use {
        val gson = Gson()
        val userToUpdate = gson.toJson(user)
        val response = it.put {
            url("${URL}/users/${user.id}")
            header(HttpHeaders.Authorization, "Bearer $token")
            header(HttpHeaders.ContentType, ContentType.Application.Json)
            setBody(userToUpdate)
        }
        resultResponse = response
    }
    return resultResponse
}

//get mapping get user /users/{id}
suspend fun getUser(id: String, token: String): User {
    var resultUser: User
    HttpClient(CIO).use {
        val gson = Gson()
        val response = it.get {
            url("${URL}/users/$id")
            header(HttpHeaders.Authorization, "Bearer $token")
        }
        resultUser = gson.fromJson(response.bodyAsText(), User::class.java)

    }
    return resultUser
}

//delete mapping delete user /users/{id}
suspend fun deleteUser(id: String, token: String): HttpResponse {
    var result: HttpResponse
    HttpClient(CIO).use {
        val response = it.delete {
            url("${URL}/users/${id}")
            header(HttpHeaders.Authorization, "Bearer $token")
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
suspend fun createItem(item: Item, token: String): HttpResponse {
    var resultResponse: HttpResponse
    HttpClient(CIO).use {
        val gson = Gson()
        val postedItem = gson.toJson(item)
        val response = it.post {
            url("${URL}/items")
            header(HttpHeaders.Authorization, "Bearer $token")
            header(HttpHeaders.ContentType, ContentType.Application.Json)
            setBody(postedItem)
        }
        resultResponse = response
    }
    return resultResponse;
}

//delete mapping item /items/{id}
suspend fun deleteItem(id: String, token: String): HttpResponse {
    var result: HttpResponse
    HttpClient(CIO).use {
        val response = it.delete {
            url("${URL}/items/${id}")
            header(HttpHeaders.Authorization, "Bearer $token")
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
suspend fun createCreditCard(creditCard: CreditCard, token: String): HttpResponse {
    var resultResponse: HttpResponse
    HttpClient(CIO).use {
        val gson = Gson()
        val postedCreditCard = gson.toJson(creditCard)
        val response = it.post {
            url("${URL}/credit_cards")
            header(HttpHeaders.Authorization, "Bearer $token")
            header(HttpHeaders.ContentType, ContentType.Application.Json)
            setBody(postedCreditCard)
        }
        resultResponse = response
    }
    return resultResponse;
}


//delete mapping delete credit card /credit_cards/{creditCardNumber}
suspend fun deleteCreditCard(cardNumber: String, token: String): HttpResponse {
    var result: HttpResponse
    HttpClient(CIO).use {
        val response = it.delete {
            url("${URL}/credit_cards/${cardNumber}")
            header(HttpHeaders.Authorization, "Bearer $token")
        }
        result = response
    }
    return result
}

//get mapping get credit cards by user id /credit_cards/user/{userId}
suspend fun getCreditCardsByUserId(userId: String, token: String): Array<CreditCard> {
    var resultCreditCards: Array<CreditCard> = emptyArray()
    HttpClient(CIO).use {
        val gson = Gson()
        val response = it.get {
            url("${URL}/credit_cards/user/$userId")
            header(HttpHeaders.Authorization, "Bearer $token")
        }
        println(response)
        resultCreditCards = gson.fromJson(response.bodyAsText(), Array<CreditCard>::class.java)
    }
    return resultCreditCards
}

/*
* ================== BID APIs ==================
*
* */


//post mapping create bid /bids
suspend fun createBid(bid: Bid, token: String): HttpResponse {
    var resultResponse: HttpResponse
    HttpClient(CIO).use {
        val gson = Gson()
        val postedBid = gson.toJson(bid)
        val response = it.post {
            url("${URL}/bids")
            header(HttpHeaders.Authorization, "Bearer $token")
            header(HttpHeaders.ContentType, ContentType.Application.Json)
            setBody(postedBid)
        }
        resultResponse = response
    }
    return resultResponse;
}

//delete mapping delete bid /bids/{id}
suspend fun deleteBid(id: String, token: String): HttpResponse {
    var result: HttpResponse
    HttpClient(CIO).use {
        val response = it.delete {
            url("${URL}/bids/${id}")
            header(HttpHeaders.Authorization, "Bearer $token")
        }
        result = response
    }
    return result
}

//get mapping get bid by auction id /bids/auction/{auctionId}
suspend fun getBidsByAuctionId(auctionId: String, token: String): Array<Bid> {
    var resultBids: Array<Bid> = emptyArray()
    HttpClient(CIO).use {
        val gson = Gson()
        val response = it.get {
            url("${URL}/bids/auction/$auctionId")
            header(HttpHeaders.Authorization, "Bearer $token")
        }
        resultBids = gson.fromJson(response.bodyAsText(), Array<Bid>::class.java)
    }
    return resultBids
}

/*
* ================== AUTH APIs ==================
*
* */


//post mapping register /auth/registerUser   (in user)
suspend fun registerUser(user: User): HttpResponse {
    var result: HttpResponse
    HttpClient(CIO).use {
        val gson = Gson()
        val userToRegister = gson.toJson(user)
        val response = it.post {
            url("${URL}/auth/registerUser")
            header(HttpHeaders.ContentType, ContentType.Application.Json)
            setBody(userToRegister)
        }
        result = response
    }
    return result
}

//post mapping login /auth/loginUser   (out JWT in email & password)
suspend fun loginUser(email: String, password: String): String {
    var result: String
    val login = object {
        val email = email
        val password = password
    }
    HttpClient(CIO).use {
        val gson = Gson()
        val body = gson.toJson(login)
        val response = it.post {
            url("${URL}/auth/loginUser")
            header(HttpHeaders.ContentType, ContentType.Application.Json)
            setBody(body)
        }
        val jsonObject = gson.fromJson(response.bodyAsText(), JsonObject::class.java)
        result = jsonObject.get("jwt").asString
    }
    return result;
}

/*
* ================== AUCTION APIs ==================
*
* */


//post mapping create auction /auctions
suspend fun createAuction(auction: Auction, token: String): HttpResponse {
    var resultResponse: HttpResponse
    HttpClient(CIO).use {
        val gson = Gson()
        val postedAuction = gson.toJson(auction)
        val response = it.post {
            url("${URL}/auctions")
            header(HttpHeaders.Authorization, "Bearer $token")
            header(HttpHeaders.ContentType, ContentType.Application.Json)
            setBody(postedAuction)
        }
        resultResponse = response
    }
    return resultResponse;
}

//put mapping update auction /auctions/{id}
suspend fun updateAuction(auction: Auction, token: String): HttpResponse {
    var resultResponse: HttpResponse
    HttpClient(CIO).use {
        val gson = Gson()
        val updatedAuction = gson.toJson(auction)
        val response = it.put {
            url("${URL}/auctions/${auction.id}")
            header(HttpHeaders.Authorization, "Bearer $token")
            header(HttpHeaders.ContentType, ContentType.Application.Json)
            setBody(updatedAuction)
        }
        resultResponse = response
    }
    return resultResponse
}

//get mapping get auction /auctions/{id}
suspend fun getAuction(id: String, token: String): Auction {
    var resultAuction: Auction
    HttpClient(CIO).use {
        val gson = Gson()
        val response = it.get {
            url("${URL}/auctions/$id")
            header(HttpHeaders.Authorization, "Bearer $token")
        }
        resultAuction = gson.fromJson(response.bodyAsText(), Auction::class.java)
    }
    return resultAuction
}

//get mapping get auctionsByItemName /auctions/item/{name}
suspend fun getAuctionsByItemName(name: String, token: String): Array<Auction>{
    var resultAuctions: Array<Auction> = emptyArray()
    HttpClient(CIO).use {
        val gson = Gson()
        val response = it.get {
            url("${URL}/auctions/item/$name")
            header(HttpHeaders.Authorization, "Bearer $token")
        }
        resultAuctions = gson.fromJson(response.bodyAsText(), Array<Auction>::class.java)
    }
    return resultAuctions
}

//get mapping get randomAuctions /auctions/owner/{id}
suspend fun getRandomAuctions(ownerId: String, token: String): Array<Auction> {
    var resultAuctions: Array<Auction> = emptyArray()
    HttpClient(CIO).use {
        val gson = Gson()
        val response = it.get {
            url("${URL}/auctions/owner/$ownerId")
            header(HttpHeaders.Authorization, "Bearer $token")
        }
        resultAuctions = gson.fromJson(response.bodyAsText(), Array<Auction>::class.java)
    }
    return resultAuctions
}