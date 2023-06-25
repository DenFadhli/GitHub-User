package com.submission.githubuser.ui.detail

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.submission.githubuser.ItemsItem
import com.submission.githubuser.data.local.entity.UserEntity
import com.submission.githubuser.data.local.room.UserDao
import com.submission.githubuser.data.local.room.UserRoomDB
import com.submission.githubuser.data.remote.response.DetailUserResponse
import com.submission.githubuser.data.remote.retrofit.ApiConfig
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailViewModel(application: Application): AndroidViewModel(application) {

    private val _detailUser = MutableLiveData<DetailUserResponse>()
    val detailUser: LiveData<DetailUserResponse> = _detailUser

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _followers = MutableLiveData<List<ItemsItem>>()
    val followers: LiveData<List<ItemsItem>> = _followers

    private val _followings = MutableLiveData<List<ItemsItem>>()
    val followings: LiveData<List<ItemsItem>> = _followings

    private var userDb: UserRoomDB?
    private var usersDao: UserDao?

    init {
        getUserDetails()
        userDb = UserRoomDB.getDatabase(application)
        usersDao = userDb?.userDao()
    }

    fun getUserDetails(query: String = "") {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getDetailUser(query)
        client.enqueue(object : Callback<DetailUserResponse> {
            override fun onResponse(
                call: Call<DetailUserResponse>,
                response: Response<DetailUserResponse>,
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _detailUser.value = response.body()
                } else {
                    Log.d(TAGS, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<DetailUserResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAGS, "onFailure = ${t.message.toString()}")
            }
        })
    }

    fun getUserFollowers(query: String = "") {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getFollowersUser(query)
        client.enqueue(object : Callback<List<ItemsItem>> {
            override fun onResponse(
                call: Call<List<ItemsItem>>,
                response: Response<List<ItemsItem>>,
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _followers.value = response.body()
                } else {
                    Log.d(TAGS, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<List<ItemsItem>>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAGS, "onFailure : ${t.message.toString()}")
            }
        })
    }

    fun getUserFollowings(query: String = "") {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getFollowingsUser(query)
        client.enqueue(object : Callback<List<ItemsItem>> {
            override fun onResponse(
                call: Call<List<ItemsItem>>,
                response: Response<List<ItemsItem>>,
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _followings.value = response.body()
                } else {
                    Log.d(TAGS, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<List<ItemsItem>>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAGS, "onFailure : ${t.message.toString()}")
            }
        })
    }

    fun insertFavorite(username: String, id: Int, avatarUrl: String) {
        CoroutineScope(Dispatchers.IO).launch {
            val item = UserEntity(
                username,
                id,
                avatarUrl
            )
            usersDao?.insertUsers(item)
        }
    }

    suspend fun checkUser(id: Int) = usersDao?.isUsersFavorited(id)

    fun deleteFavorite(id: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            usersDao?.deleteUsers(id)
        }
    }

    companion object {
        const val TAGS = "DetailViewModel"
    }
}