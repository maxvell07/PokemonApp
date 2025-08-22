package malok.testtask.pokemonapp.data


import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import malok.testtask.pokemonapp.data.model.toEntity
import malok.testtask.pokemonapp.data.remote.ApiPokemon
import malok.testtask.pokemonapp.di.PokemonDatabase

@OptIn(ExperimentalPagingApi::class)
class PokemonRemoteMediator(
    private val api: ApiPokemon,
    private val db: PokemonDatabase
) : RemoteMediator<Int, PokemonEntity>() {

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, PokemonEntity>
    ): MediatorResult {
        return try {
            val page = when (loadType) {
                LoadType.REFRESH -> 0
                LoadType.PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)
                LoadType.APPEND -> {
                    val lastItem = state.lastItemOrNull()
                    if (lastItem == null) 0 else lastItem.id / state.config.pageSize
                }
            }

            val response = api.getListPokemons(state.config.pageSize, page * state.config.pageSize)
            val details = coroutineScope {
                response.results.map { result ->
                    async { api.getPokemon(result.name) }
                }.awaitAll()
            }

            if (loadType == LoadType.REFRESH) {
                db.pokemonsDao().clearAll()
            }

            db.pokemonsDao().insertPokemons(details.map { it.toEntity() })

            MediatorResult.Success(endOfPaginationReached = details.isEmpty())
        } catch (e: Exception) {
            MediatorResult.Error(e)
        }
    }
}
