package sample.dagger

import androidx.test.espresso.IdlingRegistry
import sample.rx.RxSchedulers
import com.squareup.rx2.idler.Rx2Idler
import dagger.Module
import dagger.Provides
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.Executors.newSingleThreadExecutor
import javax.inject.Singleton

/**
 * Module that wraps schedulers with idling resource using RxIdler before injecting [RxSchedulers] into test
 * application component.
 */
@Module
object TestRxSchedulersModule {

    @Provides
    @Singleton
    @JvmStatic
    fun providesRxSchedulers() = RxSchedulers(
        io = wrap(Schedulers.io(), "io"),
        time = wrap(Schedulers.trampoline(), "time"),
        database = wrap(Schedulers.from(newSingleThreadExecutor()), "database"),
        main = AndroidSchedulers.mainThread()
    )

    @JvmStatic
    private fun wrap(scheduler: Scheduler, name: String): Scheduler {
        val idlingScheduler = Rx2Idler.wrap(scheduler, name)
        IdlingRegistry.getInstance().register(idlingScheduler)
        return idlingScheduler
    }
}