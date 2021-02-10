package com.example.android.politicalpreparedness.network.models

import com.squareup.moshi.Json

class Source {
    @Json(name = "name")
    var name: String? = null

    @Json(name = "official")
    var official: Boolean? = null
}