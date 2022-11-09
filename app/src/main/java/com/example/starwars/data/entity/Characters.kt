package com.example.starwars.data.entity

data class Characters(
    val next: String?,
    val previous: Any?,
    val results: List<CharacterDto>
)
