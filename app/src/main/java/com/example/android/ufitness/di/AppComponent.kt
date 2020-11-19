package com.example.android.ufitness.di

import com.example.android.ufitness.ui.MainActivity
import com.example.android.ufitness.ui.fragments.support.SupportFragment
import com.example.android.ufitness.ui.fragments.exercises.ExercisesFragment
import com.example.android.ufitness.ui.fragments.exercises.edit.EditExerciseFragment
import com.example.android.ufitness.ui.fragments.main.MainFragment
import com.example.android.ufitness.ui.fragments.plans.PlansFragment
import com.example.android.ufitness.ui.fragments.plans.edit.EditPlanFragment
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [ViewModelFactoryModule::class, AppProviderModule::class])
interface AppComponent {

    fun inject(fragment: MainFragment)
    fun inject(fragment: PlansFragment)
    fun inject(fragment: EditPlanFragment)
    fun inject(fragment: ExercisesFragment)
    fun inject(fragment: EditExerciseFragment)
    fun inject(fragment: SupportFragment)
    fun inject(activity: MainActivity)
}