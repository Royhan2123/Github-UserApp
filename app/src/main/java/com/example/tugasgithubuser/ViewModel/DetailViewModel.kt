package com.example.tugasgithubuser.ViewModel
import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tugasgithubuser.API.ApiConfig
import com.example.tugasgithubuser.API.DetailuserResponse
import com.example.tugasgithubuser.API.UserItem
import com.example.tugasgithubuser.database.UserEntity
import com.example.tugasgithubuser.database.UserRepository
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailViewModel(application: Application) : ViewModel() {
    private val userRepository: UserRepository = UserRepository(application)

    private val _userDetail = MutableLiveData<UserEntity>()
    val userDetail: LiveData<UserEntity> = _userDetail

    private val _loadingScreen = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = _loadingScreen

    private val _userFollower = MutableLiveData<List<UserItem>>()
    val userFollower: LiveData<List<UserItem>> = _userFollower

    private val _userFollowing = MutableLiveData<List<UserItem>>()
    val userFollowing: LiveData<List<UserItem>> = _userFollowing

    private var isLoaded = false
    private var isFollowerloaded = false
    private var isFollowingLoaded = false

    fun insertUserFavorite(userEntity: UserEntity) {
        userRepository.insert(userEntity)
    }

    fun deleteUserFavorite(userEntity: UserEntity) {
        userRepository.delete(userEntity)
    }

    fun getDetailUser(username: String) {
        if (!isLoaded) {
            _loadingScreen.value = true
            val client = ApiConfig.getApiServices().getDetailUser(username)
            client.enqueue(object : Callback<DetailuserResponse> {
                override fun onResponse(
                    call: Call<DetailuserResponse>,
                    response: Response<DetailuserResponse>
                ) {
                    _loadingScreen.value = false
                    if (response.isSuccessful) {
                        val resBody = response.body()
                        if (resBody != null) {
                            viewModelScope.launch {
                                val isFavoriteUser =
                                    userRepository.isFavorite(resBody.login.toString())
                                val user = UserEntity(
                                    username = resBody.login.toString(),
                                    name = resBody.name,
                                    avatarUrl = resBody.avatarUrl.toString(),
                                    followersCount = resBody.followers.toString(),
                                    followingCount = resBody.following.toString(),
                                    isFavorite = isFavoriteUser
                                )
                                _userDetail.postValue(user)
                            }
                        }
                    }
                }

                override fun onFailure(call: Call<DetailuserResponse>, t: Throwable) {
                    _loadingScreen.value = false
                }
            })
            isLoaded = true
        }
    }

    fun getFollower(username: String) {
        if (!isFollowerloaded) {
            _loadingScreen.value = true
            val client = ApiConfig.getApiServices().getFollowers(username)
            client.enqueue(object : Callback<List<UserItem>> {
                override fun onResponse(
                    call: Call<List<UserItem>>,
                    response: Response<List<UserItem>>
                ) {
                    _loadingScreen.value = false
                    if (response.isSuccessful) {
                        _userFollower.postValue(response.body())
                    }
                }

                override fun onFailure(call: Call<List<UserItem>>, t: Throwable) {
                    _loadingScreen.value = false
                }
            })
            isFollowerloaded = true
        }
    }

    fun getFollowing(username: String) {
        if (!isFollowingLoaded) {
            _loadingScreen.value = true
            val client = ApiConfig.getApiServices().getFollowing(username)
            client.enqueue(object : Callback<List<UserItem>> {
                override fun onResponse(
                    call: Call<List<UserItem>>,
                    response: Response<List<UserItem>>
                ) {
                    _loadingScreen.value = false
                    if (response.isSuccessful) {
                        _userFollowing.postValue(response.body())
                    }
                }

                override fun onFailure(call: Call<List<UserItem>>, t: Throwable) {
                    _loadingScreen.value = false
                }
            })
            isFollowingLoaded = true
        }
    }
}