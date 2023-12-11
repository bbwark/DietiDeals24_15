package com.CioffiDeVivo.dietideals.DataModels

class Item(
    imagesUrl: Array<String> = arrayOf(),
    name: String,
    description: String?
) {
    var imagesUrl: Array<String> = imagesUrl
    var name: String = name
    var description: String? = description
}