package ru.abdt.coroutine.utils

import kotlinx.coroutines.Job

class CompositeJob {

    private val jobs = mutableListOf<Job>()

    fun add(job: Job) {
        jobs.add(job)
    }

    fun cancel() {
        jobs.forEach { it.cancel() }
    }

    operator fun plusAssign(job: Job) {
        add(job)
    }
}
