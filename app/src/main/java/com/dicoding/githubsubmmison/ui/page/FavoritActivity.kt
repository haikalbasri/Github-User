package com.dicoding.githubsubmmison.ui.page

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.githubsubmmison.R
import com.dicoding.githubsubmmison.ui.viewmodel.FavoriteViewModel
import com.dicoding.githubsubmmison.data.local.entity.FavUserEntity
import com.dicoding.githubsubmmison.data.remote.response.ItemsItem
import com.dicoding.githubsubmmison.databinding.ActivityFavoritBinding
import com.dicoding.githubsubmmison.ui.adapter.ListUserAdapter
import com.dicoding.githubsubmmison.ui.view_model_factory.FavViewModelFactory

class FavoritActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFavoritBinding
    private lateinit var adapter: ListUserAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoritBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initRecyclerView()

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        supportActionBar?.title = getString(R.string.favorite)

        val favViewModelFactory = FavViewModelFactory.getInstance(this)
        val favViewModel: FavoriteViewModel by viewModels {
            favViewModelFactory
        }

        favViewModel.getAllFavorite().observe(this) { user ->
            setFavoriteData(user)
            if (user.isNullOrEmpty()) {
                Toast.makeText(
                    this,
                    "Anda sama sekali tidak memfavoritkan pengguna mana pun",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun setFavoriteData(listFav: List<FavUserEntity>) {
        val items = listFav.map { favEntity ->
            ItemsItem(login = favEntity.username, avatarUrl = favEntity.avatarUrl)
        }

        if (!::adapter.isInitialized) {
            adapter = ListUserAdapter()
            binding.rvFavorit.adapter = adapter
        }
        adapter.submitList(items)
    }

    private fun initRecyclerView() {
        binding.rvFavorit.layoutManager = LinearLayoutManager(this)
        binding.rvFavorit.addItemDecoration(
            DividerItemDecoration(
                this,
                LinearLayoutManager.VERTICAL
            )
        )
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }

}