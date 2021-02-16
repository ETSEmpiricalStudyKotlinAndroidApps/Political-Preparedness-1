package com.example.android.politicalpreparedness.election

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.android.politicalpreparedness.database.ElectionDao
import com.example.android.politicalpreparedness.network.CivicsApi
import com.example.android.politicalpreparedness.network.models.*
import kotlinx.coroutines.launch
import timber.log.Timber
import java.lang.Exception
import java.text.DateFormat

class VoterInfoViewModel(private val dataSource: ElectionDao) : ViewModel() {

    private val _voterInfo = MutableLiveData<VoterInfoResponse>()
    val voterInfo: LiveData<VoterInfoResponse>
        get() = _voterInfo


    private val _actionTitle = MutableLiveData<String>()
    val actionTitle: LiveData<String>
        get() = _actionTitle

    private val _electionDate = MutableLiveData<String>()
    val electionDate: LiveData<String>
        get() = _electionDate

   private val _pollingLocationUrl = MutableLiveData<String>()
    val pollingLocation: LiveData<String>
        get() = _pollingLocationUrl

    private val _ballotInfoUrl = MutableLiveData<String>()
    val ballotInfoUrl: LiveData<String>
        get() = _ballotInfoUrl

    private val _correspondenceAddress = MutableLiveData<String>()
    val correspondenceAddress: LiveData<String>
        get() = _correspondenceAddress

    private val _entryExists = MutableLiveData<Boolean>()
    val entryExists: LiveData<Boolean>
        get() = _entryExists

    fun doesEntryExist(electionId: Int) {
        viewModelScope.launch {
            _entryExists.value = dataSource.entryExists(electionId)
        }
    }

    /**
     * Hint: The saved state can be accomplished in multiple ways. It is directly related to how elections are saved/removed from the database.
     */

    fun retrieveVoterInfoResponse(electionId: Int) {
        try {
               viewModelScope.launch {
                val voterInfo: VoterInfoResponse = CivicsApi.retrofitService.getVoterInfo("1263 Pacific Ave. Kansas City, KS", electionId)
                   _voterInfo.value = voterInfo
                   assignData(voterInfo)
            }
        } catch(e: Exception) {
            Timber.d(e.localizedMessage)
        }
    }

    private fun assignData(voterInfo: VoterInfoResponse?) {
        val dateFormat = DateFormat.getDateInstance()

            if(voterInfo != null) {
                _actionTitle.value  = voterInfo.election.name
                _electionDate.value = dateFormat.format(voterInfo.election.electionDay).toString()
                _pollingLocationUrl.value = voterInfo.state?.get(0)?.electionAdministrationBody?.votingLocationFinderUrl
                _ballotInfoUrl.value = voterInfo.state?.get(0)?.electionAdministrationBody?.ballotInfoUrl
                _correspondenceAddress.value = voterInfo.state?.get(0)?.electionAdministrationBody?.correspondenceAddress?.toFormattedString()
            }
        }

    fun saveElection() {
        viewModelScope.launch {
            voterInfo.value?.let { dataSource.insert(it.election) }
        }
    }

    fun removeElection() {
        viewModelScope.launch {
            voterInfo.value?.let { dataSource.deleteElection(it.election.id) }
        }
    }
}