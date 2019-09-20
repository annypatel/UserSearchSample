package sample.search.data.network

import com.google.gson.annotations.SerializedName

/**
 * Models the search query response.
 */
data class UserSearchResponse(val ok: Boolean, val users: List<UserRaw>)

/**
 * User model returned by the API.
 */
data class UserRaw(
    val username: String,
    @SerializedName("display_name")
    val displayName: String,
    @SerializedName("avatar_url")
    val avatarUrl: String
)