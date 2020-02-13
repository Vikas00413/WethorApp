package com.demo.demotext.di.modules


import com.demo.demotext.MyApplication
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * This is Base Application provider Module
 * use it for get application context
 */
@Module
class AppModule( var mApplication: MyApplication) {

    @Provides
    @Singleton
     fun providesApplication(): MyApplication {
        return mApplication
    }

}
