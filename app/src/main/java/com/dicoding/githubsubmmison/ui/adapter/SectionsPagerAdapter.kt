package com.dicoding.githubsubmmison.ui.adapter

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.dicoding.githubsubmmison.ui.page.fragment.FollowFragment


class SectionsPagerAdapter(activity: AppCompatActivity, username: String) :
    FragmentStateAdapter(activity) {

    private var username: String = username

    override fun getItemCount(): Int = 2
    override fun createFragment(position: Int): Fragment {
        val fragment: Fragment = FollowFragment()
        fragment.arguments = Bundle().apply {
            putString(FollowFragment.USERNAME, username)
            putInt(FollowFragment.TAB_POSITION, position + 1)

        }
        return fragment
    }

}