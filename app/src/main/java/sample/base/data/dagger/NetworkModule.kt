package sample.base.data.dagger

import sample.base.data.network.NetworkClient
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Dagger module for exposing [NetworkClient] to application component.
 */
@Module(subcomponents = [NetworkComponent::class])
object NetworkModule {

    @Provides
    @Singleton
    @JvmStatic
    fun networkClient(builder: NetworkComponent.Builder): NetworkClient {
        return builder.build().networkClient()
    }
}
