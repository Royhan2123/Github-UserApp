package com.example.tugasgithubuser.database
import android.app.Application
import androidx.lifecycle.LiveData
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class UserRepository(application: Application) {
    private val userDao: UserDao
    private val executorService: ExecutorService = Executors.newSingleThreadExecutor()

    init {
        val db = UserDatabase.getDatabase(application)
        userDao = db.userDao()
    }

    fun getAllFavoriteUsers(): LiveData<List<UserEntity>> = userDao.getAllFavoriteUsers()

    fun insert(favorite: UserEntity) {
        executorService.execute { userDao.insert(favorite) }
    }

    fun delete(favorite: UserEntity) {
        executorService.execute { userDao.delete(favorite) }
    }

    fun isFavorite(username: String): Boolean {
        return userDao.isFavorite(username)
    }
}