package malok.testtask.pokemonapp.domain

data class Pokemon(
    val id: Int,
    val name: String,
    val imageUrl: String,
    val height: Int,
    val weight: Int,
    val baseExperience: Int,
    val types: List<String>,
    val stats: List<PokemonStat>,
    val abilities: List<String>,
    val color: String
)

data class PokemonStat(
    val name: String,
    val value: Int
)