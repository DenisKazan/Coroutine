package ru.abdt.coroutine.utils

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer

inline fun <T> LiveData<T>.observe(lifecycleOwner: LifecycleOwner,
                                  crossinline onHandleContent: (T?) -> Unit) {
    observe(lifecycleOwner, Observer { onHandleContent(it) })
}

inline fun <T> LiveData<T>.safeObserve(lifecycleOwner: LifecycleOwner,
                                       crossinline onHandleContent: (T) -> Unit) {
    observe(lifecycleOwner, Observer { it?.let(onHandleContent) })
}

inline fun <T> LiveData<Event<T>>.observeEvent(lifecycleOwner: LifecycleOwner,
                                               crossinline onEventUnhandledContent: (T) -> Unit) {
    observe(lifecycleOwner, Observer { it?.getContentIfNotHandled()?.let(onEventUnhandledContent) })
}
