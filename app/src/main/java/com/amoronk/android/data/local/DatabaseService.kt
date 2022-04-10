package com.amoronk.android.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.amoronk.android.data.local.model.Devs

@Database(entities = [Devs.DevEntity::class, Devs.DevRemoteKeys::class], version = 1)
abstract class DatabaseService : RoomDatabase() {

    companion object {

        private const val DATABASE_NAME = "lagosdevs.db"

        private var instance: DatabaseService? = null

        private fun create(context: Context): DatabaseService =
            Room.databaseBuilder(context, DatabaseService::class.java, DATABASE_NAME)
                .fallbackToDestructiveMigration()
                .build()

        fun getInstance(context: Context): DatabaseService =
            (instance ?: create(context)).also { instance = it }
    }

    abstract fun devDao(): DevDao
    abstract fun devRemote(): DevRemoteKeysDao
}