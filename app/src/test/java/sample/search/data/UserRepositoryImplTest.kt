package sample.search.data

import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.never
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import sample.search.data.database.UserDao
import sample.search.data.database.UserEntity
import sample.base.data.network.ConnectivityService
import sample.search.data.network.UserRaw
import sample.search.data.network.UserSearchResponse
import sample.search.data.network.UserSearchService
import sample.search.domain.User
import sample.search.domain.UserSearchResult
import sample.testRxSchedulers
import io.reactivex.Completable.complete
import io.reactivex.Single.error
import io.reactivex.Single.just
import org.junit.Test

class UserRepositoryImplTest {

    private val userService = mock<UserSearchService>()
    private val connectivityService = mock<ConnectivityService>()
    private val userDao = mock<UserDao>()
    private val repository = UserRepositoryImpl(userService, connectivityService, userDao, testRxSchedulers())

    @Test
    fun fetchUser_whenConnectedToNetwork_shouldPerformSearchAndSave() {
        // GIVEN
        val stubResponse = UserSearchResponse(true, listOf(UserRaw("b", "B", "url")))
        val expectedResult = UserSearchResult(setOf(User("b", "B", "url")))
        val expectedEntities = listOf(UserEntity(username = "b", displayName = "B", avatarUrl = "url"))
        whenever(userService.searchUsers(any()))
            .thenReturn(just(stubResponse))
        whenever(connectivityService.connected())
            .thenReturn(just(true))
        whenever(userDao.insert(any()))
            .thenReturn(complete())

        // WHEN
        val observer = repository.fetchUsers("b")
            .test()

        // THEN
        observer.assertValue(expectedResult)
            .assertComplete()
            .assertNoErrors()
        verify(userService).searchUsers("b")
        verify(userDao, never()).findBy(any())
        verify(userDao).insert(expectedEntities)
    }

    @Test
    fun fetchUser_whenNotConnectedToNetwork_shouldPerformOfflineSearch() {
        // GIVEN
        val stubEntities = listOf(UserEntity(username = "b", displayName = "B", avatarUrl = "url"))
        val expectedResult = UserSearchResult(setOf(User("b", "B", "url")), true)
        whenever(userDao.findBy(any()))
            .thenReturn(just(stubEntities))
        whenever(connectivityService.connected())
            .thenReturn(just(false))

        // WHEN
        val observer = repository.fetchUsers("b")
            .test()

        // THEN
        observer.assertValue(expectedResult)
            .assertComplete()
            .assertNoErrors()
        verify(userService, never()).searchUsers(any())
        verify(userDao).findBy("b")
        verify(userDao, never()).insert(any())
    }

    @Test
    fun fetchUser_whenSearchFails_shouldEmitError() {
        // GIVEN
        val expectedError = Throwable()
        whenever(userService.searchUsers(any()))
            .thenReturn(error(expectedError))
        whenever(connectivityService.connected())
            .thenReturn(just(true))
        whenever(userDao.insert(any()))
            .thenReturn(complete())

        // WHEN
        val observer = repository.fetchUsers("b")
            .test()

        // THEN
        observer.assertError(expectedError)
            .assertNoValues()
            .assertNotComplete()
        verify(userService).searchUsers("b")
        verify(userDao, never()).findBy(any())
        verify(userDao, never()).insert(any())
    }

    @Test
    fun fetchUser_whenOfflineSearchFails_shouldEmitError() {
        // GIVEN
        val expectedError = Throwable()
        whenever(userDao.findBy(any()))
            .thenReturn(error(expectedError))
        whenever(connectivityService.connected())
            .thenReturn(just(false))

        // WHEN
        val observer = repository.fetchUsers("b")
            .test()

        // THEN
        observer.assertError(expectedError)
            .assertNoValues()
            .assertNotComplete()
        verify(userService, never()).searchUsers(any())
        verify(userDao).findBy("b")
        verify(userDao, never()).insert(any())
    }
}