package sample.dagger

import sample.base.data.dagger.NetworkModule
import sample.base.data.network.FakeConnectivityService
import sample.base.ui.dagger.ViewModelFactoryModule
import sample.search.data.dagger.SearchDataModule
import sample.search.domain.dagger.SearchDomainModule
import sample.search.ui.dagger.SearchUiModule
import dagger.Component
import dagger.android.support.AndroidSupportInjectionModule
import okreplay.OkReplayInterceptor
import javax.inject.Singleton

/**
 * Component providing Application scoped instances for espresso tests.
 */
@Singleton
@Component(
    modules = [
        AndroidSupportInjectionModule::class,
        ViewModelFactoryModule::class,
        NetworkModule::class,
        AppModule::class,
        SearchDomainModule::class,
        SearchDataModule::class,
        SearchUiModule::class,
        // test specific modules
        TestRxSchedulersModule::class,
        OkReplayInterceptorModule::class,
        FakeConnectivityServiceModule::class
    ]
)
interface TestAppComponent : AppComponent {

    fun okReplayInterceptor(): OkReplayInterceptor

    fun connectivityService(): FakeConnectivityService

    @Component.Builder
    abstract class Builder : AppComponent.Builder()
}
