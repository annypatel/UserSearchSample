package sample.search.domain

/**
 * Models use search result returned by the API.
 */
data class UserSearchResult(
    val users: Set<User>,
    val offline: Boolean = false
) {
    companion object {
        val EMPTY = UserSearchResult(emptySet())
    }
}

data class User(
    val username: String,
    val displayName: String,
    val avatarUrl: String
)