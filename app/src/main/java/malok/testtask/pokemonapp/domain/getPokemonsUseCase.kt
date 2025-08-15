package malok.testtask.pokemonapp.domain

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import malok.testtask.pokemonapp.common.Resource
import malok.testtask.pokemonapp.data.model.toDomain

class GetPokemonsUseCase(
    private val repository: PokemonRepository
) {
    operator fun invoke(offset: Int, limit: Int): Flow<Resource<List<Pokemon>>> = flow {
        emit(Resource.Loading())

        when (val result = repository.getPokemonList(limit, offset)) { // <-- тут теперь limit первым, offset вторым
            is Resource.Success -> {
                val pokemons = result.data?.map { it.toDomain() } ?: emptyList()
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
