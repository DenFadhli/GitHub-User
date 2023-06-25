package com.submission.githubuser.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "favorite_user")
class UserEntity (

    @field:ColumnInfo(name = "login")
    val login: String,

    @field:ColumnInfo(name = "id")
    @field:PrimaryKey
    val id: Int,

    @field:ColumnInfo(name = "avatar_url")
    val avatarUrl: String,

): Serializable