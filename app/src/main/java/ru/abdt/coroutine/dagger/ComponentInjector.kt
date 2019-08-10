package ru.abdt.coroutine.dagger

import android.content.Context
import androidx.multidex.MultiDexApplication
import ru.abdt.coroutine.app.CoroutineApp

class ComponentInjector(app: MultiDexApplication) {

    private val appComponent: AppComponent

    init {
        val contextProvider = AndroidContextComponent
            .Initializer
            .init(app)
        val networkProvider = NetworkComponent
            .Initializer
            .init(contextProvider)
        appComponent = DaggerAppComponent.builder()
            .appModule(AppModule())
            .contextProvider(contextProvider)
            .networkProvider(networkProvider)
            .build()
    }

    fun getAppComponent(): AppComponent = appComponent

    companion object {
        fun get(context: Context) = (context.applicationContext as CoroutineApp).componentInjector
    }
}
