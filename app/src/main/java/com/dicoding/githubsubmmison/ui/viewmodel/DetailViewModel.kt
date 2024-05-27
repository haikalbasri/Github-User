package com.dicoding.githubsubmmison.ui.viewmodel

import android.util.Log
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dicoding.githubsubmmison.data.remote.response.DetailResponse
import com.dicoding.githubsubmmison.data.remote.response.ItemsItem
import com.dicoding.githubsubmmison.data.remote.api.ApiConfig
import com.dicoding.githubsubmmison.databinding.ActivityMainBinding
import com.dicoding.githubsubmmison.ui.page.DetailUserActivity
import com.dicoding.githubsubmmison.ui.page.MainActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailViewModel : ViewModel() {

    private val _detailUser = MutableLiveData<DetailResponse>()
    val detailuser: LiveData<DetailResponse> = _detailUser

    private val _following = MutableLiveData<List<ItemsItem>?>()
    val following: LiveData<List<ItemsItem>?> = _following

    private val _loading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _loading

    fun getUserDetail(username: String = "") {
        _loading.value = true
        val apiService = ApiConfig.getApiService().getDetailUser(username)
        apiService.enqueue(object : Callback<DetailResponse> {
            override fun onResponse(
                call: Call<DetailResponse>,
                response: Response<DetailResponse>
            ) {
                _loading.value = false
                if (response.isSuccessful) {
                    _detailUser.value = response.body()
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<DetailResponse>, t: Throwable) {
                _loading.value = false
                Log.e(TAG, "onFailure: ${t.message}")
            }
        })
    }


    companion object {
        private const val TAG = "DetailViewModel"
    }
}