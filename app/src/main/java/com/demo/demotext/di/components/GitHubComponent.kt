package com.demo.demotext.di.components


import com.demo.demotext.di.modules.GitHubModule
import com.demo.demotext.di.scopes.UserScope
import com.demo.demotext.repository.BaseRepository
import com.demo.demotext.repository.GitHubRepository

import com.demo.demotext.view.activity.BaseActivity
import com.demo.demotext.viewmodel.BaseAndroidViewModel
import dagger.Component

/**
 * This is Api Interface Component ,use it for get api interface service
 */
@UserScope
@Component(dependencies = [NetComponent::class], modules = [GitHubModule::class])
interface GitHubComponent {
    fun inject(activity: BaseActivity)
    fun inject(repository: BaseRepository)
    fun inject(repository: BaseAndroidViewModel)
    fun getGitHubRepository(): GitHubRepository


}
