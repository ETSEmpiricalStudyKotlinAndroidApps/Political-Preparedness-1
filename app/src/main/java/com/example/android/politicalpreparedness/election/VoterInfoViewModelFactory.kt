package com.example.android.politicalpreparedness.election

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.android.politicalpreparedness.database.ElectionDao

@Suppress("UNCHECKED_CAST")
class VoterInfoViewModelFactory(private val dataSource: ElectionDao): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return (VoterInfoViewModel(dataSource) as T)
    }

}