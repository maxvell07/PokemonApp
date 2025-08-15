package malok.testtask.pokemonapp.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import malok.testtask.pokemonapp.common.Resource
import malok.testtask.pokemonapp.domain.Pokemon
import malok.testtask.pokemonapp.domain.GetPokemonsUseCase

class PokemonViewModel(
    private val getPokemonsUseCase: GetPokemonsUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(PokemonListState())
    val state: StateFlow<PokemonListState> = _state.asStateFlow()

    private var currentOffset = 0
    private var limit = 10

    fun loadPokemons() {
            getPokemonsUseCase(currentOffset,limit)
                .onEach { result ->
                    when (result) {
                        is Resource.Loading -> {
                            _state.update { it.copy(isLoading = true, error = null) }
                        }
                        is Resource.Success -> {
                            val pokemons = result.data ?: emptyList()
                            _state.update { it.copy(
                                isLoading = false,
                                pokemons = _state.value.pokemons + pokemons
                            ) }
                            currentOffset += limit
                        }
                        is Resource.Error -> {
                            _state.update { it.copy(
                                isLoading = false,
                                error = result.message ?: "Unknown error"
                            ) }
                        }
                    }
                }
                .launchIn(viewModelScope)
    }

    fun refreshPokemons() {
        currentOffset = 0
        limit =5
        _state.value = PokemonListState()
        loadPokemons()
    }
}

data class PokemonListState(
    val isLoading: Boolean = false,
    val pokemons: List<Pokemon> = emptyList(),
    val error: String? = null
)
