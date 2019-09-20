package sample.search.data.dagger

import android.app.Application
import androidx.room.Room
import sample.search.data.database.DatabaseCallback
import sample.search.data.database.SearchDatabase
import dagger.Module
import dagger.Provides
import dagger.Subcomponent
import javax.inject.Named

/**
 * Dagger component that exposes [SearchDatabase] with [searchDatabase] provision method, dependencies declared via
 * [Subcomponent.modules] won't exposed outside this component.
 */
@Subcomponent(modules = [InternalSearchDatabaseModule::class])
interface SearchDatabaseComponent {

    @Named("internal")
    fun searchDatabase(): SearchDatabase

    @Subcomponent.Builder
    interface Builder {

        fun build(): SearchDatabaseComponent
    }
}

/**
 * Dagger module for [SearchDatabaseComponent]. Defines dependencies required to constructs [SearchDatabase]. All
 * the dependencies declared here is internal to [SearchDatabaseComponent] and won't be accessible outside.
 */
@Module
object InternalSearchDatabaseModule {

    @Provides
    @JvmStatic
    @Named("internal")
    fun searchDatabase(app: Application, callback: DatabaseCallback): SearchDatabase {
        return Room.databaseBuilder(app, SearchDatabase::class.java, SearchDatabase.NAME)
            .addCallback(callback)
            .build()
    }
}