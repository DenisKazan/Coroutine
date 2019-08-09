package ru.abdt.coroutine.app

import androidx.multidex.MultiDexApplication
import ru.abdt.coroutine.dagger.ComponentInjector
import timber.log.Timber

class CoroutineApp: MultiDexApplication() {

    private val componentInjector: ComponentInjector by lazy {
        ComponentInjector(this)
    }

    override fun onCreate() {
        super.onCreate()
        plantDebugTree()
        componentInjector.getAppComponent().inject(this)
    }

    private fun plantDebugTree() {
        Timber.plant(object : Timber.DebugTree() {
            override fun createStackElementTag(element: StackTraceElement): String? {
                return String.format(
                    "%s#%s:%s",
                    super.createStackElementTag(element),
                    element.methodName,
                    element.lineNumber
                )
            }
        })
    }
}
