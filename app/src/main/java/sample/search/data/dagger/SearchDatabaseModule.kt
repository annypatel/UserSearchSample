package sample.search.data.dagger

import sample.search.data.database.SearchDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Dagger module for exposing [SearchDatabase] to application object graph.
 */
@Module(subcomponents = [SearchDatabaseComponent::class])
object SearchDatabaseModule {

    @Provides
    @Singleton
    @JvmStatic
    fun searchDatabase(builder: SearchDatabaseComponent.Builder): SearchDatabase {
        return builder.build().searchDatabase()
    }
}