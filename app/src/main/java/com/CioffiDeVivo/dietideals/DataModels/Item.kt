package com.CioffiDeVivo.dietideals.DataModels

import java.util.UUID

class Item(
    id: UUID,
    imagesUrl: Array<String> = arrayOf(),
    name: String,
    description: String? = null
) {
    val id: UUID = id
    var imagesUrl: Array<String> = imagesUrl
    var name: String = name
    var description: String? = description
}