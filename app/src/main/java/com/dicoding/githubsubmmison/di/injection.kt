package com.dicoding.githubsubmmison.di

import android.content.Context
import com.dicoding.githubsubmmison.data.local.room.FavRoomDb
import com.dicoding.githubsubmmison.data.repository.FavRepository

object Injection {

    fun providedRepoFavorite(context: Context): FavRepository {
        val database = FavRoomDb.getDatabase(context)
        val dao = database.favDao()
        return FavRepository.getInstance(dao)
    }
}