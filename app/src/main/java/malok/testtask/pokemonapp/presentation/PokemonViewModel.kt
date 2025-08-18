package malok.testtask.pokemonapp.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import malok.testtask.pokemonapp.common.Resource
import malok.testtask.pokemonapp.domain.Pokemon
import malok.testtask.pokemonapp.domain.GetPokemonsUseCase
import malok.testtask.pokemonapp.domain.RefreshPokemonsUseCase
import javax.inject.Inject

@HiltViewModel
class PokemonViewModel @Inject constructor(
    private val getPokemonsUseCase: GetPokemonsUseCase,
    private val refreshPokemonsUseCase: RefreshPokemonsUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(PokemonListState())
    val state: StateFlow<PokemonListState> = _state.asStateFlow()
    private var currentOffset = 0
    private var limit = 12

    fun loadPokemons() {
        getPokemonsUseCase(currentOffset, limit)
            .onEach { result ->
                when (result) {
                    is Resource.Loading -> {
                        _state.update { it.copy(isLoading = true, error = null) }
                    }

                    is Resource.Success -> {
                        val pokemons = result.data ?: emptyList()
                        _state.update {
                            it.copy(
                                isLoading = false,
                                pokemons = _state.value.pokemons + pokemons
                            )
                        }
                        currentOffset += limit
                    }

                    is Resource.Error -> {
                        _state.update {
                            it.copy(
                                isLoading = false,
                                error = result.message ?: "Unknown error"
                            )
                        }
                    }
                }
            }
            .launchIn(viewModelScope)
    }

    fun refreshPokemons() {
        //TODO(сделать правильный refresh)
        _state.value = PokemonListState()
        refreshPokemonsUseCase(limit, currentOffset).onEach { result ->
            when (result) {
                is Resource.Loading -> {
                    _state.update { it.copy(isLoading = true, error = null) }
                }

                is Resource.Success -> {
                    val pokemons = result.data ?: emptyList()
                    _state.update {
                        it.copy(
                            isLoading = false,
                            pokemons = _state.value.pokemons + pokemons
                        )
                    }
                    currentOffset += limit
                }

                is Resource.Error -> {
                    _state.update {
                        it.copy(
                            isLoading = false,
                            error = result.message ?: "Unknown error"
                        )
                    }
                }
            }
        }
            .launchIn(viewModelScope)
    }

    fun applyFilters(sortBy: String, types: Set<String>) {
        val currentList = _state.value.pokemons

        val filtered = if (types.isNotEmpty()) {
            val lowerSelected = types.map { it.lowercase() }
            currentList.filter { pokemon ->
                pokemon.types.any { it.lowercase() in lowerSelected }
            }
        } else {
            currentList
        }
        val sorted = when (sortBy.uppercase()) {
            "NUMBER" -> filtered.sortedBy { it.id }
            "NAME" -> filtered.sortedBy { it.name }
            "HP" -> filtered.sortedByDescending { it.hp }
            "ATTACK" -> filtered.sortedByDescending { it.attack }
            "DEFENSE" -> filtered.sortedByDescending { it.defense }
            else -> filtered
        }

        _state.update { it.copy(pokemons = sorted) }
    }

}

data class PokemonListState(
    val isLoading: Boolean = false,
    val pokemons: List<Pokemon> = emptyList(),
    val error: String? = null
)
