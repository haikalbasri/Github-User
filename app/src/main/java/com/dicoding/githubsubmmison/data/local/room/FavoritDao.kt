package com.dicoding.githubsubmmison.data.local.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.dicoding.githubsubmmison.data.local.entity.FavUserEntity

@Dao
interface FavoritDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(user: FavUserEntity)

    @Delete
    suspend fun delete(user: FavUserEntity)

    @Query("SELECT * FROM FavUserEntity")
    fun getAllFavoriteData(): LiveData<List<FavUserEntity>>

    @Query("SELECT * FROM FavUserEntity WHERE username = :username")
    fun getDataByUsername(username: String): LiveData<FavUserEntity>

}