package malok.testtask.pokemonapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import dagger.hilt.android.AndroidEntryPoint
import malok.testtask.pokemonapp.presentation.MainScreen
import malok.testtask.pokemonapp.presentation.PokemonViewModel
import malok.testtask.pokemonapp.ui.theme.PokemonAppTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: PokemonViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.loadPokemons()

        setContent {
            PokemonAppTheme {
                Surface(color = MaterialTheme.colorScheme.background) {
                    MainScreen(viewModel = viewModel)
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    PokemonAppTheme {
     //   MainScreen()
    }
}