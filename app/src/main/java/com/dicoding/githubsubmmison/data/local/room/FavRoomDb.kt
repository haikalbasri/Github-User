package com.dicoding.githubsubmmison.data.local.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.dicoding.githubsubmmison.data.local.entity.FavUserEntity

@Database(entities = [FavUserEntity::class], version = 1, exportSchema = false)
abstract class FavRoomDb : RoomDatabase() {
    abstract fun favDao(): FavoritDao

    companion object {
        @Volatile
        private var INSTANCE: FavRoomDb? = null

        @JvmStatic
        fun getDatabase(context: Context): FavRoomDb =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext,
                    FavRoomDb::class.java, "favorite.db"
                ).build()
            }
    }
}