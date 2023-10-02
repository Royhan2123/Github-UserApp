package com.example.tugasgithubuser.UI

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tugasgithubuser.Adapter.FollowerAdapter
import com.example.tugasgithubuser.ViewModel.DetailViewModel
import com.example.tugasgithubuser.ViewModel.ViewModelFactory
import com.example.tugasgithubuser.databinding.FragmentFollowerBinding

class FollowerFragment : Fragment() {
    var position = 0
    var username: String = ""

    private lateinit var binding: FragmentFollowerBinding
    private lateinit var detailViewModel: DetailViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFollowerBinding.inflate(inflater, container, false)
        return binding.root
    }
    companion object {
        const val ARG_USERNAME = "0"
        const val ARG_POSITION = "Royhan"
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        detailViewModel = obtainDetailViewModel(requireActivity())

        arguments?.let {
            position = it.getInt(ARG_POSITION)
            username = it.getString(ARG_USERNAME) ?: "Royhan"
        }

        detailViewModel.getFollowing(username)
        detailViewModel.getFollower(username)

        val layoutManager = LinearLayoutManager(requireActivity())
        binding.recyclerView.layoutManager = layoutManager

        detailViewModel.loading.observe(viewLifecycleOwner) {
            showLoading(it)
        }
        if (position == 1) {
            detailViewModel.userFollower.observe(viewLifecycleOwner) {
                val adapter = FollowerAdapter()
                adapter.submitList(it)
                binding.recyclerView.adapter = adapter
            }
        } else {
            detailViewModel.userFollowing.observe(viewLifecycleOwner) {
                val adapter = FollowerAdapter()
                adapter.submitList(it)
                binding.recyclerView.adapter = adapter
            }
        }
    }

    private fun obtainDetailViewModel(activity: FragmentActivity): DetailViewModel {
        val factory = ViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(requireActivity(), factory)[DetailViewModel::class.java]
    }

    private fun showLoading(isLoading: Boolean) {
        binding.apply {
            if (isLoading) {
                progressBarFragment.visibility = View.VISIBLE
            } else {
                progressBarFragment.visibility = View.GONE
            }
        }
    }
}