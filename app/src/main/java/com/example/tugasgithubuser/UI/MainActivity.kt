package com.example.tugasgithubuser.UI

import com.example.tugasgithubuser.ViewModel.ThemeViewModelFactory
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tugasgithubuser.API.UserItem
import com.example.tugasgithubuser.Adapter.UserAdapter
import com.example.tugasgithubuser.Prefrences.SettingPrefrences
import com.example.tugasgithubuser.Prefrences.dataStore
import com.example.tugasgithubuser.ViewModel.ThemeViewModel
import com.example.tugasgithubuser.R
import com.example.tugasgithubuser.ViewModel.MainViewModel
import com.example.tugasgithubuser.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val mainViewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        val pref = SettingPrefrences.getInstance(application.dataStore)
        val themeViewModel =
            ViewModelProvider(this, ThemeViewModelFactory(pref))[ThemeViewModel::class.java]

        themeViewModel.getThemeSettings().observe(this) { isDarkModeActive: Boolean ->
            if (isDarkModeActive) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }


        binding.searchBar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.menuFavorit -> {
                    val intent = Intent(this, Favorit::class.java)
                    startActivity(intent)
                    true
                }
                R.id.menuSetting -> {
                    val intent = Intent(this, SwitchActivity::class.java)
                    startActivity(intent)
                    true
                }
                else -> false
            }
        }

        with(binding) {
            searchView.setupWithSearchBar(searchBar)
            searchView
                .editText
                .setOnEditorActionListener { textView, actionId, event ->
                    searchBar.text = searchView.text
                    searchView.hide()
                    mainViewModel.getListSearch(searchView.text.toString())
                    mainViewModel.listSearch.observe(this@MainActivity) { items ->
                        if (items.isNullOrEmpty()) {
                            showNotFound(true)
                        } else {
                            showNotFound(false)
                        }
                    }
                    false
                }
        }


        mainViewModel.loading.observe(this) { isLoading ->
            showLoading(isLoading)
        }

        val layoutManager = LinearLayoutManager(this)
        binding.recyclerView.layoutManager = layoutManager


        mainViewModel.listSearch.observe(this) { items ->
            if (items != null) {
                setUserData(items)
            }
        }
    }

    private fun setUserData(datauser: List<UserItem>) {
        val adapter = UserAdapter(datauser)
        adapter.submitList(datauser)
        binding.recyclerView.adapter = adapter
    }

    private fun showLoading(isLoading: Boolean) {
        binding.apply {
            if (isLoading) {
                progressBar.visibility = View.VISIBLE
            } else {
                progressBar.visibility = View.GONE
            }
        }
    }

    private fun showNotFound(isDataNotFound: Boolean) {
        binding.apply {
            if (isDataNotFound) {
                recyclerView.visibility = View.GONE
            } else {
                recyclerView.visibility = View.VISIBLE
            }
        }
    }
}
