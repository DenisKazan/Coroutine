package ru.abdt.coroutine.dagger

import dagger.Component
import ru.abdt.coroutine.app.CoroutineApp
import javax.inject.Singleton

@Component(modules = [AppModule::class],
    dependencies = [ContextProvider::class,
    NetworkProvider::class])
@Singleton
interface AppComponent {
    fun inject(coroutineApp: CoroutineApp)
}
