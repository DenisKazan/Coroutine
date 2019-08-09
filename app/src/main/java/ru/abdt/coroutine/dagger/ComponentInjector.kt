package ru.abdt.coroutine.dagger

import androidx.multidex.MultiDexApplication

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
}
