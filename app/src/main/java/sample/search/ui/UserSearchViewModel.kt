package sample.search.ui

import sample.base.ui.BaseViewModel
import sample.base.ui.LiveResult
import sample.base.ui.MutableLiveResult
import sample.base.ui.Result
import sample.rx.RxSchedulers
import sample.search.domain.SearchUserUseCase
import sample.search.domain.UserSearchResult
import io.reactivex.subjects.PublishSubject
import java.util.concurrent.TimeUnit
import javax.inject.Inject

/**
 * ViewModel responsible for reacting to user inputs and initiating search queries.
 */
class UserSearchViewModel @Inject constructor(
    searchUser: SearchUserUseCase,
    schedulers: RxSchedulers
) : BaseViewModel() {

    companion object {
        const val DEBOUNCE_TIME = 300L
        val DEBOUNCE_TIME_UNIT = TimeUnit.MILLISECONDS
    }

    private val searchQuerySubject = PublishSubject.create<String>()

    private val _searchResults = MutableLiveResult<UserSearchResult>()
    val searchResults: LiveResult<UserSearchResult>
        get() = _searchResults

    init {
        searchQuerySubject
            .map { it.trim() }
            .debounce(DEBOUNCE_TIME, DEBOUNCE_TIME_UNIT, schedulers.time)
            .distinctUntilChanged()
            .switchMapSingle { searchTerm ->
                searchUser(searchTerm)
                    .map { Result.value(it) }
                    .onErrorReturn { Result.error(it) }
            }
            .observeOn(schedulers.main)
            .subscribe { _searchResults.value = it }
            .autoDispose()
    }

    fun onQueryTextChange(searchTerm: String) {
        searchQuerySubject.onNext(searchTerm)
    }
}