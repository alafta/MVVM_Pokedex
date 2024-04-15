package com.example.newpokedexapp

import android.animation.ObjectAnimator
import android.os.Bundle
import android.view.View
import android.view.animation.OvershootInterpolator
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.core.animation.doOnEnd
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.newpokedexapp.pokemonDetail.PokemonDetailScreen
import com.example.newpokedexapp.pokemonList.PokemonListScreen
import com.example.newpokedexapp.ui.theme.NewPokedexAppTheme

import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
import java.util.Locale

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen().apply {
            setKeepOnScreenCondition {
                !viewModel.isReady.value
            }
            setOnExitAnimationListener {screen ->
                val zoomX = ObjectAnimator.ofFloat(
                    screen.iconView,
                    View.SCALE_X,
                    0.4f,
                    0.0f,
                )
                zoomX.interpolator = OvershootInterpolator()
                zoomX.duration = 500L
                zoomX.doOnEnd{screen.remove()}

                val zoomY = ObjectAnimator.ofFloat(
                    screen.iconView,
                    View.SCALE_Y,
                    0.4f,
                    0.0f,
                )
                zoomY.interpolator = OvershootInterpolator()
                zoomY.duration = 500L
                zoomY.doOnEnd{screen.remove()}

                zoomX.start()
                zoomY.start()
            }
        }
        Timber.d("Loading Pokemon paginated")
        setContent {
            NewPokedexAppTheme {
                Timber.d("Loading Pokemon paginated")
                val navController = rememberNavController()
                NavHost(
                    navController = navController,
                    startDestination = "pokemon_list_screen"
                ) {
                    composable("pokemon_list_screen") {
                        PokemonListScreen(navController = navController)
                    }
                    composable(
                        "pokemon_detail_screen/{dominantColor}/{pokemonName}",
                        arguments = listOf(
                            navArgument("dominantColor") {
                                type = NavType.IntType
                            },
                            navArgument("pokemonName") {
                                type = NavType.StringType
                            }
                        )
                    ) {
                        val dominantColor = remember {
                            val color = it.arguments?.getInt("dominantColor")
                            color?.let { Color(it) } ?: Color.White
                        }
                        val pokemonName = remember {
                            it.arguments?.getString("pokemonName")
                        }
                        PokemonDetailScreen(
                            dominantColor = dominantColor,
                            pokemonName = pokemonName?.lowercase(Locale.ROOT) ?: "",
                            navController = navController
                        )

                    }
                }
            }
        }
    }
}
