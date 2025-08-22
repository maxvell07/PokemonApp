package malok.testtask.pokemonapp.domain

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetPokemonsUseCase @Inject constructor(
    private val repository: PokemonRepository
) {
    operator fun invoke(pageSize: Int): Flow<PagingData<Pokemon>> {
        return repository.getPokemonsPager(pageSize)
    }
}

