package ru.abdt.coroutine.screens.questions.di

import androidx.appcompat.app.AppCompatActivity
import dagger.*
import retrofit2.Retrofit
import ru.abdt.coroutine.dagger.AppComponent
import ru.abdt.coroutine.dagger.appComponent
import ru.abdt.coroutine.screens.questions.*
import ru.abdt.coroutine.utils.BottomSheetHelper
import javax.inject.Scope

@Scope
@Retention(AnnotationRetention.RUNTIME)
annotation class QuestionsScope

@Component(dependencies = [AppComponent::class],
    modules = [QuestionsModule::class])
@QuestionsScope
interface QuestionsComponent {

    fun inject(activity: QuestionsActivity)

    class Initializer private constructor() {
        companion object {
            fun init(activity: AppCompatActivity) =
                    DaggerQuestionsComponent.builder()
                        .appComponent(activity.appComponent)
                        .appCompatActivity(activity)
                        .build()
        }
    }

    @Component.Builder
    interface Builder {
        fun build(): QuestionsComponent
        fun appComponent(appComponent: AppComponent): Builder
        @BindsInstance
        fun appCompatActivity(appCompatActivity: AppCompatActivity): Builder
    }
}

@Module
abstract class QuestionsModule {

    @Binds
    abstract fun bindsRepo(repository: QuestionsRepository): IQuestionsRepository

    @Module
    companion object {

        @QuestionsScope
        @JvmStatic
        @Provides
        fun provideBottomHelper(appCompatActivity: AppCompatActivity) = BottomSheetHelper(appCompatActivity)

        @QuestionsScope
        @JvmStatic
        @Provides
        fun provideViewModelFactory(repository: IQuestionsRepository,
                                    bottomSheetHelper: BottomSheetHelper) = QuestionsViewModel.Factory(repository, bottomSheetHelper)

        @Provides
        @JvmStatic
        @QuestionsScope
        fun provideApi(retrofit: Retrofit): QuestionsApi = retrofit.create(QuestionsApi::class.java)
    }
}
