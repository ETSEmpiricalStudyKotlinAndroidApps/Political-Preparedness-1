package com.example.android.politicalpreparedness.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.android.politicalpreparedness.network.models.Election
import kotlinx.coroutines.flow.Flow

@Dao
interface ElectionDao {

    @Insert
    suspend fun insert(election: Election)

    @Query("SELECT * FROM election_table")
    fun getSavedElections(): LiveData<List<Election>>

    @Query("SELECT * FROM election_table WHERE id= :electionId")
    suspend fun getElectionById(electionId: Int): Election

    @Query("DELETE FROM election_table WHERE id= :electionId")
    suspend fun deleteElection(electionId: Int)

    @Query("DELETE FROM election_table")
    fun deleteAllElections()

    @Query("SELECT EXISTS(SELECT * FROM election_table WHERE id= :electionId)")
    suspend fun entryExists(electionId: Int): Boolean

}