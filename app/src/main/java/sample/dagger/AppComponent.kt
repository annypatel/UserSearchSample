package sample.dagger

import sample.App
import sample.base.data.dagger.NetworkModule
import sample.base.ui.dagger.ViewModelFactoryModule
import sample.base.data.dagger.ConnectivityServiceModule
import sample.search.data.dagger.SearchDataModule
import sample.search.domain.dagger.SearchDomainModule
import sample.search.ui.dagger.SearchUiModule
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

/**
 * Component providing Application scoped instances.
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
        RxSchedulersModule::class,
        ConnectivityServiceModule::class
    ]
)
interface AppComponent : AndroidInjector<App> {

    @Component.Builder
    abstract class Builder : AndroidInjector.Builder<App>()
}