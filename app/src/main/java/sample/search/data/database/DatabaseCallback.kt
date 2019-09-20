package sample.search.data.database

import android.content.Context
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import javax.inject.Inject

/**
 * Database lifecycle callback that imports data on database creation.
 */
class DatabaseCallback @Inject constructor(
    private val context: Context
) : RoomDatabase.Callback() {

    override fun onCreate(db: SupportSQLiteDatabase) {
        super.onCreate(db)
        BlacklistedTermDao.import(context, db)
    }
}