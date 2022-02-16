package com.example.live17work.myViewModel

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module


val mGithubListModel = module {
    // MyViewModel ViewModel
    viewModel {
        GithubListModel()
    }
}

val mGithubUserModel = module {

    // MyViewModel ViewModel
    viewModel {
        GithubUserModel()
    }
}