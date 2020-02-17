package com.demo.demotext

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import com.demo.demotext.di.components.DaggerGitHubComponent
import com.demo.demotext.di.components.DaggerNetComponent.builder
import com.demo.demotext.di.components.GitHubComponent
import com.demo.demotext.di.components.NetComponent
import com.demo.demotext.di.modules.AppModule
import com.demo.demotext.di.modules.GitHubModule
import com.demo.demotext.di.modules.NetModule


/**
 * This is Base Application class used in AndroidManifest.xml in application tag
 * @author vikas kesharvani
 */
class MyApplication : Application() {

     val isNetworkConnected: Boolean
        get() {
            val cm = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

            val activeNetwork = cm.activeNetworkInfo
            return activeNetwork != null && activeNetwork.isConnectedOrConnecting
        }

    override fun onCreate() {
        super.onCreate()

        if (instance == null) {
            instance = this
        }
        mNetComponent = builder()
            .appModule(AppModule(this))
            .netModule(NetModule(BuildConfig.BASE_URL))

            .build()

        mGitHubComponent = DaggerGitHubComponent.builder()
            .netComponent(mNetComponent)
            .gitHubModule(GitHubModule())
            .build()

    }

    companion object {
        @JvmStatic  lateinit var mNetComponent: NetComponent
        @JvmStatic lateinit var mGitHubComponent: GitHubComponent
        public var instance: MyApplication? = null
             set

        /**
         * This method is use for network connectivity
         */
        fun hasNetwork(): Boolean {
            return instance!!.isNetworkConnected
        }
    }

    fun getNetComponent(): NetComponent {
        return mNetComponent
    }

    fun getGitHubComponent(): GitHubComponent {
        return mGitHubComponent
    }
}
