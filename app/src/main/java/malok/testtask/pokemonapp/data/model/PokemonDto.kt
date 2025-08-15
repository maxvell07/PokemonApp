package malok.testtask.pokemonapp.data.model

import malok.testtask.pokemonapp.domain.Pokemon
import malok.testtask.pokemonapp.domain.PokemonStat

data class PokemonDto(
    val abilities: List<Ability>,
    val base_experience: Int,
    val cries: Cries,
    val forms: List<Form>,
    val game_indices: List<GameIndice>,
    val height: Int,
    val held_items: List<HeldItem>,
    val id: Int,
    val is_default: Boolean,
    val location_area_encounters: String,
    val moves: List<Move>,
    val name: String,
    val order: Int,
    val past_abilities: List<PastAbility>,
    val past_types: List<PastType>,
    val species: Species,
    val sprites: Sprites,
    val stats: List<Stat>,
    val types: List<TypeXX>,
    val weight: Int
)
fun PokemonDto.toDomain(): Pokemon {
    return Pokemon(
        id = id,
        name = name,
        imageUrl = sprites.front_default ?: "",
        height = height,
        weight = weight,
        baseExperience = base_experience,
        types = types.map { it.type.name },
        stats = stats.map { PokemonStat(it.stat.name, it.base_stat) },
        abilities = abilities.map { it.ability.name },
        color = ""
    )
}
