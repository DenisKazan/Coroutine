package ru.abdt.coroutine.dagger

import android.net.Uri
import androidx.multidex.MultiDexApplication
import com.google.gson.Gson
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import dagger.Component
import dagger.Module
import dagger.Provides
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.abdt.coroutine.BuildConfig
import ru.abdt.coroutine.utils.GsonUtils
import timber.log.Timber
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

interface NetworkProvider {

    fun provideOkHttpClient(): OkHttpClient
    fun providesRetrofit(): Retrofit
    fun provideGson(): Gson
    fun provideServerUri(): Uri
}

@Singleton
@Component(modules = [NetworkModule::class],
    dependencies = [ContextProvider::class])
interface NetworkComponent : NetworkProvider {

    class Initializer private constructor() {

        companion object {
            fun init(contextProvider: ContextProvider): NetworkProvider {
                return DaggerNetworkComponent.builder()
                    .contextProvider(contextProvider)
                    .build()
            }
        }
    }
}

@Module
internal object NetworkModule {

    private const val RELEASE_TIMEOUT_MIN = 2L
    private const val DEBUG_TIMEOUT_MIN = 3L

    private val timeOut: Long = if (!BuildConfig.DEBUG) {
        TimeUnit.MINUTES.toMillis(RELEASE_TIMEOUT_MIN)
    } else {
        TimeUnit.MINUTES.toMillis(DEBUG_TIMEOUT_MIN)
    }

    @Provides
    @Singleton
    @JvmStatic
    fun serverUri(): Uri {
        return Uri.parse("https://api.stackexchange.com/2.2/")
    }

    @Provides
    @Singleton
    @JvmStatic
    fun provideGson(): Gson =
        GsonUtils.create()

    @Provides
    @Singleton
    @JvmStatic
    fun provideOkHttpClient(app: MultiDexApplication): OkHttpClient {
        val loggingInterceptor = HttpLoggingInterceptor { message -> Timber.v(message) }
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        return OkHttpClient.Builder()
            .writeTimeout(timeOut, TimeUnit.MILLISECONDS)
            .readTimeout(timeOut, TimeUnit.MILLISECONDS)
            .connectTimeout(timeOut, TimeUnit.MILLISECONDS)
            .cache(Cache(app.applicationContext.cacheDir, Integer.MAX_VALUE.toLong()))
            .addInterceptor(loggingInterceptor)
            .build()
    }

    @Provides
    @Singleton
    @JvmStatic
    fun providesRetrofit(serverUri: Uri,
                         okHttpClient: OkHttpClient,
                         gson: Gson): Retrofit {
        val builder = Retrofit.Builder()
            .baseUrl(serverUri.toString())
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
        return builder.build()
    }
}
