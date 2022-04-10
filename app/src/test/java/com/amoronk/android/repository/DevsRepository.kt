package com.amoronk.android.repository

import com.amoronk.android.data.local.LocalDataSource
import com.amoronk.android.data.local.model.Devs
import com.amoronk.android.data.remote.ApiDataSource
import com.amoronk.android.data.repository.DevRepositoryImpl
import kotlinx.coroutines.runBlocking
import org.junit.Test

class DevsRepository {
    val fakeList= mutableListOf<Devs.DevEntity>().apply {
        add(Devs.DevEntity(
            id = 1,
            devId = 1,
            userName = "amoronk",
            avatarUrl = "",
            url = "",
            score = 0.0,
            isFav = false
        ))
        add(Devs.DevEntity(
            id = 2,
            devId = 2,
            userName = "moronk",
            avatarUrl = "",
            url = "",
            score = 0.0,
            isFav = false
        ))

    }

}