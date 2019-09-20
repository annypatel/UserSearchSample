package sample.base.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LifecycleRegistry
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.verifyZeroInteractions
import com.nhaarman.mockitokotlin2.whenever
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class LiveResultTest {

    @get:Rule val instantExecutor = InstantTaskExecutorRule()
    private val owner = mock<LifecycleOwner>()
    private val lifecycle = LifecycleRegistry(owner)

    private val liveResult = MutableLiveResult<String>()
    private val mockedValue = mock<(String) -> Unit>()
    private val mockedError = mock<(Throwable) -> Unit>()
    private val mockedComplete = mock<() -> Unit>()

    @Before
    fun setup() {
        whenever(owner.lifecycle).thenReturn(lifecycle)
        lifecycle.handleLifecycleEvent(Lifecycle.Event.ON_RESUME)
    }

    @Test
    fun observe_whenValue_shouldCallValue() {
        // GIVEN
        val expectedValue = "xyz"
        liveResult.value = Result.value(expectedValue)

        // WHEN
        liveResult.observe(owner, mockedValue, mockedError, mockedComplete)

        // THEN
        verify(mockedValue)(expectedValue)
        verifyZeroInteractions(mockedError)
        verifyZeroInteractions(mockedComplete)
    }

    @Test
    fun observe_whenError_shouldCallError() {
        // GIVEN
        val expectedError = Throwable()
        liveResult.value = Result.error(expectedError)

        // WHEN
        liveResult.observe(owner, mockedValue, mockedError, mockedComplete)

        // THEN
        verify(mockedError)(expectedError)
        verifyZeroInteractions(mockedValue)
        verifyZeroInteractions(mockedComplete)
    }

    @Test
    fun observe_whenComplete_shouldCallComplete() {
        // GIVEN
        liveResult.value = Result.complete()

        // WHEN
        liveResult.observe(owner, mockedValue, mockedError, mockedComplete)

        // THEN
        verify(mockedComplete)()
        verifyZeroInteractions(mockedValue)
        verifyZeroInteractions(mockedError)
    }
}