package sample.search.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import sample.isError
import sample.isValue
import sample.search.domain.SearchUserUseCase
import sample.search.domain.User
import sample.search.domain.UserSearchResult
import sample.search.ui.UserSearchViewModel.Companion.DEBOUNCE_TIME
import sample.search.ui.UserSearchViewModel.Companion.DEBOUNCE_TIME_UNIT
import sample.testRxSchedulers
import io.reactivex.Single
import io.reactivex.schedulers.TestScheduler
import org.hamcrest.CoreMatchers.nullValue
import org.hamcrest.CoreMatchers.sameInstance
import org.junit.Assert.assertThat
import org.junit.Rule
import org.junit.Test

class UserSearchViewModelTest {

    @get:Rule val instantExecutor = InstantTaskExecutorRule()
    private val mockedSearchUser = mock<SearchUserUseCase>()
    private val viewModel = UserSearchViewModel(mockedSearchUser, testRxSchedulers())

    @Test
    fun onQueryTextChange_whenSearchSuccessful_shouldSetSearchResults() {
        // GIVEN
        val expectedResult = UserSearchResult(setOf(User("b", "B", "url")))
        whenever(mockedSearchUser(any()))
            .thenReturn(Single.just(expectedResult))

        // WHEN
        viewModel.onQueryTextChange("b")

        // THEN
        assertThat(viewModel.searchResults.value, isValue(expectedResult))
    }

    @Test
    fun onQueryTextChange_whenSearchFails_shouldSetError() {
        // GIVEN
        val expectedError = Throwable()
        whenever(mockedSearchUser(any()))
            .thenReturn(Single.error(expectedError))

        // WHEN
        viewModel.onQueryTextChange("b")

        // THEN
        assertThat(viewModel.searchResults.value, isError(expectedError))
    }

    @Test
    fun onQueryTextChange_whenSameSearchTerm_shouldNotSetSearchResultsAgain() {
        // GIVEN
        whenever(mockedSearchUser(any()))
            .thenReturn(Single.just(UserSearchResult.EMPTY))
        viewModel.onQueryTextChange("b")
        val first = viewModel.searchResults.value

        // WHEN
        viewModel.onQueryTextChange("b")

        // THEN
        assertThat(viewModel.searchResults.value, sameInstance(first))
    }

    @Test
    fun onQueryTextChange_whenDebounceTimeNotExpired_shouldNotSetResult() {
        // GIVEN
        whenever(mockedSearchUser(any()))
            .thenReturn(Single.just(UserSearchResult.EMPTY))
        val scheduler = TestScheduler()
        val viewModel = UserSearchViewModel(mockedSearchUser, testRxSchedulers(time = scheduler))

        // WHEN
        viewModel.onQueryTextChange("b")
        scheduler.advanceTimeBy(DEBOUNCE_TIME / 2, DEBOUNCE_TIME_UNIT)

        // THEN
        assertThat(viewModel.searchResults.value, nullValue())
    }
}