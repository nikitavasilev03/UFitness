package com.example.android.ufitness.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.android.ufitness.utils.ViewModelProviderFactory
import dagger.Binds
import dagger.Module

@Module
abstract class ViewModelFactoryModule {

    @Binds
    internal abstract fun bind(factory: ViewModelProviderFactory): ViewModelProvider.Factory


}