package sample

import sample.dagger.DaggerTestAppComponent
import sample.dagger.TestAppComponent
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication

/**
 * Base application class for this app while running instrumentation tests.
 */
class TestApp : App() {

    val component: TestAppComponent by lazy {
        DaggerTestAppComponent.builder()
            .create(this) as TestAppComponent
    }

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return component
    }
}