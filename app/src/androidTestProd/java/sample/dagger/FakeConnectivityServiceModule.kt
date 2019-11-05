package sample.dagger

import sample.base.data.network.ConnectivityService
import sample.base.data.network.FakeConnectivityService
import dagger.Binds
import dagger.Module

/**
 * Module to inject [ConnectivityService] into the test application component.
 */
@Module
abstract class FakeConnectivityServiceModule {

    @Binds
    abstract fun connectivityService(service: FakeConnectivityService): ConnectivityService
}