package sample.search.data.dagger

import dagger.Module
import dagger.Provides
import sample.base.data.network.NetworkClient
import sample.search.data.network.UserSearchService

@Module
object UserSearchServiceModule {

    @Provides
    @JvmStatic
    fun userService(networkClient: NetworkClient): UserSearchService {
        return networkClient.create(UserSearchService::class)
    }
}
