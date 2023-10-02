package com.example.tugasgithubuser.ViewModel

import android.app.Application
import androidx.lifecycle.ViewModel
import com.example.tugasgithubuser.database.UserRepository

class FavoriteViewModel(application: Application) : ViewModel() {
    private val userRepository: UserRepository = UserRepository(application)

    fun getAllFavoriteUsers() = userRepository.getAllFavoriteUsers()

    init {
        getAllFavoriteUsers()
    }
}