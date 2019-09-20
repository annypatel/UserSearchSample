package sample.search.data.dagger

import sample.search.data.BlacklistRepositoryImpl
import sample.search.data.UserRepositoryImpl
import sample.search.data.database.BlacklistedTermDao
import sample.search.data.database.SearchDatabase
import sample.search.data.database.UserDao
import sample.search.domain.BlacklistRepository
import sample.search.domain.UserRepository
import dagger.Binds
import dagger.Module
import dagger.Provides

/**
 * Module to inject [UserRepository] into application component.
 */
@Module(
    includes = [
        SearchDatabaseModule::class,
        UserSearchServiceModule::class
    ]
)
abstract class SearchDataModule {
    @Binds
    abstract fun userRepository(repository: UserRepositoryImpl): UserRepository

    @Binds
    abstract fun blacklistRepository(repository: BlacklistRepositoryImpl): BlacklistRepository

    @Module
    companion object {

        @Provides
        @JvmStatic
        fun blacklistedTermDao(database: SearchDatabase): BlacklistedTermDao {
            return database.blacklistedTermDao()
        }

        @Provides
        @JvmStatic
        fun userDao(database: SearchDatabase): UserDao {
            return database.userDao()
        }
    }
}