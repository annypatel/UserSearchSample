package sample.search.data.database

import android.content.Context
import androidx.annotation.VisibleForTesting
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.sqlite.db.SupportSQLiteDatabase
import sample.search.R
import io.reactivex.Completable
import io.reactivex.Single
import java.io.InputStreamReader

/**
 * Dao for accessing blacklist table in database.
 */
@Dao
interface BlacklistedTermDao {

    companion object {

        /**
         * To import the blacklisted terms into database.
         */
        fun import(context: Context, db: SupportSQLiteDatabase) {
            val lines = InputStreamReader(context.resources.openRawResource(R.raw.blacklist))
                .readLines()

            val statement = db.compileStatement("INSERT INTO blacklist(term) VALUES(?)")
            try {
                db.beginTransaction()
                lines.forEach {
                    statement.bindString(1, it)
                    statement.execute()
                }
                db.setTransactionSuccessful()
            } finally {
                db.endTransaction()
            }
        }
    }

    /**
     * To insert the term in to database.
     */
    @Insert
    fun insert(entity: BlacklistedTermEntity): Completable

    /**
     * To retrieve only one [blacklisted term][BlacklistedTermEntity] from table which is prefix of the given
     * [search term][term].
     */
    @Query("SELECT * FROM blacklist WHERE :term LIKE (term || '%') LIMIT 1")
    fun findBy(term: String): Single<BlacklistedTermEntity>

    /**
     * To get count of number of entries in blacklist table.
     */
    @VisibleForTesting
    @Query("SELECT COUNT(*) FROM blacklist")
    fun count(): Long
}