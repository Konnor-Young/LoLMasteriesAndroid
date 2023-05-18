package com.example.leaguelookup

import android.app.Application

class LeagueLookupApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        DataRepository.initialize(this)
    }
}