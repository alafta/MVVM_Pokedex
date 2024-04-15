package com.example.newpokedexapp.data.remote.responses

data class HeldItem(
    val item: com.example.newpokedexapp.data.remote.responses.Item,
    val versionDetails: List<com.example.newpokedexapp.data.remote.responses.VersionDetail>
)