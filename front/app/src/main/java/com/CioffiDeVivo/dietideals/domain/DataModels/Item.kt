package com.CioffiDeVivo.dietideals.domain.DataModels

import android.net.Uri
import java.util.UUID

data class Item(
    val id: UUID = UUID.randomUUID(),
    val name: String = "",
    val imagesUri: List<Uri?> = emptyList()
)