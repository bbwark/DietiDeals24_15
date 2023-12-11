package com.CioffiDeVivo.dietideals.DataModels

import java.util.UUID

class Item(
    id: UUID,
    imagesUrl: Array<String> = arrayOf(),
    name: String,
    description: String?
) {
    val id: UUID = id
    var imagesUrl: Array<String> = imagesUrl
    var name: String = name
    var description: String? = description
}


/*
Creation of following data models and respective parameters and attributes:
- User
    - ID (UUID)
    - Name (String)
    - Email (String)
    - Password (String)
    - isSeller (Bool)
    - Bio (String?)
    - WebLinks (String?)
    - Address (String?)
    - PhoneNumber (String?)
    - CreditCards (Array<CreditCard>)
- CreditCard
    - Number (String)
    - CVV (String)
- Item
    - Images URL (Array<String>)
    - Name (String)
    - Description (String?)
 */