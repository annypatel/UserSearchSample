package sample.search.data.dagger

import android.content.Context
import dagger.Module
import dagger.Provides
import sample.search.data.network.UserSearchService

@Module
object UserSearchServiceModule {

    @Provides
    @JvmStatic
    fun userService(context: Context): UserSearchService = MockUserSearchService(context)
}
