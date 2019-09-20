package sample.search.data.database

import androidx.room.Database
import androidx.room.RoomDatabase

/**
 * Room database for search feature that maintains offline cache for users and blacklisted terms.
 */
@Database(
    version = SearchDatabase.VERSION,
    entities = [
        BlacklistedTermEntity::class,
        UserEntity::class
    ]
)
abstract class SearchDatabase : RoomDatabase() {

    companion object {
        const val VERSION = 1
        const val NAME = "search.db"
    }

    abstract fun blacklistedTermDao(): BlacklistedTermDao
    abstract fun userDao(): UserDao
}