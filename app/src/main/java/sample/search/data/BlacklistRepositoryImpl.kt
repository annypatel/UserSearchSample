package sample.search.data

import sample.rx.RxSchedulers
import sample.search.data.database.BlacklistedTermDao
import sample.search.data.database.BlacklistedTermEntity
import sample.search.domain.BlacklistRepository
import io.reactivex.Completable
import io.reactivex.Single
import javax.inject.Inject

/**
 * Implementation of [BlacklistRepository] that maintains blacklisted search terms.
 */
class BlacklistRepositoryImpl @Inject constructor(
    private val dao: BlacklistedTermDao,
    private val schedulers: RxSchedulers
) : BlacklistRepository {

    override fun add(searchTerm: String): Completable {
        return dao.insert(BlacklistedTermEntity(term = searchTerm))
            .subscribeOn(schedulers.database)
    }

    override fun isBlacklisted(searchTerm: String): Single<Boolean> {
        return dao.findBy(searchTerm)
            .map { true }
            .onErrorReturn { false }
            .subscribeOn(schedulers.database)
    }
}