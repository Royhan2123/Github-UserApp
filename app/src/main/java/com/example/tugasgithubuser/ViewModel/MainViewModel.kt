package com.example.tugasgithubuser.ViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.tugasgithubuser.API.ApiConfig
import com.example.tugasgithubuser.API.SearchResponse
import com.example.tugasgithubuser.API.UserItem
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel : ViewModel() {

    private val _listUser = MutableLiveData<List<UserItem>>()
    val listSearch: LiveData<List<UserItem>> = _listUser

    private val _loadingScreen = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = _loadingScreen

    init {
        getListSearch("A")
    }

    fun getListSearch(query: String) {
        _loadingScreen.value = true
        val client = ApiConfig.getApiServices().getUsers(query)
        client.enqueue(object : Callback<SearchResponse> {
            override fun onResponse(
                call: Call<SearchResponse>,
                response: Response<SearchResponse>
            ) {
                _loadingScreen.value = false
                if (response.isSuccessful) {
                    _listUser.value = response.body()?.items
                }
            }

            override fun onFailure(call: Call<SearchResponse>, t: Throwable) {
                _loadingScreen.value = false
            }
        })
    }
}