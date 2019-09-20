package sample.base.data.dagger

import sample.base.data.network.NetworkClient
import dagger.Module
import dagger.Provides
import dagger.Subcomponent
import dagger.multibindings.IntoSet
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named

/**
 * NetworkComponent exposes [NetworkClient] with [networkClient] provision method, dependencies declared via
 * [Subcomponent.modules] won't exposed outside this component.
 */
@Subcomponent(modules = [InternalNetworkModule::class])
interface NetworkComponent {

    @Named("internal")
    fun networkClient(): NetworkClient

    @Subcomponent.Builder
    interface Builder {

        fun build(): NetworkComponent
    }
}

/**
 * Dagger module for [NetworkComponent]. Defines dependencies required to constructs [NetworkClient]. All the
 * dependencies declared here is internal to [NetworkComponent] and won't be accessible outside.
 */
@Module
object InternalNetworkModule {

    private const val apiUrl = "https://search-users.example.com/"

    @Provides
    @JvmStatic
    @Named("internal")
    fun networkClient(retrofit: Retrofit): NetworkClient {
        return NetworkClient(retrofit)
    }

    @Provides
    @JvmStatic
    fun retrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(apiUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(okHttpClient)
            .build()
    }

    @Provides
    @JvmStatic
    fun okHttpClient(interceptors: Set<@JvmSuppressWildcards Interceptor>): OkHttpClient {
        return OkHttpClient.Builder()
            .apply {
                interceptors.forEach { addInterceptor(it) }
            }.build()
    }

    @IntoSet
    @Provides
    @JvmStatic
    fun httpLoggingInterceptor(): Interceptor {
        return HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
    }
}
