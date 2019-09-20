package sample.search.domain

import sample.base.domain.SingleUseCase
import io.reactivex.Single
import javax.inject.Inject

/**
 * Use case for searching users.
 */
interface SearchUserUseCase : SingleUseCase<String, UserSearchResult>

/**
 * Implementation of [SearchUserUseCase] that perform search using [UserRepository] only if search term is not
 * blacklisted and also adds the search term to blacklist if no result found.
 */
class SearchUserUseCaseImpl @Inject constructor(
    private val isBlacklisted: IsBlacklistedUseCase,
    private val addToBlacklist: AddToBlacklistUseCase,
    private val userRepository: UserRepository
) : SearchUserUseCase {

    override fun invoke(input: String): Single<UserSearchResult> {
        return if (input.isEmpty()) {
            Single.just(UserSearchResult.EMPTY)
        } else {
            isBlacklisted(input)
                .flatMap { blacklisted ->
                    if (blacklisted) {
                        Single.just(UserSearchResult.EMPTY)
                    } else {
                        userRepository.fetchUsers(input)
                            .doOnSuccess {
                                if (it.users.isEmpty() && !it.offline) {
                                    addToBlacklist(input).subscribe()
                                }
                            }
                    }
                }
        }
    }
}