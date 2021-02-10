package com.example.android.politicalpreparedness.election

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.android.politicalpreparedness.BuildConfig
import com.example.android.politicalpreparedness.database.ElectionDao
import com.example.android.politicalpreparedness.network.CivicsApi
import com.example.android.politicalpreparedness.network.models.*
import kotlinx.coroutines.launch
import timber.log.Timber
import java.lang.Exception
import java.text.DateFormat

class VoterInfoViewModel(private val dataSource: ElectionDao) : ViewModel() {

    //TODO: Add live data to hold voter info
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
        get() = _pollingLocationUrl

    //TODO: Add var and methods to populate voter info

    //TODO: Add var and methods to support loading URLs

    //TODO: Add var and methods to save and remove elections to local database
    //TODO: cont'd -- Populate initial state of save button to reflect proper action based on election saved status

    /**
     * Hint: The saved state can be accomplished in multiple ways. It is directly related to how elections are saved/removed from the database.
     */

    fun retrieveVoterInfoResponse(electionId: Int) {
        try {
               viewModelScope.launch {
                val voterInfo: VoterInfoResponse = CivicsApi.retrofitService.getVoterInfo(BuildConfig.ApiKey,"1263 Pacific Ave. Kansas City, KS", electionId)
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
            }
        }
}