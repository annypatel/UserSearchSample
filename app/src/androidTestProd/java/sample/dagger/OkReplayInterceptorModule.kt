package sample.dagger

import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoSet
import okhttp3.Interceptor
import okreplay.OkReplayInterceptor
import javax.inject.Singleton

/**
 * Module to inject [OkReplayInterceptor] into the test application component.
 */
@Module
abstract class OkReplayInterceptorModule {

    @Binds
    @IntoSet
    abstract fun bindsOkReplayInterceptor(interceptor: OkReplayInterceptor): Interceptor

    @Module
    companion object {

        @Provides
        @JvmStatic
        @Singleton
        fun okReplayInterceptor(): OkReplayInterceptor = OkReplayInterceptor()
    }
}