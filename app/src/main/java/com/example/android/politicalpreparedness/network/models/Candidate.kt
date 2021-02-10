package com.example.android.politicalpreparedness.network.models

import com.squareup.moshi.Json


class Candidate {
    @Json(name = "name")
    var name: String? = null

    @Json(name = "party")
    var party: String? = null

    @Json(name = "email")
    var email: String? = null

    @Json(name = "candidateUrl")
    var candidateUrl: String? = null

    @Json(name = "phone")
    var phone: String? = null
}