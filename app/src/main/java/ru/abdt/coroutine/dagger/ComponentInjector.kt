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
        appComponent = DaggerAppComponent.builder()
            .appModule(AppModule())
            .networkModule(NetworkModule())
            .contextProvider(contextProvider)
            .build()
    }

    fun getAppComponent(): AppComponent = appComponent

    companion object {
        fun get(context: Context) = (context.applicationContext as CoroutineApp).componentInjector
    }
}
