package malok.testtask.pokemonapp.domain

import kotlinx.coroutines.flow.Flow
import malok.testtask.pokemonapp.common.Resource
import malok.testtask.pokemonapp.data.model.PokemonDto
import javax.inject.Singleton

@Singleton
interface PokemonRepository {
    suspend fun getPokemonsFromNetwork(limit: Int, offset: Int): Resource<List<Pokemon>>

    suspend fun getPokemosCache(): Flow<List<Pokemon>>

    suspend fun getPokemons(limit: Int, offset: Int): Resource<List<Pokemon>>

    suspend fun savePokemonsToCache(pokemons: List<PokemonDto>)
}