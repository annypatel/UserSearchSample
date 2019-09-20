package sample.base.ui

import io.reactivex.Flowable
import org.hamcrest.CoreMatchers.equalTo
import org.junit.Assert.assertThat
import org.junit.Test

class BaseViewModelTest {

    class TestViewModel : BaseViewModel() {

        fun subscribeAndAutoDispose() {
            Flowable.just(1, 2, 3)
                .subscribe()
                .autoDispose()
        }

        // overridden with public modifier to make it accessible in tests
        public override fun onCleared() {
            super.onCleared()
        }
    }

    @Test
    fun autoDispose_shouldAddDisposable() {
        // GIVEN
        val viewModel = TestViewModel()

        // WHEN
        viewModel.subscribeAndAutoDispose()

        // THEN
        assertThat(viewModel.disposables.size(), equalTo(1))
    }

    @Test
    fun onCleared_shouldClearDisposables() {
        // GIVEN
        val viewModel = TestViewModel()
        viewModel.subscribeAndAutoDispose()

        // WHEN
        viewModel.onCleared()

        // THEN
        assertThat(viewModel.disposables.size(), equalTo(0))
    }
}
