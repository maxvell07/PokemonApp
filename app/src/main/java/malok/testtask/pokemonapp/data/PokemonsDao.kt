package malok.testtask.pokemonapp.data

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface PokemonsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPokemons(pokemons: List<PokemonEntity>)

    @Query("SELECT * FROM pokemons ORDER BY id ASC")
    fun getAllPokemons(): Flow<List<PokemonEntity>>

    @Query("SELECT * FROM pokemons ORDER BY id ASC")
    fun getAllPokemonsPaging(): PagingSource<Int, PokemonEntity>

    @Query("DELETE FROM pokemons")
    suspend fun clearAll()
}