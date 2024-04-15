package com.example.newpokedexapp.pokemonList

import android.content.Context
import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.nfc.tech.MifareUltralight.PAGE_SIZE
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.palette.graphics.Palette
import com.example.newpokedexapp.data.models.PokedexListEntry
import com.example.newpokedexapp.repository.PokemonRepository
import com.example.newpokedexapp.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import timber.log.Timber
import java.net.URL
import java.util.Locale
import javax.inject.Inject


@HiltViewModel
class PokemonListViewModel @Inject constructor(
    private val repository: PokemonRepository,
    private val context: Context

) : ViewModel() {

    private var curPage = 0

    var pokemonList = mutableStateOf<List<PokedexListEntry>>(listOf())
    var loadError = mutableStateOf("")
    var isLoading = mutableStateOf(false)
    var endReached = mutableStateOf(false)

    private var cachedPokemonList = listOf<PokedexListEntry>()
    private var isSearchStarting = true
    var isSearching = mutableStateOf(false)

    init {
        loadPokemonPaginated()
    }

    fun searchPokemonList(query: String) {
        val listToSearch = if (isSearchStarting) {
            pokemonList.value
        } else {
            cachedPokemonList
        }
        viewModelScope.launch(Dispatchers.Default) {
            if (query.isEmpty()) {
                pokemonList.value = cachedPokemonList
                isSearching.value = false
                isSearchStarting = true
                return@launch
            }
            val results = listToSearch.filter {
                it.pokemonName.contains(query.trim(), ignoreCase = true) ||
                        it.number.toString() == query.trim()
            }
            if (isSearchStarting) {
                cachedPokemonList = pokemonList.value
                isSearchStarting = false
            }
            pokemonList.value = results
            isSearching.value = true
        }
    }

//    fun loadPokemonPaginated() {
//        Timber.d("Loading Pokemon paginated")
//
//        viewModelScope.launch {
//            isLoading.value = true
//            when (val result = repository.getPokemonList(PAGE_SIZE, curPage * PAGE_SIZE)) {
//                is Resource.Success -> {
//                    endReached.value = curPage * PAGE_SIZE >= result.data!!.count
//                    val pokedexEntries = result.data.results.mapIndexed { index, entry ->
//                        val number = if (entry.url.endsWith("/")) {
//                            entry.url.dropLast(1).takeLastWhile { it.isDigit() }
//                        } else {
//                            entry.url.takeLastWhile { it.isDigit() }
//                        }
//                        val url =
//                            "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/${number}.png"
//                        PokedexListEntry(entry.name.replaceFirstChar {
//                            if (it.isLowerCase()) it.titlecase(
//                                Locale.ROOT
//                            ) else it.toString()
//                        }, url, number.toInt())
//                    }
//                    curPage++
//
//                    loadError.value = ""
//                    isLoading.value = false
//                    pokemonList.value += pokedexEntries
//                    Timber.d("Pokemon paginated loaded successfully")
//                }
//
//                is Resource.Error -> {
//                    loadError.value = result.message!!
//                    isLoading.value = false
//                    Timber.e("Error loading Pokemon paginated: ${result.message}")
//                }
//
//                else -> {
//                    Timber.w("Unexpected result when loading Pokemon paginated")
//                }
//            }
//        }
//    }

    fun loadPokemonPaginated() {
        viewModelScope.launch {
            isLoading.value = true
            when(val result = repository.getPokemonList(PAGE_SIZE, curPage * PAGE_SIZE)) {
                is Resource.Success -> {
                    Timber.d("Pokemon list result: ${result.data}") // Print the data inside Resource.Success to the log

                    endReached.value = curPage * PAGE_SIZE >= result.data!!.count
                    val pokedexEntries = result.data.results.mapIndexed { index, entry ->
                        val number = if(entry.url.endsWith("/")) {
                            entry.url.dropLast(1).takeLastWhile { it.isDigit() }
                        } else {
                            entry.url.takeLastWhile { it.isDigit() }
                        }
                        val url = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/${number}.png"
                        PokedexListEntry(entry.name.capitalize(Locale.ROOT), url, number.toInt())
                    }
                    curPage++
                    Timber.d("Pokemon paginated loaded successfully")

                    loadError.value = ""
                    isLoading.value = false
                    pokemonList.value += pokedexEntries
                }
                is Resource.Error -> {
                    loadError.value = result.message!!
                    isLoading.value = false
                }

                else -> {}
            }
        }
    }


    fun calcDominantColor(drawable: Drawable, onFinish: (Color) -> Unit) {
        val bmp = (drawable as BitmapDrawable).bitmap.copy(Bitmap.Config.ARGB_8888, true)

        Palette.from(bmp).generate { palette ->
            palette?.dominantSwatch?.rgb?.let { colorValue ->
                onFinish(Color(colorValue))
            }
        }
    }

    suspend fun loadImage(imageUrl: String): Drawable? = withContext(Dispatchers.IO) {
        try {
            val inputStream = URL(imageUrl).openStream()
            val bitmap = BitmapFactory.decodeStream(inputStream)
            BitmapDrawable(Resources.getSystem(), bitmap)
        } catch (e: Exception) {
            null
        }
    }




}




