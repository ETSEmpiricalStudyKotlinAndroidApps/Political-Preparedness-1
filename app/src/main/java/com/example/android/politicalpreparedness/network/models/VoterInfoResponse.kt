package com.example.android.politicalpreparedness.network.models

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
class VoterInfoResponse (
    val election: Election,
    val pollingLocations: List<PollingLocation>? = null,
    val contests: List<Contest>? = null,
    val state: List<State>? = null,
    val electionElectionOfficials: List<ElectionOfficial>? = null
)