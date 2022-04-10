package com.amoronk.android.di

import com.amoronk.android.data.repository.DevRepository
import com.amoronk.android.data.repository.DevRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RemoteModule {

    @Binds
    abstract fun provideDevRepository(devRepository: DevRepositoryImpl): DevRepository

}