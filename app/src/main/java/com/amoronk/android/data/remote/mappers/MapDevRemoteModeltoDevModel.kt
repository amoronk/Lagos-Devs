@file:Suppress("FunctionName")

package com.amoronk.android.data.remote.mappers

import com.amoronk.android.data.local.model.Devs
import com.amoronk.android.data.remote.model.DevResponse

fun mapDevRemoteModeltoDevModel(response: DevResponse): Devs {
    return with(response) {
        Devs(
            totalCount,
            0,
            devs.map {
                Devs.DevEntity(
                    0,
                    it.id.toLong(),
                    it.login,
                    it.avatarUrl,
                    it.url,
                    it.score,
                    false
                )
            }


        )
    }
}
