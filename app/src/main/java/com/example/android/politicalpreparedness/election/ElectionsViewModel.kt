package com.example.android.politicalpreparedness.election

import androidx.lifecycle.*
import com.example.android.politicalpreparedness.database.ElectionDao
import com.example.android.politicalpreparedness.network.CivicsApi
import com.example.android.politicalpreparedness.network.CivicsHttpClient
import com.example.android.politicalpreparedness.network.models.Division
import com.example.android.politicalpreparedness.network.models.Election
import com.example.android.politicalpreparedness.network.models.ElectionResponse
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import timber.log.Timber
import java.lang.Exception
import java.util.*

class ElectionsViewModel(private val dataSource: ElectionDao) : ViewModel() {

    private var _upcomingElections = MutableLiveData<List<Election>>()
    val upcomingElections: LiveData<List<Election>>
        get() = _upcomingElections

    val savedElections = dataSource.getSavedElections()

    private val _dataLoading = MutableLiveData<Boolean>()
    val dataLoading: LiveData<Boolean>
        get() = _dataLoading

    private val _navigate = MutableLiveData<Election>()
    val navigate: LiveData<Election>
        get() = _navigate

    private fun retrieveElections() {
        _dataLoading.value = true

        try {
            viewModelScope.launch {
                val response: ElectionResponse = CivicsApi.retrofitService.getAllElections()
                Timber.d("{$response}")
                _upcomingElections.value = response.elections

                if (_upcomingElections.value.isNullOrEmpty()) {
                    _upcomingElections.value = emptyList()
                }

                _dataLoading.value = false
            }

        } catch (e: Exception) {
            Timber.d(e.localizedMessage)
        }
    }

    init {
        retrieveElections()
    }

    fun navigate(election: Election) {
        _navigate.value = election
    }

    fun doneNavigating() {
        _navigate.value = null
    }
}