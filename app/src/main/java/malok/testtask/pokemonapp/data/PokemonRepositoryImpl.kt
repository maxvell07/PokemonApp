package malok.testtask.pokemonapp.data

import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import malok.testtask.pokemonapp.common.Resource
import malok.testtask.pokemonapp.data.model.PokemonDto
import malok.testtask.pokemonapp.data.model.toDomain
import malok.testtask.pokemonapp.data.model.toEntity
import malok.testtask.pokemonapp.data.remote.ApiPokemon
import malok.testtask.pokemonapp.domain.Pokemon
import malok.testtask.pokemonapp.domain.PokemonRepository

@Singleton
class PokemonRepositoryImpl @Inject constructor (
    private val api: ApiPokemon,
    private val dao: PokemonsDao) : PokemonRepository {

    override suspend fun getPokemons(limit: Int, offset: Int): Resource<List<Pokemon>>{

        val cached = getPokemosCache().first()
        if (cached.isNotEmpty()) {
            return Resource.Success(cached )
        } else {
          return getPokemonsFromNetwork(limit,offset)
        }
    }

    override suspend fun savePokemonsToCache(pokemons: List<PokemonDto>) {
        val entities = pokemons.map { dto ->
            dto.toEntity()
        }
        dao.insertPokemons(entities)
    }

    override suspend fun getPokemosCache(): Flow<List<Pokemon>> {
        return dao.getAllPokemons().map { list ->
            list.map { it.mapToPokemon() }
        }
    }
    override suspend fun getPokemonsFromNetwork(limit: Int, offset: Int): Resource<List<Pokemon>>{
        try {
            val listResponse = api.getListPokemons(limit, offset)
            val details = coroutineScope {
                listResponse.results.map { result ->
                    async { api.getPokemon(result.name) }
                }.awaitAll()
            }
            savePokemonsToCache(details) // DTO → Entity
            return Resource.Success(details.map { value -> value.toDomain() })
        } catch (e: Exception) {
            return Resource.Error("error = ${e}")
        }
    }
}