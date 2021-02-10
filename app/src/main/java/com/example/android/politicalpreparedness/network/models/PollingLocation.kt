package com.example.android.politicalpreparedness.network.models

import com.squareup.moshi.Json

class PollingLocation {
    @Json(name = "address")
    var address: Address? = null

    @Json(name = "pollingHours")
    var pollingHours: String? = null
}