package com.dicoding.githubsubmmison.data.repository

import androidx.lifecycle.LiveData
import com.dicoding.githubsubmmison.data.local.entity.FavUserEntity
import com.dicoding.githubsubmmison.data.local.room.FavoritDao

class FavRepository private constructor(private val mFavDao: FavoritDao) {

    suspend fun insertUser(user: FavUserEntity) = mFavDao.insert(user)

    suspend fun deleteUser(user: FavUserEntity) = mFavDao.delete(user)

    fun getAllFavorite(): LiveData<List<FavUserEntity>> = mFavDao.getAllFavoriteData()

    fun getDataByUsername(username: String): LiveData<FavUserEntity> =
        mFavDao.getDataByUsername(username)

    companion object {
        @Volatile
        private var instant: FavRepository? = null
        fun getInstance(favoritDao: FavoritDao): FavRepository =
            instant ?: synchronized(this)
            {
                instant ?: FavRepository(favoritDao).also { instant = it }
            }
    }
}
