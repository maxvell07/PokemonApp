package malok.testtask.pokemonapp.domain

import malok.testtask.pokemonapp.common.Resource
import malok.testtask.pokemonapp.data.model.PokemonDto

interface PokemonRepository {
    suspend fun getPokemonList(limit: Int, offset: Int): Resource<List<PokemonDto>>
}