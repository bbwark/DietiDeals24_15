package com.CioffiDeVivo.dietideals.DataModels

import java.util.UUID

data class Item(
    val id: UUID = UUID.randomUUID(),
    val name: String = "",
    val imagesUrl: Array<String> = arrayOf()
)