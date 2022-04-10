package com.amoronk.android.data.remote.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


class DevResponse(
    @SerializedName("total_count")
    @Expose
    val totalCount: Int = 0,

    @SerializedName("incomplete_results")
    @Expose
    val incompleteResults: Boolean = false,

    @SerializedName("items")
    @Expose
    val devs: List<NetworkDev>
)