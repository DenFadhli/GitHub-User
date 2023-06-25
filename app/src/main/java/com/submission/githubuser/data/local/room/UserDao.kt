package com.submission.githubuser.data.local.room

import androidx.lifecycle.LiveData
import androidx.room.*
import com.submission.githubuser.data.local.entity.UserEntity

@Dao
interface UserDao {

    @Query("SELECT * FROM favorite_user")
    fun getFavoritedUsers(): LiveData<List<UserEntity>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertUsers(users: UserEntity)

    @Query("DELETE FROM favorite_user WHERE favorite_user.id = :id")
    suspend fun deleteUsers(id: Int): Int

    @Query("SELECT count(*) FROM favorite_user WHERE favorite_user.id = :id")
    suspend fun isUsersFavorited(id: Int): Int

}