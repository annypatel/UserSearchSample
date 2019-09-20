package sample.search.domain

import io.reactivex.Single

/**
 * Provider of [UserSearchResult].
 * This interface abstracts the logic of searching for users through the API or other data sources.
 */
interface UserRepository {

    /**
     * Returns a [Single] emitting a [UserSearchResult].
     */
    fun fetchUsers(searchTerm: String): Single<UserSearchResult>
}