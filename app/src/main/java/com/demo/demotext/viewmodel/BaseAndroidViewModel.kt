package com.demo.demotext.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.demo.demotext.repository.GitHubRepository
import com.demo.demotext.MyApplication
import javax.inject.Inject

/**
 * This is Base view model is extended by FetchCurrentWethorViewModel,BaseAndroidViewModel
 */
abstract class BaseAndroidViewModel : AndroidViewModel {
    @Inject
    lateinit var repositires: GitHubRepository

    constructor(application: Application) : super(application) {
        MyApplication.mGitHubComponent.inject(this)

    }
}