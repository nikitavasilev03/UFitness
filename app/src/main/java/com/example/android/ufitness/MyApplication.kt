package com.example.android.ufitness

import android.app.Application
import com.example.android.ufitness.di.AppProviderModule
import com.example.android.ufitness.di.DaggerAppComponent

class MyApplication: Application() {

    val appComponent = DaggerAppComponent.builder()
            .appProviderModule(AppProviderModule(this))
            .build()
}