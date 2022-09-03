package {{ cookiecutter.package_name }}.di.module

import android.content.Context
import android.content.SharedPreferences
import dagger.Module
import dagger.Provides
import {{ cookiecutter.package_name }}.BuildConfig
import {{ cookiecutter.package_name }}.utils.extensions.fetchAuthToken
import {{ cookiecutter.package_name }}.utils.extensions.isInternetAvailable
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import javax.inject.Named
import javax.inject.Singleton

@Module
class InterceptorModule {
    private val maxAge = 60 // read from cache for 60 seconds even if there is internet connection
    private val maxStale = 60 * 60 *24 * 30 // offline cache available for 30 days

    @Singleton
    @Provides
    @Named(value = "onlineInterceptor")
    fun provideOnlineInterceptor() = Interceptor { chain ->
        val response: Response = chain.proceed(chain.request())
        response.newBuilder()
                .header("Cache-Control", "public, max-age=$maxAge")
                .removeHeader("Pragma")
                .build()
    }

    @Singleton
    @Provides
    @Named(value = "offlineInterceptor")
    fun provideOfflineInterceptor(context: Context) = Interceptor { chain ->
        var request: Request = chain.request()
        if (!context.isInternetAvailable()) {
            request = request.newBuilder()
                    .header("Cache-Control", "public, only-if-cached, max-stale=$maxStale")
                    .removeHeader("Pragma")
                    .build()
        }
        chain.proceed(request)
    }

    @Singleton
    @Provides
    @Named("authInterceptor")
    fun provideAuthInterceptor(pref: SharedPreferences) = Interceptor { chain ->
        var request: Request = chain.request()
        pref.fetchAuthToken()?.let {
            request = request.newBuilder()
                    .addHeader("Authorization", "Bearer $it").build()
        }
        chain.proceed(request)
    }

    @Singleton
    @Provides
    @Named("loggingInterceptor")
    fun provideLoggingInterceptor(): Interceptor {
        return when (BuildConfig.DEBUG) {
            false -> Interceptor { chain -> chain.proceed(chain.request()) }
            else -> HttpLoggingInterceptor().apply { this.level = HttpLoggingInterceptor.Level.HEADERS }
        }
    }

}