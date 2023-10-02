package com.example.tugasgithubuser.UI
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tugasgithubuser.Adapter.FavoriteAdapter
import com.example.tugasgithubuser.ViewModel.FavoriteViewModel
import com.example.tugasgithubuser.ViewModel.ViewModelFactory
import com.example.tugasgithubuser.databinding.ActivityFavoritBinding

class Favorit : AppCompatActivity() {

    private lateinit var binding: ActivityFavoritBinding
    private lateinit var favoriteViewModel: FavoriteViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoritBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.show()
        supportActionBar?.title = "Favorit"

        val layoutManager = LinearLayoutManager(this)
        binding.recyclerView.layoutManager = layoutManager

        favoriteViewModel = obtainFavoriteUserViewModel(this@Favorit)
        favoriteViewModel.getAllFavoriteUsers().observe(this) {
            val adapter = FavoriteAdapter()
            adapter.submitList(it)
            binding.recyclerView.adapter = adapter
        }
    }

    private fun obtainFavoriteUserViewModel(activity: AppCompatActivity): FavoriteViewModel {
        val factory = ViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(
            this@Favorit,
            factory
        )[FavoriteViewModel::class.java]
    }
}