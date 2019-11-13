package com.urban.mobility.io.data.interfaces

import io.reactivex.Scheduler

interface ISchedulersProvider {
    fun ui(): Scheduler
    fun io(): Scheduler
}