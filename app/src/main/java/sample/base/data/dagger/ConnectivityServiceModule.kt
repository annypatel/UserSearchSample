package sample.base.data.dagger

import sample.base.data.network.ConnectivityService
import sample.base.data.network.ConnectivityServiceImpl
import dagger.Binds
import dagger.Module

/**
 * Module to inject [ConnectivityService] into the application component.
 */
@Module
abstract class ConnectivityServiceModule {

    @Binds
    abstract fun connectivityService(service: ConnectivityServiceImpl): ConnectivityService
}