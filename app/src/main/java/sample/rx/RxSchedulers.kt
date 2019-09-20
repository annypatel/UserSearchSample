package sample.rx

import io.reactivex.Scheduler

/**
 * Just a wrapper for rx schedulers.
 */
class RxSchedulers(
    val io: Scheduler,
    val time: Scheduler,
    val database: Scheduler,
    val main: Scheduler
)