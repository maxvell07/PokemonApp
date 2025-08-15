package malok.testtask.pokemonapp.data

import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import malok.testtask.pokemonapp.common.Resource
import malok.testtask.pokemonapp.data.model.PokemonDto
import malok.testtask.pokemonapp.data.remote.ApiPokemon
import malok.testtask.pokemonapp.domain.PokemonRepository
import java.io.IOException

class PokemonRepositoryImpl(private val api: ApiPokemon) : PokemonRepository {

    override suspend fun getPokemonList(limit: Int, offset: Int): Resource<List<PokemonDto>> {
        return try {
            val listResponse = api.getListPokemons(limit, offset)
            val details = coroutineScope {
                listResponse.results.map { result ->
                    async { api.getPokemon(result.name) }
                }.awaitAll()
            }

            Resource.Success(details)
        } catch (e: IOException) {
            Resource.Error("Couldn't reach server.")
        } catch (e: Exception) {
            Resource.Error(e.localizedMessage ?: "Unknown error")
        }
    }
}
