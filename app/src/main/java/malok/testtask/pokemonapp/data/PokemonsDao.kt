package malok.testtask.pokemonapp.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface PokemonsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPokemons(pokemons: List<PokemonEntity>)

    @Query("SELECT * FROM pokemons")
    fun getAllPokemons(): Flow<List<PokemonEntity>>

//    @Query("SELECT * FROM pokemons WHERE types LIKE :type")
//    fun getPokemonsByType(type: String): Flow<List<PokemonEntity>>
}