package com.demo.demotext.di.components





import com.demo.demotext.di.modules.AppModule
import com.demo.demotext.di.modules.NetModule

import com.demo.demotext.MyApplication
import com.demo.demotext.view.activity.BaseActivity
import dagger.Component
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import javax.inject.Singleton

/**
 * This Is NetComponent ,it use provide retrofit object ,httpclient object
 */
@Singleton
@Component(modules = [AppModule::class, NetModule::class])
interface NetComponent {
    fun retrofit(): Retrofit
    fun okHttpClient(): OkHttpClient
    fun inject(baseActivity: BaseActivity)

    fun application(): MyApplication


}