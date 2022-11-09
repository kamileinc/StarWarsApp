package com.example.starwars.data.entity

data class CharactersDto(
    val count: Int,
    val next: String?,
    val previous: Any?,
    val results: List<CharacterDto>
)

fun CharactersDto.toCharacters(): Characters {
    return Characters(
        next = next,
        previous = previous,
        results = results
    )
}