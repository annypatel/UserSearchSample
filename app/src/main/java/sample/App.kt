package sample

import sample.dagger.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import sample.search.BuildConfig
import timber.log.Timber

open class App : DaggerApplication() {

    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return DaggerAppComponent.builder()
            .create(this)
    }
}