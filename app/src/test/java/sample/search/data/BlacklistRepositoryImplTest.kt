package sample.search.data

import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import sample.search.data.database.BlacklistedTermDao
import sample.search.data.database.BlacklistedTermEntity
import sample.testRxSchedulers
import io.reactivex.Completable
import io.reactivex.Single
import org.junit.Test

class BlacklistRepositoryImplTest {

    private val mockedDao = mock<BlacklistedTermDao>()
    private val repository = BlacklistRepositoryImpl(mockedDao, testRxSchedulers())

    @Test
    fun add_shouldCallInsert() {
        // GIVEN
        whenever(mockedDao.insert(any()))
            .thenReturn(Completable.complete())

        // WHEN
        repository.add("xyz").subscribe()

        // THEN
        verify(mockedDao).insert(BlacklistedTermEntity(term = "xyz"))
    }

    @Test
    fun isBlacklisted_whenMatchFound_emitsTrue() {
        // GIVEN
        whenever(mockedDao.findBy(any()))
            .thenReturn(Single.just(BlacklistedTermEntity(term = "xyz")))

        // WHEN
        val observer = repository.isBlacklisted("xyz")
            .test()

        // THEN
        observer.assertValue(true)
            .assertComplete()
            .assertNoErrors()
    }

    @Test
    fun isBlacklisted_whenNoMatchFound_emitsFalse() {
        // GIVEN
        whenever(mockedDao.findBy(any()))
            .thenReturn(Single.error(Throwable()))

        // WHEN
        val observer = repository.isBlacklisted("xyz")
            .test()

        // THEN
        observer.assertValue(false)
            .assertComplete()
            .assertNoErrors()
    }
}