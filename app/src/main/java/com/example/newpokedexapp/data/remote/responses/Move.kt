package com.example.newpokedexapp.data.remote.responses

data class Move(
    val move: com.example.newpokedexapp.data.remote.responses.MoveX,
    val versionGroupDetails: List<com.example.newpokedexapp.data.remote.responses.VersionGroupDetail>
)