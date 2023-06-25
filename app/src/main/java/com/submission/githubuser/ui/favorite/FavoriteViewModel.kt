package com.submission.githubuser.ui.favorite

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.submission.githubuser.data.local.entity.UserEntity
import com.submission.githubuser.data.local.room.UserDao
import com.submission.githubuser.data.local.room.UserRoomDB

class FavoriteViewModel(application: Application): AndroidViewModel(application) {

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private var userDb: UserRoomDB?
    private var usersDao: UserDao?

    init {
        userDb = UserRoomDB.getDatabase(application)
        usersDao = userDb?.userDao()
    }

    fun getFavoriteUser(): LiveData<List<UserEntity>>? {
        return usersDao?.getFavoritedUsers()
    }
}