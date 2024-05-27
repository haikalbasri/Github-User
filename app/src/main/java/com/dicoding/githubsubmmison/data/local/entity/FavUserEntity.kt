package com.dicoding.githubsubmmison.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class FavUserEntity(
    @PrimaryKey(autoGenerate = false)
    var username: String,
    var avatarUrl: String? = null,
)