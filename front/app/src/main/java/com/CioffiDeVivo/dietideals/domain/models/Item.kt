package com.CioffiDeVivo.dietideals.domain.models

data class Item(
    val id: String = "",
    var name: String = "",
    val imagesUri: List<String> = emptyList()
)