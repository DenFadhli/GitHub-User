package com.submission.githubuser.data.local.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.submission.githubuser.data.local.entity.UserEntity

@Database(entities = [UserEntity::class], version = 1, exportSchema = false)
abstract class UserRoomDB : RoomDatabase() {
    abstract fun userDao(): UserDao

    companion object {
        @Volatile
        var INSTANCE: UserRoomDB? = null

        @JvmStatic
        fun getDatabase(context: Context): UserRoomDB? {
            if (INSTANCE == null) {
                synchronized(UserRoomDB::class.java) {
                    INSTANCE = Room.databaseBuilder(context.applicationContext,
                        UserRoomDB::class.java, "fav_database.db")
                        .fallbackToDestructiveMigration()
                        .build()
                }
            }
            return INSTANCE
        }
    }
}