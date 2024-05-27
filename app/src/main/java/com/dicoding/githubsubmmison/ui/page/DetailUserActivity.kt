package com.dicoding.githubsubmmison.ui.page

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.dicoding.githubsubmmison.R
import com.dicoding.githubsubmmison.data.local.entity.FavUserEntity
import com.dicoding.githubsubmmison.data.remote.response.DetailResponse
import com.dicoding.githubsubmmison.databinding.ActivityDetailUserBinding
import com.dicoding.githubsubmmison.ui.adapter.SectionsPagerAdapter
import com.dicoding.githubsubmmison.ui.view_model_factory.FavViewModelFactory
import com.dicoding.githubsubmmison.ui.viewmodel.DetailViewModel
import com.dicoding.githubsubmmison.ui.viewmodel.FavoriteViewModel
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class DetailUserActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailUserBinding
    private val detailViewModel by viewModels<DetailViewModel>()
    private var detailResponse = DetailResponse()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()


        val favFactory = FavViewModelFactory.getInstance(this)
        val favVM: FavoriteViewModel by viewModels {
            favFactory
        }

        val usernameArgs = intent.extras?.getString(USERNAME)
        var isFavorite = false


        with(detailViewModel) {
            getUserDetail(usernameArgs!!)
            isLoading.observe(this@DetailUserActivity) { showLoading(it) }
            detailuser.observe(this@DetailUserActivity) { data ->
                setUser(data)
                tabInitialization(usernameArgs)
                detailResponse = data
            }
        }

        with(favVM) {
            with(binding) {
                checkStatusFavorite(usernameArgs ?: "").observe(this@DetailUserActivity) { fav ->
                    isFavorite = fav != null
                    updateFavButtonIcon(isFavorite)
                }
                fabFav.setOnClickListener {
                    isFavorite = !isFavorite
                    val entity = FavUserEntity(
                        username = detailResponse.login ?: "-",
                        avatarUrl = detailResponse.avatarUrl
                    )
                    if (isFavorite) {
                        insertFav(entity)
                        getToast("Success added $usernameArgs to Favorite")
                    } else {
                        deleteFav(entity)
                        getToast("Success delete $usernameArgs from Favorite")
                    }
                }
            }
        }
    }

    private fun updateFavButtonIcon(isFavorite: Boolean) {
        val iconResId = if (isFavorite) R.drawable.ic_favorite else R.drawable.ic_favorite_border
        binding.fabFav.setImageResource(iconResId)
    }

    private fun tabInitialization(username: String) {
        val sectionPagerAdapter = SectionsPagerAdapter(this, username)
        val viewPager: ViewPager2 = binding.tvPage
        viewPager.adapter = sectionPagerAdapter
        val tab: TabLayout = binding.tabLayout
        TabLayoutMediator(tab, viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()
    }

    private fun setUser(data: DetailResponse) {

        with(binding) {
            detailUsername.text = data.login
            detailNameUser.text = data.name
            Glide.with(root.context).load(data.avatarUrl).into(detailProfileUser)
            tvFollowing.text = data.following.toString()
            tvFollowers.text = data.followers.toString()
        }
    }

    private fun getToast(text: String) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progresBar.visibility = View.VISIBLE
        } else {
            binding.progresBar.visibility = View.GONE
        }
    }


    companion object {

        const val USERNAME = "username"

        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.followers,
            R.string.following
        )
    }

}
