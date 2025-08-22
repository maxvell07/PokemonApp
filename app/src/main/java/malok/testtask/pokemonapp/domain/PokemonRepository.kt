package malok.testtask.pokemonapp.domain

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import malok.testtask.pokemonapp.common.Resource
import malok.testtask.pokemonapp.data.model.PokemonDto
import javax.inject.Singleton

@Singleton
interface PokemonRepository {
    fun getPokemonsPager(pageSize: Int): Flow<PagingData<Pokemon>>
}