package sample.dagger

import sample.rx.RxSchedulers
import dagger.Module
import dagger.Provides
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.Executors.newSingleThreadExecutor
import javax.inject.Singleton

/**
 * Module for injecting [RxSchedulers] into application component.
 */
@Module
object RxSchedulersModule {

    @Provides
    @Singleton
    @JvmStatic
    fun providesRxSchedulers() = RxSchedulers(
        io = Schedulers.io(),
        time = Schedulers.computation(),
        database = Schedulers.from(newSingleThreadExecutor()),
        main = AndroidSchedulers.mainThread()
    )
}