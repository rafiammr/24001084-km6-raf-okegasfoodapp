package com.rafi.okegasfood

import android.app.Application
import com.rafi.okegasfood.data.source.local.database.AppDatabase

class App : Application(){
    override fun onCreate() {
        super.onCreate()
        AppDatabase.getInstance(this)
    }
}