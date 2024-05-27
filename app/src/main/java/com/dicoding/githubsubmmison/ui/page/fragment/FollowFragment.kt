package com.dicoding.githubsubmmison.ui.page.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import androidx.fragment.app.Fragment
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.githubsubmmison.databinding.FragmentFollowBinding
import com.dicoding.githubsubmmison.ui.adapter.ListUserAdapter
import com.dicoding.githubsubmmison.ui.viewmodel.FollowerViewModel
import com.dicoding.githubsubmmison.ui.viewmodel.FollowingViewModel


class FollowFragment : Fragment() {
    private var position: Int? = null
    private var username: String? = null
    private var _binding: FragmentFollowBinding? = null
    private val binding get() = _binding
    private val followingVM by viewModels<FollowingViewModel>()
    private val followerVM by viewModels<FollowerViewModel>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFollowBinding.inflate(inflater, container, false)
        return binding?.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        arguments?.let {
            position = it.getInt(TAB_POSITION)
            username = it.getString(USERNAME)
        }

        val adapter = ListUserAdapter()

        setRV()

        if (position == 1) {
            followingData(adapter)
        } else {
            followerData(adapter)
        }


    }

    private fun setRV() {
        with(binding?.rvFollowing!!) {
            val layoutManager = LinearLayoutManager(requireActivity())
            this.layoutManager = layoutManager
            val itemDecor = DividerItemDecoration(requireActivity(), layoutManager.orientation)
            addItemDecoration(itemDecor)
        }
    }

    private fun followingData(adapter: ListUserAdapter) {
        with(followingVM) {
            with(binding!!) {
                if (followingData.value == null) {
                    getFollowing(username!!)
                }
                isLoading.observe(viewLifecycleOwner) { showLoading(it) }
                followingData.observe(viewLifecycleOwner) {
                    adapter.submitList(it)
                    rvFollowing.adapter = adapter
                }
            }
        }

    }

    private fun followerData(adapter: ListUserAdapter) {
        with(followerVM) {
            with(binding!!) {
                if (followerData.value == null) {
                    getFollower(username!!)
                }
                isloading.observe(viewLifecycleOwner) { showLoading(it) }
                followerData.observe(viewLifecycleOwner) {
                    adapter.submitList(it)
                    rvFollowing.adapter = adapter
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding?.progressBar?.visibility = View.VISIBLE
        } else {
            binding?.progressBar?.visibility = View.GONE
        }
    }

    companion object {
        const val TAB_POSITION = "tab_position"
        const val USERNAME = "username"
    }
}