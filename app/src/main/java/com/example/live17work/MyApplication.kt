package com.example.live17work

import android.app.Application
import com.example.live17work.myViewModel.mGithubListModel
import com.example.live17work.myViewModel.mGithubUserModel
import org.koin.core.context.startKoin

class MyApplication: Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin{
//            androidLogger()
//            androidContext(this@MyApplication)
            modules(listOf(mGithubListModel, mGithubUserModel))
        }
    }
}