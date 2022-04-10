package com.amoronk.android.data.local

import androidx.paging.PagingSource
import androidx.room.*
import com.amoronk.android.data.local.model.Devs
import io.reactivex.Completable
import io.reactivex.Single

@Dao
interface DevDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveDevsData(recentDevs: List<Devs.DevEntity>)

    @Query("SELECT * FROM recent_devs ORDER BY id ASC")
    fun getAllRecentDevs(): PagingSource<Int, Devs.DevEntity>

    @Query("SELECT * FROM recent_devs WHERE id = :id LIMIT 1")
    fun getDevData(id: String): Single<Devs.DevEntity>

    @Query("SELECT * FROM recent_devs WHERE isFav = :isFav")
    fun getFavDevs(isFav: Boolean): Single<List<Devs.DevEntity>>

    @Update
    fun updateDevData(devEntity: Devs.DevEntity): Completable

    @Query("DELETE FROM recent_devs")
    fun clearDevs()
}