package com.CioffiDeVivo.dietideals.domain.DataModels

import android.net.Uri

data class Item(
    val id: String = "",
    var name: String = "",
    val imagesUri: List<Uri?> = emptyList()
)