package {{ cookiecutter.package_name }}.di.module

import android.content.Context
import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import okhttp3.Cache
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton
import {{ cookiecutter.package_name }}.BuildConfig
{% if cookiecutter.with_list == 'y' %}
import {{ cookiecutter.package_name }}.data.remote.CharacterService
import {{ cookiecutter.package_name }}.data.remote.CharacterRemoteDataSource
{% endif %}
{% if cookiecutter.login_page == 'y' %}
import {{ cookiecutter.package_name }}.data.remote.AuthService
import {{ cookiecutter.package_name }}.data.remote.AuthRemoteDataSource
{% endif %}

@Module
class NetModule {

    @Singleton
    @Provides
    @Named("cached")
    fun provideOkHttpClient(context: Context,
                            @Named("offlineInterceptor") offlineInterceptor: Interceptor,
                            @Named("onlineInterceptor") onlineInterceptor: Interceptor,
                            @Named("authInterceptor") authInterceptor: Interceptor,
                            @Named("loggingInterceptor") loggingInterceptor: Interceptor
    ): OkHttpClient {
        val cacheSize = (10 * 1024 * 1024).toLong() // 10 MB
        val cache = Cache(context.cacheDir, cacheSize)
        return OkHttpClient.Builder()
                .readTimeout(1, TimeUnit.MINUTES)
                .writeTimeout(1, TimeUnit.MINUTES)
                .addInterceptor(authInterceptor)
                .addInterceptor(offlineInterceptor)
                .addNetworkInterceptor(onlineInterceptor)
                .addInterceptor(loggingInterceptor)
                .cache(cache)
                .build()
    }

    @Singleton
    @Provides
    @Named("non_cached")
    fun provideNonCachedOkHttpClient(
            @Named("authInterceptor") authInterceptor: Interceptor,
            @Named("logginInterceptor") loggingInterceptor: Interceptor
    ): OkHttpClient {
        return OkHttpClient.Builder()
                .readTimeout(1, TimeUnit.MINUTES)
                .writeTimeout(1, TimeUnit.MINUTES)
                .addInterceptor(authInterceptor)
                .addInterceptor(loggingInterceptor)
                .build()
    }

    @Provides
    @Singleton
    fun provideGson(): Gson {
        val gsonBuilder = GsonBuilder()
        gsonBuilder.setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
        return gsonBuilder.create()
    }

    @Singleton
    @Provides
    fun provideRetrofit(gson: Gson, @Named("cached") client: OkHttpClient): Retrofit.Builder {
        return Retrofit.Builder()
                .client(client)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
    }

    {% if cookiecutter.with_list == 'y' %}
    /**
     * Example service
     */
    @Provides
    fun provideCharacterService(builder: Retrofit.Builder): CharacterService {
        return builder.baseUrl(BuildConfig.API_URL)
            .build()
            .create(CharacterService::class.java)
    }

    @Singleton
    @Provides
    fun provideCharacterRemoteDataSource(characterService: CharacterService) =
        CharacterRemoteDataSource(characterService)
    {% endif %}

    {% if cookiecutter.login_page == 'y' %}
    @Provides
    fun provideAuthService(builder: Retrofit.Builder): AuthService {
        return builder.baseUrl(BuildConfig.API_URL)
            .build()
            .create(AuthService::class.java)
    }

    @Singleton
    @Provides
    fun provideAuthRemoteDataSource(authService: AuthService) =
        AuthRemoteDataSource(authService)

        {% endif %}
}