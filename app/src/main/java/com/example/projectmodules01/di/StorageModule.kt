package com.example.projectmodules01.di

import com.example.loginmodule.storage.SharedPreferencesStorage
import com.example.loginmodule.storage.Storage
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

@InstallIn(ActivityComponent::class)
@Module
abstract class StorageModule {

    @Binds
    abstract fun provideStorage(impl: SharedPreferencesStorage) : Storage

}