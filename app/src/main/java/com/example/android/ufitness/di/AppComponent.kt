package com.example.android.ufitness.di

import com.example.android.ufitness.ui.fragments.exercises.ExercisesFragment
import com.example.android.ufitness.ui.fragments.main.MainFragment
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [ViewModelFactoryModule::class, AppProviderModule::class])
interface AppComponent {

    fun inject(fragment: MainFragment)
    fun inject(fragment: ExercisesFragment)
}