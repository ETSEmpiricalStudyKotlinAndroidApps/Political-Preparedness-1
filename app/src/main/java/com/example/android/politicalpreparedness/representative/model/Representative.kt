package com.example.android.politicalpreparedness.representative.model

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import com.example.android.politicalpreparedness.network.models.Office
import com.example.android.politicalpreparedness.network.models.Official

data class Representative (
        @Bindable
        var official: Official,

        @Bindable
        var office: Office
): BaseObservable()