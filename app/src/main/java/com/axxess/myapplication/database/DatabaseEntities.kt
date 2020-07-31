package com.axxess.myapplication.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class CommentEntity constructor(
    @PrimaryKey
    var id: String,
    var Comment: String)

