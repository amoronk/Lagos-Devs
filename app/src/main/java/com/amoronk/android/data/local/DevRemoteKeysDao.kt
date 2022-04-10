package com.amoronk.android.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.amoronk.android.data.local.model.Devs

@Dao
interface DevRemoteKeysDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(remoteKey: List<Devs.DevRemoteKeys>)

    @Query("SELECT * FROM dev_remote_keys WHERE devId = :devId")
    fun remoteKeysByDevId(devId: Long): Devs.DevRemoteKeys?

    @Query("DELETE FROM dev_remote_keys")
    fun clearRemoteKeys()

}