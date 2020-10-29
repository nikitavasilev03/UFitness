package com.example.android.ufitness.di

import com.example.android.ufitness.ui.fragments.MainFragment
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [ViewModelFactoryModule::class])
interface AppComponent {

    fun inject(fragment: MainFragment)
}