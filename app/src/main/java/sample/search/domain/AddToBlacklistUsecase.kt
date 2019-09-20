package sample.search.domain

import sample.base.domain.CompletableUseCase
import io.reactivex.Completable
import javax.inject.Inject

/**
 * Use case to add a search term to blacklist.
 */
interface AddToBlacklistUseCase : CompletableUseCase<String>

/**
 * Implementation of [AddToBlacklistUseCase] that uses [BlacklistRepository] to add search term to black list.
 */
class AddToBlacklistUseCaseImpl @Inject constructor(
    private val repository: BlacklistRepository
) : AddToBlacklistUseCase {

    override fun invoke(input: String): Completable {
        return repository.add(input)
    }
}