package com.example.android.ufitness.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.android.ufitness.ui.fragments.exercises.ExercisesViewModel
import com.example.android.ufitness.ui.fragments.main.MainFViewModel
import com.example.android.ufitness.utils.vm.ViewModelKey
import com.example.android.ufitness.utils.vm.ViewModelProviderFactory
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelFactoryModule {

    @Binds
    internal abstract fun bind(factory: ViewModelProviderFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(MainFViewModel::class)
    abstract fun bindsMainFViewModel(viewModel: MainFViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ExercisesViewModel::class)
    abstract fun bindsExercisesViewModel(viewModel: ExercisesViewModel): ViewModel

}