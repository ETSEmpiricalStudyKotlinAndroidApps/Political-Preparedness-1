package com.example.android.politicalpreparedness.election

import androidx.lifecycle.*
import com.example.android.politicalpreparedness.database.ElectionDao
import com.example.android.politicalpreparedness.network.CivicsApi
import com.example.android.politicalpreparedness.network.CivicsHttpClient
import com.example.android.politicalpreparedness.network.models.Election
import com.example.android.politicalpreparedness.network.models.ElectionResponse
import kotlinx.coroutines.flow.map
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


    private val _dataLoadingSaved = MutableLiveData<Boolean>()
    val dataLoadingSaved: LiveData<Boolean>
        get() = _dataLoadingSaved

    private val _navigate = MutableLiveData<Election>()
    val navigate: LiveData<Election>
        get() = _navigate

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

    private fun retrieveSavedElections() {
        _dataLoadingSaved.value = true

        val savedList = dataSource.getSavedElections().asLiveData()

        if(!savedList.value.isNullOrEmpty()) {
            _savedElections.value = savedList.value
            _dataLoadingSaved.value = false
        } else {
            _savedElections.value = emptyList()
            _dataLoadingSaved.value = false
        }
    }

    init {
        _dataLoading.value = false
        _dataLoadingSaved.value = false
        retrieveElections()
        retrieveSavedElections()
    }

    fun navigate(election: Election) {
        _navigate.value = election
    }

    fun doneNavigating() {
        _navigate.value = null
    }
}