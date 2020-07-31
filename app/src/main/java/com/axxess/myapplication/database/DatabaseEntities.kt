package com.axxess.myapplication.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class CommentEntity constructor(
    @PrimaryKey
    val id: String,
    val Comment: String = "")

