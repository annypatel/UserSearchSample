package sample.search.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.reactivex.Completable
import io.reactivex.Single

/**
 * Dao for accessing user table in database.
 */
@Dao
interface UserDao {

    /**
     * To insert the users into database.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(entities: List<UserEntity>): Completable

    /**
     * To find users by the search term.
     */
    @Query("SELECT * FROM user WHERE (username LIKE :term || '%') OR (display_name LIKE :term || '%')")
    fun findBy(term: String): Single<List<UserEntity>>
}