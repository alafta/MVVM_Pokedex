package com.example.newpokedexapp.data.remote.responses


data class Ability(
    val ability: com.example.newpokedexapp.data.remote.responses.AbilityX,
    val isHidden: Boolean,
    val slot: Int
)