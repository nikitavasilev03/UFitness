package com.example.android.ufitness.di

import com.example.android.ufitness.ui.fragments.exercises.ExercisesFragment
import com.example.android.ufitness.ui.fragments.exercises.edit.EditExerciseFragment
import com.example.android.ufitness.ui.fragments.plans.PlansFragment
import com.example.android.ufitness.ui.fragments.plans.edit.EditPlanFragment
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [ViewModelFactoryModule::class, AppProviderModule::class])
interface AppComponent {

    fun inject(fragment: PlansFragment)
    fun inject(fragment: EditPlanFragment)
    fun inject(fragment: ExercisesFragment)
    fun inject(fragment: EditExerciseFragment)

}