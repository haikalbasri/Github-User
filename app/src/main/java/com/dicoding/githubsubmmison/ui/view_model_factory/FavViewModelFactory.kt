package com.dicoding.githubsubmmison.ui.view_model_factory

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dicoding.githubsubmmison.data.repository.FavRepository
import com.dicoding.githubsubmmison.di.Injection
import com.dicoding.githubsubmmison.ui.viewmodel.FavoriteViewModel
import java.lang.IllegalArgumentException

class FavViewModelFactory(private val favRepo: FavRepository) :
    ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FavoriteViewModel::class.java)) {
            return FavoriteViewModel(favRepo) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }

    companion object {
        @Volatile
        private var instance: FavViewModelFactory? = null
        fun getInstance(context: Context): FavViewModelFactory = instance ?: synchronized(this) {
            instance ?: FavViewModelFactory(Injection.providedRepoFavorite(context))
        }.also { instance = it }
    }
}
