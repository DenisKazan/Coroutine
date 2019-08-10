package ru.abdt.coroutine.utils

import androidx.lifecycle.ViewModel

open class CustomViewModel: ViewModel() {

    protected val jobs = CompositeJob()

    open fun onAttachView() {

    }

    open fun onDetachView() {
        jobs.cancel()
    }
}
