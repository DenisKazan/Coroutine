package ru.abdt.coroutine.dagger

import android.content.Context
import androidx.multidex.MultiDexApplication
import dagger.BindsInstance
import dagger.Component
import dagger.Module
import dagger.Provides
import ru.abdt.coroutine.utils.ResourcesProvider
import javax.inject.Singleton

@Singleton
@Component(modules = [AndroidContextModule::class])
interface AndroidContextComponent : ContextProvider {
    @Component.Builder
    interface Builder {
        fun build(): AndroidContextComponent
        @BindsInstance
        fun app(app: MultiDexApplication): Builder
    }

    class Initializer private constructor() {
        companion object {

            fun init(app: MultiDexApplication): ContextProvider =
                DaggerAndroidContextComponent.builder()
                    .app(app)
                    .build()
        }
    }
}

@Module
object AndroidContextModule {

    @Provides
    @JvmStatic
    fun provideContext(app: MultiDexApplication): Context =
        app.applicationContext

    @Provides
    @JvmStatic
    fun provideResources(app: MultiDexApplication): ResourcesProvider =
        ResourcesProvider.AndroidResourcesProvider(app.applicationContext)
}

interface ContextProvider {

    fun provideContext(): Context

    fun provideApp(): MultiDexApplication

    fun provideResources(): ResourcesProvider
}
