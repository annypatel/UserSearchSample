package sample.search.data.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

/**
 * A database entity for users.
 */
@Entity(
    tableName = "user",
    indices = [Index(
        value = ["username"],
        unique = true
    )]
)
data class UserEntity(

    @ColumnInfo(name = "id")
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,

    @ColumnInfo(name = "username")
    val username: String,

    @ColumnInfo(name = "display_name")
    val displayName: String,

    @ColumnInfo(name = "avatar_url")
    val avatarUrl: String
)