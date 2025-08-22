package malok.testtask.pokemonapp.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import malok.testtask.pokemonapp.domain.GetPokemonsUseCase
import malok.testtask.pokemonapp.domain.Pokemon

@HiltViewModel
class PokemonViewModel @Inject constructor(
    private val getPokemonsUseCase: GetPokemonsUseCase,
) : ViewModel() {

    // Пейджинг-поток
    val pokemons: Flow<PagingData<Pokemon>> =
        getPokemonsUseCase(20)
            .cachedIn(viewModelScope)
}