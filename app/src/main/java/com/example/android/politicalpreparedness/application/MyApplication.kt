package com.example.android.politicalpreparedness.application

import android.app.Application
import com.example.android.politicalpreparedness.database.ElectionDao
import com.example.android.politicalpreparedness.database.ElectionDatabase
import timber.log.Timber

class MyApplication: Application() {

    private lateinit var dataSource: ElectionDao

    override fun onCreate() {
        super.onCreate()

        dataSource = ElectionDatabase.getInstance(applicationContext).electionDao

        Timber.plant(Timber.DebugTree())
    }

    fun getDataSource(): ElectionDao {
        return dataSource
    }


}