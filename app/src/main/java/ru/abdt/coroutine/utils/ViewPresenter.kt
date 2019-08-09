package ru.abdt.coroutine.utils

import kotlinx.coroutines.Job

import java.lang.ref.WeakReference

abstract class ViewPresenter<V : IView> {

    protected val jobs = CompositeJob()

    private var mViewRef: WeakReference<V>? = null

    protected var view: V?
        get() = mViewRef?.get()
        set(view) {
            mViewRef = WeakReference<V>(view)
        }

    fun onAttachView() {

    }

    fun onDetachView() {
        jobs.cancel()
        mViewRef?.clear()
    }

    protected fun unsubscribeOnDestroy(job: Job) {
        jobs.add(job)
    }
}
