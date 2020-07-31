package com.axxess.myapplication.viewmodels

import android.app.Application
import androidx.lifecycle.*
import com.axxess.myapplication.database.getDatabase
import com.axxess.myapplication.model.SearchModel
import com.axxess.myapplication.repository.SearchRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import java.io.IOException


class ImageDetailViewModel(application: Application, id: String) : AndroidViewModel(application) {

    /**
     * The data source this ViewModel will fetch results from.
     */
    private val repository = SearchRepository(getDatabase(application))

    /**
     * A article list displayed on the screen.
     */
    val commentData = repository.getComment(id)




    /**
     * This is the job for all coroutines started by this ViewModel.
     *
     * Cancelling this job will cancel all coroutines started by this ViewModel.
     */
    private val viewModelJob = SupervisorJob()

    /**
     * This is the main scope for all coroutines launched by MainViewModel.
     *
     * Since we pass viewModelJob, you can cancel all coroutines launched by uiScope by calling
     * viewModelJob.cancel()
     */
    private val viewModelScope = CoroutineScope(viewModelJob + Dispatchers.Main)


    /**
     * get data from the repository. Use a coroutine launch to run in a
     * background thread.
     */
    fun setComment(id: String, comment: String) {
        viewModelScope.launch {
            try {
                repository.insertComment(id, comment)

            } catch (networkError: IOException) {

            }
        }
    }



    /**
     * Cancel all coroutines when the ViewModel is cleared
     */
    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    /**
     * Factory for constructing with parameter
     */
    class Factory(val app: Application, val id: String) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(ImageDetailViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return ImageDetailViewModel(app, id) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }

}
