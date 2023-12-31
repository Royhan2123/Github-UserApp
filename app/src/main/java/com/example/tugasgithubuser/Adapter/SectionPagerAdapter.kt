package com.example.tugasgithubuser.Adapter

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.tugasgithubuser.UI.FollowerFragment

class SectionPagerAdapter(activity:AppCompatActivity,private val username : String) : FragmentStateAdapter(activity) {
    override fun getItemCount()= 2

    override fun createFragment(position: Int): Fragment {
    val fragment = FollowerFragment()

        fragment.arguments = Bundle().apply {
            putInt(FollowerFragment.ARG_POSITION,position + 1)
            putString(FollowerFragment.ARG_USERNAME,username)
        }
        return fragment
    }
}