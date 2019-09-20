package sample.search.data.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * A database entity for blacklisted search terms.
 */
@Entity(tableName = "blacklist")
data class BlacklistedTermEntity(

    @ColumnInfo(name = "id")
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,

    @ColumnInfo(name = "term")
    val term: String
)