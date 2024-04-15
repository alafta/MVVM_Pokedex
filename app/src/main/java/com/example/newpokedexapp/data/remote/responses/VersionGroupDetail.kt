package com.example.newpokedexapp.data.remote.responses

data class VersionGroupDetail(
    val levelLearnedAt: Int,
    val moveLearnMethod: com.example.newpokedexapp.data.remote.responses.MoveLearnMethod,
    val versionGroup: com.example.newpokedexapp.data.remote.responses.VersionGroup
)