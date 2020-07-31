package com.axxess.myapplication.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [CommentEntity::class], version = DatabaseConstants.version, exportSchema = false)
abstract class CommentDatabase: RoomDatabase() {
    abstract val searchDao: SearchDao
}

private lateinit var INSTANCE: CommentDatabase

fun getDatabase(context: Context): CommentDatabase {
    synchronized(CommentDatabase::class.java) {
        if (!::INSTANCE.isInitialized) {
            INSTANCE = Room.databaseBuilder(context.applicationContext,
                CommentDatabase::class.java,
                DatabaseConstants.databaseName).build()
        }
    }
    return INSTANCE
}
