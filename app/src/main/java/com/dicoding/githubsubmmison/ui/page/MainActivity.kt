package com.dicoding.githubsubmmison.ui.page

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.githubsubmmison.R
import com.dicoding.githubsubmmison.data.remote.response.ItemsItem
import com.dicoding.githubsubmmison.databinding.ActivityMainBinding
import com.dicoding.githubsubmmison.ui.viewmodel.MainViewModel
import com.dicoding.githubsubmmison.ui.adapter.ListUserAdapter
import com.dicoding.githubsubmmison.ui.page.darktheme.DarkModeActivity
import com.dicoding.githubsubmmison.ui.view_model_factory.SettViewModelFactory
import com.dicoding.githubsubmmison.ui.viewmodel.SettingViewModel
import com.dicoding.githubsubmmison.utils.SettingPreferences
import com.dicoding.githubsubmmison.utils.dataStore


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val mainViewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val pref = SettingPreferences.getInstance(application.dataStore)
        val setViewModel = ViewModelProvider(this, SettViewModelFactory(pref)).get(
            SettingViewModel::class.java
        )
        setViewModel.getThemeSettings().observe(this) { isDarkModeActive: Boolean ->
            if (isDarkModeActive) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }


        supportActionBar?.hide()

        val layoutManager = LinearLayoutManager(this)
        binding.rvUser.layoutManager = layoutManager

        val itemDecoration = DividerItemDecoration(this, layoutManager.orientation)
        binding.rvUser.addItemDecoration(itemDecoration)



        with(mainViewModel) {
            userData.observe(this@MainActivity) {
                if (it != null) {
                    setDataUser(it)
                }
            }
            isLoading.observe(this@MainActivity) { showLoading(it) }
        }


        with(binding) {
            searchView.setupWithSearchBar(searchBar)
            searchBar.inflateMenu(R.menu.option_menu)
            searchView.editText.setOnEditorActionListener { _, actionId, _ ->
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    val query = searchView.text.toString()
                    searchBar.setText(query)
                    searchView.hide()

                    mainViewModel.findUsers(searchView.text.toString())
                    true
                } else {
                    false
                }
            }
            binding.searchBar.setOnMenuItemClickListener { menuItem ->
                when (menuItem.itemId) {
                    R.id.favoritUser -> {
                        val intent = Intent(this@MainActivity, FavoritActivity::class.java)
                        startActivity(intent)
                        true
                    }

                    R.id.themeSetting -> {
                        val intent = Intent(this@MainActivity, DarkModeActivity::class.java)
                        startActivity(intent)
                        true
                    }

                    else -> false
                }
            }
        }


    }


    private fun setDataUser(users: List<ItemsItem>) {
        val adapter = ListUserAdapter()
        adapter.submitList(users)
        binding.rvUser.adapter = adapter

    }

    fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }

}





