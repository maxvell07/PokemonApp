package malok.testtask.pokemonapp.data

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import malok.testtask.pokemonapp.data.remote.ApiPokemon
import malok.testtask.pokemonapp.di.PokemonDatabase
import malok.testtask.pokemonapp.domain.Pokemon
import malok.testtask.pokemonapp.domain.PokemonRepository

@Singleton
class PokemonRepositoryImpl @Inject constructor(
    private val api: ApiPokemon,
    private val db: PokemonDatabase
) : PokemonRepository {

    @OptIn(androidx.paging.ExperimentalPagingApi::class)
    override fun getPokemonsPager(pageSize: Int): Flow<PagingData<Pokemon>> {
        return Pager(
            config = PagingConfig(
                pageSize = pageSize,
                initialLoadSize = 20,
                enablePlaceholders = false
            ),
            remoteMediator = PokemonRemoteMediator(api, db),
            pagingSourceFactory = { db.pokemonsDao().getAllPokemonsPaging() }
        ).flow.map { pagingData ->
            pagingData.map { entity -> entity.mapToPokemon() }
        }
    }
}