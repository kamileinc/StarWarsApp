package com.example.starwars.data.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Character(
    val name: String,
    val gender: String,
    val height: String,
    val films: List<String>,
    val url: String?
) : Parcelable
