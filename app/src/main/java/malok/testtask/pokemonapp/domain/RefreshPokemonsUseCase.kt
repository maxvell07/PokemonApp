package malok.testtask.pokemonapp.domain

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import malok.testtask.pokemonapp.common.Resource
import javax.inject.Inject

class RefreshPokemonsUseCase @Inject constructor(
    private val repository: PokemonRepository
) {
    operator fun invoke(limit: Int, offset: Int ): Flow<Resource<List<Pokemon>>> = flow {
        emit(Resource.Loading())

        when (val result = repository.getPokemonsFromNetwork(limit, offset)) {
            is Resource.Success -> {
                val pokemons = result.data?.map { it } ?: emptyList()
                emit(Resource.Success(pokemons))
            }
            is Resource.Error -> {
                emit(Resource.Error(result.message ?: "Unknown error"))
            }
            is Resource.Loading -> {
                emit(Resource.Loading())
            }
        }
    }
}