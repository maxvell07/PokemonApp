package malok.testtask.pokemonapp.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import malok.testtask.pokemonapp.data.PokemonRepositoryImpl
import malok.testtask.pokemonapp.data.PokemonsDao
import malok.testtask.pokemonapp.data.remote.ApiPokemon
import malok.testtask.pokemonapp.domain.PokemonRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): PokemonDatabase {
        return Room.databaseBuilder(
            context,
            PokemonDatabase::class.java,
            "pokemon_database"
        ).build()
    }

    @Provides
    @Singleton
    fun providePokemonsDao(database: PokemonDatabase): PokemonsDao {
        return database.pokemonsDao()
    }

    @Provides
    @Singleton
    fun providePokemonRepository(
        api: ApiPokemon,
        database: PokemonDatabase
    ): PokemonRepository {
        return PokemonRepositoryImpl(api, database)
    }
}

