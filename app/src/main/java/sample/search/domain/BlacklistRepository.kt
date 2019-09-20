package sample.search.domain

import io.reactivex.Completable
import io.reactivex.Single

/**
 * Repository to maintain the blacklisted search terms.
 */
interface BlacklistRepository {

    /**
     * To add search term to the blacklist.
     */
    fun add(searchTerm: String): Completable

    /**
     * To check search term is present in the blacklist or not.
     */
    fun isBlacklisted(searchTerm: String): Single<Boolean>
}