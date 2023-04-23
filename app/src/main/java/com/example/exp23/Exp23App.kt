package com.example.exp23

import android.app.Application
import dagger.hilt.android.HiltAndroidApp


@HiltAndroidApp
class Exp23App : Application() {
    override fun onCreate() {
        super.onCreate()
        googlePayInitialise()
    }

    private fun googlePayInitialise() {

    }
}
