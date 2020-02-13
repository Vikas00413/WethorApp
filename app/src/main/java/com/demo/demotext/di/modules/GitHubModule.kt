package com.demo.demotext.di.modules



import com.demo.demotext.network.interfaces.GitHubApiInterface
import com.demo.demotext.di.scopes.UserScope
import com.demo.demotext.repository.GitHubRepository
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit

/**
 * This is Api Interface Provider Module
 * this works internally for provide GitHubApiInterface
 * by Dagger
 */
@Module
class GitHubModule {

    @Provides
    @UserScope
    fun providesGitHubInterface(retrofit: Retrofit): GitHubApiInterface {
        return retrofit.create(GitHubApiInterface::class.java)
    }
    @Provides
    @UserScope
    fun provideGitHubRepository(): GitHubRepository {
        return GitHubRepository
    }

}
