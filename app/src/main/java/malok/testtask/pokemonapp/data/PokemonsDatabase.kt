package malok.testtask.pokemonapp.data

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [PokemonEntity :: class ], version = 1)
abstract class PokemosnDatabase: RoomDatabase(){
    abstract fun dao(): PokemonsDao

}