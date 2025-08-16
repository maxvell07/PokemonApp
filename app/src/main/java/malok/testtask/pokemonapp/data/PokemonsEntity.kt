package malok.testtask.pokemonapp.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import malok.testtask.pokemonapp.domain.Pokemon
import malok.testtask.pokemonapp.domain.PokemonStat

@Entity(tableName = "pokemons")
data class PokemonEntity(
    @PrimaryKey val id: Int,
    val name: String,
    val imageurl: String,
    val hp: Int,
    val attack: Int,
    val defense: Int,
    val height: Int,
    val weight: Int,
    val types: String
)

fun PokemonEntity.mapToPokemon(): Pokemon {
    val typesList = types.split(", ").map { it.trim() }

    val statsList = listOf(
        PokemonStat("hp", hp),
        PokemonStat("attack", attack),
        PokemonStat("defense", defense)
    )
    return Pokemon(
        id = id,
        name = name,
        imageUrl = imageurl,
        height = height,
        weight = weight,
        baseExperience = 0,
        types = typesList,
        stats = statsList,
        abilities = emptyList(),
        color = ""
    )
}

