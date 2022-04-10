package com.amoronk.android.data.remote

import javax.inject.Inject

class ApiDataSource @Inject constructor(private val service: DevService) {

    fun getLagdevs(page: Int?, pageSize: Int) = service.getLagosDevs(page, pageSize)

}
