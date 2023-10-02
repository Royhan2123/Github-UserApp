package com.example.tugasgithubuser.UI

import com.example.tugasgithubuser.ViewModel.ThemeViewModelFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.CompoundButton
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.ViewModelProvider
import com.example.tugasgithubuser.Prefrences.SettingPrefrences
import com.example.tugasgithubuser.ViewModel.ThemeViewModel
import com.example.tugasgithubuser.Prefrences.dataStore
import com.example.tugasgithubuser.databinding.ActivityBackgroundBinding

class SwitchActivity : AppCompatActivity() {
    private lateinit var binding: ActivityBackgroundBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBackgroundBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.show()
        supportActionBar?.title = "Switch"

        val switchTheme = binding.switchTheme

        val pref = SettingPrefrences.getInstance(application.dataStore)
        val themeViewModel =
            ViewModelProvider(this, ThemeViewModelFactory(pref))[ThemeViewModel::class.java]

        themeViewModel.getThemeSettings().observe(this) { isDarkModeActive: Boolean ->
            if (isDarkModeActive) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                switchTheme.isChecked = true
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                switchTheme.isChecked = false
            }
        }

        switchTheme.setOnCheckedChangeListener { _: CompoundButton?, isChecked: Boolean ->
            themeViewModel.saveThemeSetting(isChecked)
        }
    }
}