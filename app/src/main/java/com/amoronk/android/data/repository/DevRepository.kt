package com.amoronk.android.data.repository

import androidx.paging.PagingData
import com.amoronk.android.data.local.model.Devs
import io.reactivex.Flowable
import io.reactivex.Single

interface DevRepository {
    fun getLagosDevs(): Flowable<PagingData<Devs.DevEntity>>
    fun getDev(id: String): Single<Devs.DevEntity>
    fun getFavDev(isFav: Boolean): Single<List<Devs.DevEntity>>
    fun updateDevDetails(dev: Devs.DevEntity)
}