package malok.testtask.pokemonapp.di

import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import malok.testtask.pokemonapp.data.CacheModule
import malok.testtask.pokemonapp.data.PokemonRepositoryImpl
import malok.testtask.pokemonapp.data.PokemonsDao
import malok.testtask.pokemonapp.domain.PokemonRepository

@Module
@InstallIn(SingletonComponent::class)
abstract class CacheModuleHilt {

    @Binds
    @Singleton
    abstract fun bindPokemonRepository(
        repository: PokemonRepositoryImpl
    ): PokemonRepository

    @Binds
    @Singleton
    abstract fun bindCacheModule(
        cacheModule: CacheModule.Base
    ): CacheModule

    companion object {
        @Provides
        @Singleton
        fun provideDao(cacheModule: CacheModule): PokemonsDao = cacheModule.dao()
    }
}

