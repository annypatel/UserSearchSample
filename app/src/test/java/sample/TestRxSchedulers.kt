package sample

import sample.rx.RxSchedulers
import io.reactivex.Scheduler
import io.reactivex.schedulers.Schedulers

/**
 * [RxSchedulers] for local unit tests that uses [Schedulers.trampoline] scheduler.
 */
fun testRxSchedulers(
    io: Scheduler = Schedulers.trampoline(),
    time: Scheduler = Schedulers.trampoline(),
    database: Scheduler = Schedulers.trampoline(),
    main: Scheduler = Schedulers.trampoline()
) = RxSchedulers(io, time, database, main)