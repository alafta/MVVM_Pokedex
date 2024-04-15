package com.example.newpokedexapp.pokemonList

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.newpokedexapp.R
import com.example.newpokedexapp.data.models.PokedexListEntry
import androidx.compose.runtime.remember
import androidx.compose.ui.draw.scale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import coil.compose.rememberImagePainter
import com.example.newpokedexapp.ui.theme.Tajawal

@Composable
fun PokemonListScreen(
    navController: NavController,
    viewModel: PokemonListViewModel = hiltViewModel()
) {
    Surface(
        color = MaterialTheme.colorScheme.background,
        modifier = Modifier.fillMaxSize()
    ) {
        Column {
            Spacer(modifier = Modifier.height(20.dp))
            Image(
                painter = painterResource(id = R.drawable.pokemon_logo),
                contentDescription = "Pokemon",
                modifier = Modifier
                    .width(450.dp)
                    .height(100.dp)
                    .align(CenterHorizontally)
            )
            SearchBar(
                hint = "Search...",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                viewModel.searchPokemonList(it)
            }
            Spacer(modifier = Modifier.height(16.dp))
            PokemonList(navController = navController)
        }
    }
}

@Composable
fun SearchBar(
    modifier: Modifier = Modifier,
    hint: String = "Search",
    onSearch: (String) -> Unit = {}
) {
    var text by remember { mutableStateOf("") }
    var isHintDisplayed by remember { mutableStateOf(hint.isNotEmpty()) }

    Box(modifier = modifier) {
        BasicTextField(
            value = text,
            onValueChange = {
                text = it
                onSearch(it)
            },
            maxLines = 1,
            singleLine = true,
            textStyle = TextStyle(color = Color.Black),
            modifier = Modifier
                .fillMaxWidth()
                .shadow(5.dp, CircleShape)
                .background(Color.White, CircleShape)
                .padding(horizontal = 20.dp, vertical = 12.dp)
                .onFocusChanged {
                    isHintDisplayed = !it.isFocused && text.isEmpty()
                }
        )
        if (isHintDisplayed) {
            Text(
                text = hint,
                color = Color.LightGray,
                modifier = Modifier
                    .padding(horizontal = 20.dp, vertical = 12.dp)
            )
        }
    }
}

//@Composable
//fun PokemonList(
//    navController: NavController,
//    viewModel: PokemonListViewModel = hiltViewModel()
//) {
//    val pokemonList by remember { viewModel.pokemonList }
//    val endReached by remember { viewModel.endReached }
//    val loadError by remember { viewModel.loadError }
//    val isLoading by remember { viewModel.isLoading }
//    val isSearching by remember { viewModel.isSearching }
//
//    LazyColumn(contentPadding = PaddingValues(16.dp)) {
//        val itemCount = if(pokemonList.size % 2 == 0) {
//            pokemonList.size / 2
//        } else {
//            pokemonList.size / 2 + 1
//        }
//        items(itemCount) {
//            if(it >= itemCount - 1 && !endReached && !isLoading && !isSearching) {
//                LaunchedEffect(key1 = true) {
//                    viewModel.loadPokemonPaginated()
//                }
//            }
//            PokedexRow(rowIndex = it, entries = pokemonList, navController = navController)
//        }
//    }
//
//    Box(
//        contentAlignment = Center,
//        modifier = Modifier.fillMaxSize()
//    ) {
//        if(isLoading) {
//            CircularProgressIndicator(color = MaterialTheme.colorScheme.primary)
//        }
//        if(loadError.isNotEmpty()) {
//            RetrySection(error = loadError) {
//                viewModel.loadPokemonPaginated()
//            }
//        }
//    }
//
//}

