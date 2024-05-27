package com.dicoding.githubsubmmison.ui.viewmodel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.dicoding.githubsubmmison.data.local.entity.FavUserEntity
import com.dicoding.githubsubmmison.data.repository.FavRepository
import kotlinx.coroutines.launch

class FavoriteViewModel(private val favRepository: FavRepository) : ViewModel() {

    fun getAllFavorite() = favRepository.getAllFavorite()

    fun checkStatusFavorite(username: String) = favRepository.getDataByUsername(username)

    fun insertFav(favEntity: FavUserEntity) {
        viewModelScope.launch {
            favRepository.insertUser(favEntity)
        }
    }

    fun deleteFav(favEntity: FavUserEntity) {
        viewModelScope.launch {
            favRepository.deleteUser(favEntity)
        }
    }
}