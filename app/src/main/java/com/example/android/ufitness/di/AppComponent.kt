package com.example.android.ufitness.di

import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [ViewModelFactoryModule::class])
interface AppComponent {

}