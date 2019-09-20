package sample.base.ui

import androidx.lifecycle.ViewModel
import org.hamcrest.CoreMatchers.instanceOf
import org.junit.Assert.assertThat
import org.junit.Assert.fail
import org.junit.Test
import javax.inject.Provider

class ViewModelFactoryTest {

    @Test
    fun create_whenMapHasViewModel_returnsViewModel() {
        // GIVEN
        val factory = ViewModelFactory(mapOf(
            TestViewModel::class.java to Provider<ViewModel> { TestViewModel() }
        ))

        // WHEN
        val viewModel = factory.create(TestViewModel::class.java)

        // THEN
        assertThat(viewModel, instanceOf(TestViewModel::class.java))
    }

    @Test
    fun create_whenMapHasChildViewModel_returnsChildViewModel() {
        // GIVEN
        val factory = ViewModelFactory(mapOf(
            ChildTestViewModel::class.java to Provider<ViewModel> { ChildTestViewModel() }
        ))

        // WHEN
        val viewModel = factory.create(TestViewModel::class.java)

        // THEN
        assertThat(viewModel, instanceOf(ChildTestViewModel::class.java))
    }

    @Test
    fun create_whenMapIsEmpty_throwsException() {
        // GIVEN
        val factory = ViewModelFactory(mapOf())

        try {
            // WHEN
            factory.create(TestViewModel::class.java)
            fail("Exception not thrown")
        } catch (_: IllegalStateException) {
            // THEN
        }
    }

    private open class TestViewModel : ViewModel()
    private class ChildTestViewModel : TestViewModel()
}