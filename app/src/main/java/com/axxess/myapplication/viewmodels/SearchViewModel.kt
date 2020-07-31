package com.axxess.myapplication.viewmodels

import android.app.Application
import androidx.lifecycle.*
import com.axxess.myapplication.database.getDatabase
import com.axxess.myapplication.repository.SearchRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import java.io.IOException


class SearchViewModel(application: Application) : AndroidViewModel(application) {

    /**
     * The data source this ViewModel will fetch results from.
     */
    private val repository = SearchRepository(getDatabase(application))

    /**
     * A list displayed on the screen.
     */
    val searchList = repository.searchList

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


    private var _eventNetworkError = MutableLiveData<Boolean>(false)

    val eventNetworkError: LiveData<Boolean>
        get() = _eventNetworkError

    private var _isShowProgress = MutableLiveData<Boolean>(false)

    val isShowProgress: LiveData<Boolean>
        get() = _isShowProgress


    private var _isLoading = MutableLiveData<Boolean>(false)

    val isLoading: LiveData<Boolean>
        get() = _isLoading


    /**
     * get data from the repository. Use a coroutine launch to run in a
     * background thread.
     */
    fun getSearchData(pageNumber: String, searchText: String) {
        viewModelScope.launch {
            try {
                repository.getSearchResult(pageNumber, searchText)
                _eventNetworkError.value = false
                _isShowProgress.value = true
                _isLoading.value

            } catch (networkError: IOException) {
                // Show a Toast error message and hide the progress bar.
                if(searchList.value.isNullOrEmpty())
                    _eventNetworkError.value = true
            }
        }
    }

    /**
     * Resets the network error flag.
     */
    fun hideProgress() {
        _isShowProgress.value = false
        _isLoading.value = false
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
    class Factory(val app: Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(SearchViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return SearchViewModel(app) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }

}