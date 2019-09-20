package sample.search.data

import sample.rx.RxSchedulers
import sample.search.data.database.UserDao
import sample.search.data.database.UserEntity
import sample.base.data.network.ConnectivityService
import sample.search.data.network.UserSearchResponse
import sample.search.data.network.UserSearchService
import sample.search.domain.User
import sample.search.domain.UserRepository
import sample.search.domain.UserSearchResult
import io.reactivex.Single
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Implementation of [UserRepository] with support for offline caching.
 */
@Singleton
class UserRepositoryImpl @Inject constructor(
    private val searchService: UserSearchService,
    private val connectivityService: ConnectivityService,
    private val userDao: UserDao,
    private val schedulers: RxSchedulers
) : UserRepository {

    /**
     * Returns a [Single] emitting a [UserSearchResult].
     */
    override fun fetchUsers(searchTerm: String): Single<UserSearchResult> {
        return connectivityService.connected()
            .flatMap { connected ->
                if (connected) fetch(searchTerm)
                else fetchOffline(searchTerm)
            }
    }

    private fun fetch(searchTerm: String): Single<UserSearchResult> {
        return searchService.searchUsers(searchTerm)
            .subscribeOn(schedulers.io)
            .map(toUserSearchResult())
            .doOnSuccess { saveOffline(it) }
    }

    private fun fetchOffline(searchTerm: String): Single<UserSearchResult> {
        return userDao.findBy(searchTerm)
            .subscribeOn(schedulers.database)
            .map(toOfflineUserSearchResult())
    }

    private fun saveOffline(result: UserSearchResult) {
        userDao.insert(asUserEntities(result))
            .subscribeOn(schedulers.database)
            .subscribe()
    }

    private fun toUserSearchResult() = { response: UserSearchResponse ->
        val users = response.users
            .map {
                User(it.username, it.displayName, it.avatarUrl)
            }
            .toSet()
        UserSearchResult(users = users)
    }

    private fun toOfflineUserSearchResult() = { entities: List<UserEntity> ->
        val users = entities.map {
            User(it.username, it.displayName, it.avatarUrl)
        }.toSet()
        UserSearchResult(users, true)
    }

    private fun asUserEntities(result: UserSearchResult): List<UserEntity> {
        return result.users.map {
            UserEntity(
                username = it.username,
                displayName = it.displayName,
                avatarUrl = it.avatarUrl
            )
        }
    }
}