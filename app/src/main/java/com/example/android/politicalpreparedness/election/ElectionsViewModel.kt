package com.example.android.politicalpreparedness.election

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.android.politicalpreparedness.database.ElectionDao
import com.example.android.politicalpreparedness.network.CivicsApi
import com.example.android.politicalpreparedness.network.CivicsHttpClient
import com.example.android.politicalpreparedness.network.models.Election
import com.example.android.politicalpreparedness.network.models.ElectionResponse
import kotlinx.coroutines.launch
import timber.log.Timber
import java.lang.Exception

class ElectionsViewModel(private val dataSource: ElectionDao): ViewModel() {

    private var _upcomingElections = MutableLiveData<List<Election>>()
    val upcomingElections: LiveData<List<Election>>
        get() = _upcomingElections

    private var _savedElections = MutableLiveData<List<Election>>()
    val savedElections: LiveData<List<Election>>
        get() = _savedElections

    private val _dataLoading = MutableLiveData<Boolean>()
    val dataLoading: LiveData<Boolean>
        get() = _dataLoading

    //TODO: Create val and functions to populate live data for upcoming elections from the API and saved elections from local database

    private fun retrieveElections() {
        _dataLoading.value = true

        try {
            viewModelScope.launch {
                val response: ElectionResponse = CivicsApi.retrofitService.getAllElections(CivicsHttpClient.API_KEY)
                Timber.d("{$response}")
                _upcomingElections.value = response.elections

                if(_upcomingElections.value.isNullOrEmpty()) {
                    _upcomingElections.value = emptyList()
                }

                _dataLoading.value = false
            }

        } catch(e: Exception) {
            Timber.d(e.localizedMessage)
        }
    }

    init {
        _dataLoading.value = false
        retrieveElections()
    }

    //TODO: Create functions to navigate to saved or upcoming election voter info

}