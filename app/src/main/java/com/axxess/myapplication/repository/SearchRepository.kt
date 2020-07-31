package com.axxess.myapplication.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.axxess.myapplication.database.CommentDatabase
import com.axxess.myapplication.database.CommentEntity
import com.axxess.myapplication.model.SearchModel
import com.axxess.myapplication.network.NetworkClient
import com.axxess.myapplication.network.asDataModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class SearchRepository(private val database: CommentDatabase) {

    var searchList: MutableLiveData<List<SearchModel>> = MutableLiveData()

    fun getComment(id: String) : LiveData<CommentEntity>{
        return database.searchDao.getComment(id)
    }

    /**
     *
     * This function uses the IO dispatcher to ensure the database insert database operation
     * happens on the IO dispatcher. By switching to the IO dispatcher using `withContext` this
     * function is now safe to call from any thread including the Main thread.
     *
     */
    suspend fun getSearchResult(pageNumber : String, query : String ) {
        withContext(Dispatchers.IO) {
            val results  =
                NetworkClient.searchService.getSearchResults(pageNumber, query).await()
            withContext(Dispatchers.Main){searchList.value = results.data.asDataModel()}
        }
    }



    suspend fun insertComment(id : String, comment : String ) {
        withContext(Dispatchers.IO) {
            database.searchDao.insert(CommentEntity(id, comment))
        }
    }



}