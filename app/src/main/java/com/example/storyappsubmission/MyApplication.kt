package com.example.storyappsubmission

import android.app.Application
import com.example.storyappsubmission.di.modules.AddStoryModule
import com.example.storyappsubmission.di.modules.DetailStoryModule
import com.example.storyappsubmission.di.modules.StoryModule
import com.example.storyappsubmission.di.modules.dataStoreModule
import com.example.storyappsubmission.di.modules.userModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@MyApplication)
            androidLogger(Level.ERROR)
            modules(listOf(dataStoreModule, userModule, StoryModule, DetailStoryModule, AddStoryModule))
        }
    }
}