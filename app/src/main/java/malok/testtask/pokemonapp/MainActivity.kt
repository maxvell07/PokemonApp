package malok.testtask.pokemonapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import malok.testtask.pokemonapp.data.PokemonRepositoryImpl
import malok.testtask.pokemonapp.data.remote.ApiPokemon
import malok.testtask.pokemonapp.domain.GetPokemonsUseCase
import malok.testtask.pokemonapp.presentation.MainScreen
import malok.testtask.pokemonapp.presentation.PokemonViewModel
import malok.testtask.pokemonapp.ui.theme.PokemonAppTheme
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : ComponentActivity() {

    private val viewModel by lazy {

        val retrofit = Retrofit.Builder()
            .baseUrl("https://pokeapi.co/api/v2/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val api = retrofit.create(ApiPokemon::class.java)

        val repository = PokemonRepositoryImpl(api)

        val getPokemonsUseCase = GetPokemonsUseCase(repository)

        PokemonViewModel(getPokemonsUseCase)
    }

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