package malok.testtask.pokemonapp.di

import androidx.room.Database
import androidx.room.RoomDatabase
import malok.testtask.pokemonapp.data.PokemonEntity
import malok.testtask.pokemonapp.data.PokemonsDao

@Database(
    entities = [PokemonEntity::class],
    version = 1,
    exportSchema = false
)

abstract class PokemonDatabase : RoomDatabase() {
    abstract fun pokemonsDao(): PokemonsDao
}