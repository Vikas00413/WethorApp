package com.demo.demotext.di.modules

import android.util.Log

import com.demo.demotext.MyApplication
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import dagger.Module
import dagger.Provides
import okhttp3.Cache
import okhttp3.CacheControl
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton

/**
 * This class works internally by dagger for provide base module
 * Eg. cache, Gson, HttpClient ,Retrofit,Interceptor
 * @author vikas Kesharvani
 */
@Module
class NetModule(var mBaseUrl: String) {
    private val TAG = "NetModule"
    val HEADER_PRAGMA = "Pragma"
    val HEADER_CACHE_CONTROL = "Cache-Control"
    /**
     * This method is use for provide cache ,
     * Retrofit will use it for api caching,
     * Httpclient will use this cache
     * @param cacheSize is cache size ,you can change it as per your requirement
     */
    @Provides
    @Singleton
    fun provideOkHttpCache(): Cache {
        val cacheSize = 5 * 1024 * 1024
        return Cache(
            File(MyApplication.instance!!.cacheDir, "demo_app"),
            cacheSize.toLong()
        )
    }

    /**
     * This method works internally by dagger for providing
     * Gson to Retrofit
     */
    @Provides  // Dagger will only look for methods annotated with @Provides
    @Singleton
    fun provideGson(): Gson {
        val gsonBuilder = GsonBuilder()
        // gsonBuilder.setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
        return gsonBuilder.create()
    }


    /**
     * This method works internally for providing httpclient
     * to retrofit
     * @param cache is provided by provideOkHttpCache method
     * @param networkInterceptor is provided by provideNetworkInterceptor method
     * @param offlineInterceptor is provided by provideOfflineInterceptor method
     * @param httpLoggingInterceptor is provided by provideHttpLoggingInterceptor method
     *
     */
    @Provides
    @Singleton
    fun provideOkHttpClient(
        cache: Cache,
        @Named("Offline") offlineInterceptor: Interceptor,
        @Named("Online") networkInterceptor: Interceptor,
        httpLoggingInterceptor: HttpLoggingInterceptor
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .cache(cache)
            .addInterceptor(httpLoggingInterceptor) // used if network off OR on
            .addNetworkInterceptor(networkInterceptor) // only used when network is on
            .addInterceptor(offlineInterceptor)
            .build()
    }

    /**
     * This method works internally by dagger for providing Retrofit
     * @param gson is provided by provideGson method
     * @param okHttpClient s provided by provideOkHttpClient method
     */
    @Provides
    @Singleton
    fun provideRetrofit(gson: Gson, okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .baseUrl(mBaseUrl)
            .client(okHttpClient)
            .build()
    }

    /**
     * This interceptor will be called ONLY if the network is available
     * @return
     */
    @Provides
    @Named("Online")
    @Singleton
    fun provideNetworkInterceptor(): Interceptor {
        return Interceptor { chain ->

            Log.d(TAG, "network interceptor: called.")

            val response = chain.proceed(chain.request())

            val cacheControl = CacheControl.Builder()
                .maxAge(5, TimeUnit.SECONDS)
                .build()

            response.newBuilder()
                .removeHeader(HEADER_PRAGMA)
                .removeHeader(HEADER_CACHE_CONTROL)
                .header(HEADER_CACHE_CONTROL, cacheControl.toString())
                .build()
        }
    }

    /**
     * This method works internally by dagger for providing HttpLoggingInterceptor
     * to provideOkHttpClient
     */
    @Provides
    @Singleton
    fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor {
        val httpLoggingInterceptor =
            HttpLoggingInterceptor(HttpLoggingInterceptor.Logger { message ->
                Log.d(
                    TAG,
                    "log: http log: $message"
                )
            })
        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        return httpLoggingInterceptor
    }

    /**
     * This interceptor will be called both if the network is available and if the network is not available
     * @return
     */
    @Provides
    @Named("Offline")
    @Singleton
    fun provideOfflineInterceptor(): Interceptor {
        return Interceptor { chain ->
            Log.d(TAG, "offline interceptor: called.")
            var request = chain.request()

            // prevent caching when network is on. For that we use the "networkInterceptor"
            if (!MyApplication.hasNetwork()) {
                val cacheControl = CacheControl.Builder()
                   /* .maxStale(7, TimeUnit.DAYS)*/
                    .build()

                request = request.newBuilder()
                    .removeHeader(HEADER_PRAGMA)
                    .removeHeader(HEADER_CACHE_CONTROL)
                    .cacheControl(cacheControl)
                    .build()
            }

            chain.proceed(request)
        }
    }



}
