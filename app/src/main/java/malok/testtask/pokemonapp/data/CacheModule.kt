package malok.testtask.pokemonapp.data

import android.content.Context
import androidx.room.Room
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton
import malok.testtask.pokemonapp.R

@Singleton
interface CacheModule {
    fun dao(): PokemonsDao

    @Singleton
    class Base @Inject constructor(
        @ApplicationContext applicationContext: Context
    ) : CacheModule {

        private val database by lazy {
            Room.databaseBuilder(
                applicationContext,
                PokemosnDatabase::class.java,
                applicationContext.getString(R.string.app_name)
            ).build()
        }
        override fun dao(): PokemonsDao = database.dao()
    }
}