package com.example.imdumb.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ActorModel(
    val id: Int,
    val name: String,
    val character: String,
    val profilePath: String?
) : Parcelable
