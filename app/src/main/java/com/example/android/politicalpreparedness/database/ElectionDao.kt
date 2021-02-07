package com.example.android.politicalpreparedness.database

import androidx.room.*
import com.example.android.politicalpreparedness.network.models.Election
import kotlinx.coroutines.flow.Flow

@Dao
interface ElectionDao {

    @Insert
    fun insert(election: Election)

    @Query("SELECT * FROM election_table")
    fun getAllElections(): Flow<List<Election>>

    @Query("SELECT * FROM election_table WHERE id= :electionId")
    suspend fun getElectionById(electionId: Int): Election

    @Delete
    fun deleteElection(election: Election)

    @Query("DELETE FROM election_table")
    fun deleteAllElections()

}