package app.punk.util

import io.reactivex.Scheduler

data class AppSchedulers(
    val io: Scheduler,
    val computation: Scheduler,
    val main: Scheduler
)