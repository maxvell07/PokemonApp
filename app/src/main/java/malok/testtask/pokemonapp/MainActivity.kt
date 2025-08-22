package malok.testtask.pokemonapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import dagger.hilt.android.AndroidEntryPoint
import malok.testtask.pokemonapp.presentation.MainScreen
import malok.testtask.pokemonapp.presentation.PokemonViewModel
import malok.testtask.pokemonapp.ui.theme.PokemonAppTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: PokemonViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PokemonAppTheme {
                MainScreen(viewModel = viewModel)
            }
        }
    }
}