@Composable
fun PokemonList(
    navController: NavController,
    viewModel: PokemonListViewModel = hiltViewModel()
) {
    val pokemonList by remember { viewModel.pokemonList }
    val endReached by remember { viewModel.endReached }
    val loadError by remember { viewModel.loadError }
    val isLoading by remember { viewModel.isLoading }
    val isSearching by remember { viewModel.isSearching }

    LazyColumn(contentPadding = PaddingValues(16.dp)) {
        val itemCount = if(pokemonList.size % 2 == 0) {
            pokemonList.size / 2
        } else {
            pokemonList.size / 2 + 1
        }
        items(itemCount) {
            if(it >= itemCount - 1 && !endReached && !isLoading && !isSearching) {
                viewModel.loadPokemonPaginated()
            }
            PokedexRow(rowIndex = it, entries = pokemonList, navController = navController)
        }
    }

    Box(
        contentAlignment = Center,
        modifier = Modifier.fillMaxSize()
    ) {
        if(isLoading) {
            CircularProgressIndicator(color = MaterialTheme.colorScheme.primary)
        }
        if(loadError.isNotEmpty()) {
            RetrySection(error = loadError) {
                viewModel.loadPokemonPaginated()
            }
        }
    }

}

@Composable
fun PokedexRow(
    rowIndex: Int,
    entries: List<PokedexListEntry>,
    navController: NavController
) {
    Column {
        Row {
            PokedexEntry<Any>(
                entry = entries[rowIndex * 2],
                navController = navController,
                modifier = Modifier.weight(1f)
            )
            Spacer(modifier = Modifier.width(16.dp))
            if (entries.size >= rowIndex * 2 + 2) {
                PokedexEntry<Any>(
                    entry = entries[rowIndex * 2 + 1],
                    navController = navController,
                    modifier = Modifier.weight(1f)
                )
            } else {
                Spacer(modifier = Modifier.weight(1f))
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
    }
}

@Composable
fun RetrySection(
    error: String,
    onRetry: () -> Unit
) {
    Column {
        Text(error, color = Color.Red, fontSize = 18.sp)
        Spacer(modifier = Modifier.height(8.dp))
       Button(
            onClick = { onRetry() },
            modifier = Modifier.align(CenterHorizontally)
        ) {
            Text(text = "Retry")
        }
    }
}


@Composable
fun <T> PokedexEntry(
    entry: PokedexListEntry,
    navController: NavController,
    modifier: Modifier = Modifier,
    viewModel: PokemonListViewModel = hiltViewModel()
) {
    val defaultDominantColor = MaterialTheme.colorScheme.surface
    var dominantColor by remember {
        mutableStateOf(defaultDominantColor)
    }

    val coroutineScope = rememberCoroutineScope()
    var loading by remember { mutableStateOf(true) }
    var error by remember { mutableStateOf(false) }

    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .shadow(5.dp, RoundedCornerShape(10.dp))
            .clip(RoundedCornerShape(10.dp))
            .aspectRatio(1f)
            .background(
                Brush.verticalGradient(
                    listOf(
                        dominantColor,
                        defaultDominantColor
                    )
                )
            )
            .clickable {
                navController.navigate(
                    "pokemon_detail_screen/${dominantColor.toArgb()}/${entry.pokemonName}"
                )
            }
    ) {
        Column {
            LaunchedEffect(true) {
                val result = viewModel.loadImage(entry.imageUrl)
                if (result != null) {
                    viewModel.calcDominantColor(result) { color ->
                        dominantColor = color
                    }
                    loading = false
                } else {
                    error = true
                }
            }

            if (loading) {
                CircularProgressIndicator(
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier
                        .scale(0.5f)
                        .align(CenterHorizontally)

                )
            } else if (error) {
                // Handle error state
                Text(
                    text = "Error loading image",
                    color = Color.Red,
                    modifier = Modifier.padding(8.dp)
                )
            } else {
                // Success state
                Image(
                    painter = rememberAsyncImagePainter(entry.imageUrl),
                    contentDescription = entry.pokemonName,
                    modifier = Modifier
                        .size(120.dp)
                        .align(CenterHorizontally)
                )
            }

            Text(
                text = entry.pokemonName,
                fontFamily = Tajawal,
                fontWeight = FontWeight.ExtraBold,
                fontSize = 25.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}





