package com.amoronk.android.di

import android.content.Context
import com.amoronk.android.data.local.DatabaseService
import com.amoronk.android.data.local.DevDao
import com.amoronk.android.data.local.DevRemoteKeysDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LocaleModule {

    @Provides
    @Singleton
    fun provideDevDatabase(@ApplicationContext appContext: Context): DatabaseService {
        return DatabaseService.getInstance(appContext)
    }

    @Provides
    fun provideDevDao(databaseService: DatabaseService): DevDao {
        return databaseService.devDao()
    }

    @Provides
    fun provideDevRemoteKeysDao(databaseService: DatabaseService): DevRemoteKeysDao {
        return databaseService.devRemote()
    }


}