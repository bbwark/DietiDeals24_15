package com.CioffiDeVivo.dietideals.data.models

data class User(
    val id: String = "",
    val name: String = "",
    val email: String = "",
    val password: String = "",
    val isSeller: Boolean = false,
    val favouriteAuctions: Array<Auction> = arrayOf(),
    val ownedAuctions: Array<Auction> = arrayOf(),
    val bio: String = "",
    val address: String = "",
    val zipcode: String = "",
    val country: Country? = Country.Italy,
    val phoneNumber: String = "",
    val creditCards: Array<CreditCard> = arrayOf(),
    val deviceTokens: Array<String> = arrayOf()
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as User

        if (!favouriteAuctions.contentEquals(other.favouriteAuctions)) return false
        if (!ownedAuctions.contentEquals(other.ownedAuctions)) return false
        if (!creditCards.contentEquals(other.creditCards)) return false
        return deviceTokens.contentEquals(other.deviceTokens)
    }

    override fun hashCode(): Int {
        var result = favouriteAuctions.contentHashCode()
        result = 31 * result + ownedAuctions.contentHashCode()
        result = 31 * result + creditCards.contentHashCode()
        result = 31 * result + deviceTokens.contentHashCode()
        return result
    }
}

enum class Country{
    Italy,
    Spain,
    Germany,
    France,
    Belgium
}