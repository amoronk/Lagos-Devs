package com.amoronk.android.data.local.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.IgnoredOnParcel
import kotlinx.android.parcel.Parcelize


@Parcelize
data class Devs(
    val total: Int = 0,
    val page: Int = 0,
    val devs: List<DevEntity>
) : Parcelable {

    @IgnoredOnParcel
    val endOfPage = total == page


    @Parcelize
    @Entity(tableName = "recent_devs")
    data class DevEntity(
        @PrimaryKey(autoGenerate = true)
        val id: Long = 0,
        val devId: Long,
        @ColumnInfo(name = "userName")
        val userName: String,
        @ColumnInfo(name = "avatarUrl")
        val avatarUrl: String,
        @ColumnInfo(name = "url")
        val url: String,
        @ColumnInfo(name = "score")
        val score: Double,
        @ColumnInfo(name = "isFav")
        val isFav: Boolean

    ) : Parcelable

    @Parcelize
    @Entity(tableName = "dev_remote_keys")
    data class DevRemoteKeys(
        @PrimaryKey val devId: Long,
        val prevKey: Int?,
        val nextKey: Int?
    ) : Parcelable


}