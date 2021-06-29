package learn.clean_architecture.android.external.scheduler

import io.reactivex.Scheduler

interface SchedulerProvider{
    fun ui(): Scheduler
    fun computation(): Scheduler
    fun io(): Scheduler
    fun mainThread() : Scheduler
}