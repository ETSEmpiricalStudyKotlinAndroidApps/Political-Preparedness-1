package com.example.android.politicalpreparedness.application

import android.app.Application
import com.example.android.politicalpreparedness.database.ElectionDatabase
import timber.log.Timber

class MyApplication: Application() {

    override fun onCreate() {
        super.onCreate()

        val dataSource = ElectionDatabase.getInstance(applicationContext).electionDao

        Timber.plant(Timber.DebugTree())
    }
}