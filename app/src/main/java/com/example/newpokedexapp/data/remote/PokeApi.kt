package com.example.newpokedexapp.data.remote


import com.example.newpokedexapp.data.remote.responses.Pokemon
import com.example.newpokedexapp.data.remote.responses.PokemonList
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PokeApi {

    @GET("pokemon")
    suspend fun  getPokemonList(
        @Query("limit") limit: Int,
        @Query("offset") offset: Int
    ): com.example.newpokedexapp.data.remote.responses.PokemonList

    @GET ("pokemon/{name}")
    suspend fun getPokemonInfo(
        @Path("name") name: String
    ): com.example.newpokedexapp.data.remote.responses.Pokemon
}