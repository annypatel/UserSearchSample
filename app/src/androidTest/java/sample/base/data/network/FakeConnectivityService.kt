package sample.base.data.network

import androidx.test.core.app.ApplicationProvider.getApplicationContext
import sample.TestApp
import io.reactivex.Single
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Calls the specified function [block] in offline mode.
 */
inline fun offline(block: () -> Unit) {
    val connectivity = getApplicationContext<TestApp>().component.connectivityService()
    connectivity.offline()
    block()
    connectivity.online()
}

/**
 * Fake for [ConnectivityService], use [online] and [offline] method to control the state of connectivity.
 */
@Singleton
class FakeConnectivityService @Inject constructor() : ConnectivityService {

    private var connected: Boolean = true

    fun online() {
        connected = true
    }

    fun offline() {
        connected = false
    }

    override fun connected(): Single<Boolean> = Single.just(connected)
}