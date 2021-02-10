package com.example.android.politicalpreparedness.network.models

import com.squareup.moshi.Json

class Contest {
    @Json(name = "type")
    var type: String? = null

    @Json(name = "office")
    var office: String? = null

    @Json(name = "district")
    var district: District? = null

    @Json(name = "sources")
    var sources: List<Source>? = null

    @Json(name = "candidates")
    var candidates: List<Candidate>? = null
}