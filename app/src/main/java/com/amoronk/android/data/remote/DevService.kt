package com.amoronk.android.data.remote

import com.amoronk.android.data.remote.model.DevResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface DevService {

    @GET("users?q=lagos")
    fun getLagosDevs(
        @Query("page") page: Int?,
        @Query("results") results: Int,
    ): Single<DevResponse>

}