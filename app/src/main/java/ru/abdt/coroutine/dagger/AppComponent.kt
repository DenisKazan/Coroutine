package ru.abdt.coroutine.dagger

import dagger.Component
import retrofit2.Retrofit
import ru.abdt.coroutine.app.CoroutineApp
import ru.abdt.coroutine.screens.questions.QuestionsActivity
import javax.inject.Singleton

@Component(modules = [AppModule::class,
    NetworkModule::class],
    dependencies = [ContextProvider::class])
@Singleton
interface AppComponent {
    fun inject(coroutineApp: CoroutineApp)

    fun retrofit(): Retrofit
}
