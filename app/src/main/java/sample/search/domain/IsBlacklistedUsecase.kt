package sample.search.domain

import sample.base.domain.SingleUseCase
import io.reactivex.Single
import javax.inject.Inject

/**
 * Use case to check given search term is blacklisted or not.
 */
interface IsBlacklistedUseCase : SingleUseCase<String, Boolean>

/**
 * Implementation of [IsBlacklistedUseCase] that uses [BlacklistRepository] to check given search term is blacklisted
 * or not.
 */
class IsBlacklistedUseCaseImpl @Inject constructor(
    private val repository: BlacklistRepository
) : IsBlacklistedUseCase {

    override fun invoke(input: String): Single<Boolean> {
        return repository.isBlacklisted(input)
    }
}