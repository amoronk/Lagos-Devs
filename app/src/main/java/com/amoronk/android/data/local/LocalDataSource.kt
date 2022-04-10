package com.amoronk.android.data.local

import javax.inject.Inject

class LocalDataSource @Inject constructor(private val devDb: DatabaseService) {

    fun getDevDao() = devDb.devDao()

    fun  getRemoteKeysDao() = devDb.devRemote()

    fun getDbService() = devDb
}