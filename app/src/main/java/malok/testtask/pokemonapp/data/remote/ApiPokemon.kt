package malok.testtask.pokemonapp.data.remote

import malok.testtask.pokemonapp.data.model.PokemonDto
import malok.testtask.pokemonapp.data.model.Species
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.Url

interface ApiPokemon {
    @GET("pokemon/{id}")
    suspend fun getPokemon(
        @Path("id") idOrName: String
    ): PokemonDto

//    @GET
//    suspend fun getPokemonSpecies(
//        @Url url: String
//    ): Species

    @GET("pokemon")
    suspend fun getListPokemons(
        @Query("limit") limit: Int,
        @Query("offset") offset: Int
    ): PokemonListResponse
}
data class PokemonListResponse(
    val results: List<NamedApiResource>
)
data class NamedApiResource(
    val name: String,
    val url: String
)