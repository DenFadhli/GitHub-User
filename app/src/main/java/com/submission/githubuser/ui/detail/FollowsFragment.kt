package com.submission.githubuser.ui.detail

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.submission.githubuser.ItemsItem
import com.submission.githubuser.databinding.FragmentFollowsBinding
import com.submission.githubuser.ui.main.MainUserAdapter

class FollowsFragment : Fragment() {
    private lateinit var binding: FragmentFollowsBinding

    private lateinit var detailUserViewModel: DetailViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentFollowsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var position = 0
        var username = arguments?.getString(ARG_USERNAME)

        Log.d("arguments: position", position.toString())

        detailUserViewModel = ViewModelProvider(requireActivity(), ViewModelProvider.NewInstanceFactory())[DetailViewModel::class.java]

        arguments?.let {
            position = it.getInt(ARG_POSITION)
            username = it.getString(ARG_USERNAME)
        }

        if (position == 1) {
            showLoadingUser(true)
            username?.let { detailUserViewModel.getUserFollowers(it) }
            detailUserViewModel.followers.observe(viewLifecycleOwner) {
                setFollowData(it)
                showLoadingUser(false)
            }
        } else {
            showLoadingUser(true)
            username?.let { detailUserViewModel.getUserFollowings(it) }
            detailUserViewModel.followings.observe(viewLifecycleOwner) {
                setFollowData(it)
                showLoadingUser(false)
            }
        }
    }

    private fun showLoadingUser(isLoading: Boolean) {
        binding.progressBarFollows.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun setFollowData(listFollow: List<ItemsItem>) {
        binding.apply {
            binding.recycleViewFollows.layoutManager = LinearLayoutManager(requireActivity())
            val adapter = MainUserAdapter(listFollow)
            binding.recycleViewFollows.adapter = adapter
        }
    }

    companion object {
        const val ARG_POSITION = "position"
        const val ARG_USERNAME = ""
    }
}