package com.axxess.myapplication.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface SearchDao {
    @Query("select * from commententity WHERE id = :id")
    fun getComment(id: String): LiveData<CommentEntity>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert( commentEntity: CommentEntity)
}