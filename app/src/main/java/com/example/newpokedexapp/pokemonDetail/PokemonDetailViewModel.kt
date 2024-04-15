package com.example.newpokedexapp.pokemonDetail

import androidx.lifecycle.ViewModel
import com.example.newpokedexapp.data.remote.responses.Pokemon
import com.example.newpokedexapp.repository.PokemonRepository
import com.example.newpokedexapp.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PokemonDetailViewModel @Inject constructor(
    private val repository : PokemonRepository
) : ViewModel() {

    suspend fun getPokemonInfo(pokemonName: String): Resource<Pokemon> {
        return repository.getPokemonInfo(pokemonName)
    }

}