package sample.search.domain

import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.never
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.verifyZeroInteractions
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.Single
import org.junit.Test

class SearchUserUseCaseImplTest {

    private val mockedIsBlacklisted = mock<IsBlacklistedUseCase>()
    private val mockedAddToBlacklist = mock<AddToBlacklistUseCase>()
    private val mockedRepository = mock<UserRepository>()
    private val searchUser = SearchUserUseCaseImpl(mockedIsBlacklisted, mockedAddToBlacklist, mockedRepository)

    @Test
    fun searchUser_whenSearchTermIsEmpty_shouldEmitEmptyResult() {
        // WHEN
        val observer = searchUser("").test()

        // THEN
        observer.assertValue(UserSearchResult.EMPTY)
            .assertComplete()
            .assertNoErrors()
        verifyZeroInteractions(mockedIsBlacklisted)
        verifyZeroInteractions(mockedAddToBlacklist)
        verifyZeroInteractions(mockedRepository)
    }

    @Test
    fun searchUser_whenNotBlacklisted_shouldPerformSearch() {
        // GIVEN
        whenever(mockedIsBlacklisted(any()))
            .thenReturn(Single.just(false))

        // WHEN
        searchUser("b").test()

        // THEN
        verify(mockedRepository).fetchUsers("b")
    }

    @Test
    fun searchUser_whenBlacklisted_shouldNotPerformSearch() {
        // GIVEN
        whenever(mockedIsBlacklisted(any()))
            .thenReturn(Single.just(true))

        // WHEN
        val observer = searchUser("b").test()

        // THEN
        observer.assertValue(UserSearchResult.EMPTY)
            .assertComplete()
            .assertNoErrors()
        verify(mockedRepository, never()).fetchUsers("b")
        verify(mockedAddToBlacklist, never())("b")
    }

    @Test
    fun searchUser_whenNoResultFound_shouldAddToBlacklist() {
        // GIVEN
        whenever(mockedIsBlacklisted(any()))
            .thenReturn(Single.just(false))
        val stubResult = UserSearchResult(emptySet(), false)
        whenever(mockedRepository.fetchUsers(any()))
            .thenReturn(Single.just(stubResult))

        // WHEN
        searchUser("b").test()

        // THEN
        verify(mockedAddToBlacklist)("b")
    }

    @Test
    fun searchUser_whenOfflineNoResultFound_shouldNotAddToBlacklist() {
        // GIVEN
        whenever(mockedIsBlacklisted(any()))
            .thenReturn(Single.just(false))
        val stubResult = UserSearchResult(emptySet(), true)
        whenever(mockedRepository.fetchUsers(any()))
            .thenReturn(Single.just(stubResult))

        // WHEN
        searchUser("b").test()

        // THEN
        verify(mockedAddToBlacklist, never())("b")
    }

    @Test
    fun searchUser_whenResultFound_shouldNotAddToBlacklist() {
        // GIVEN
        whenever(mockedIsBlacklisted(any()))
            .thenReturn(Single.just(false))
        val stubResult = UserSearchResult(setOf(User("b", "B", "url")))
        whenever(mockedRepository.fetchUsers(any()))
            .thenReturn(Single.just(stubResult))

        // WHEN
        searchUser("b").test()

        // THEN
        verify(mockedAddToBlacklist, never())("b")
    }
}