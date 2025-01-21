package com.example.projectpam

import android.app.Application
import com.example.projectpam.dependeciesinjection.AppContainer
import com.example.projectpam.dependeciesinjection.ContainerApp

class TiketkuApplication : Application() {
    lateinit var container: AppContainer
    override fun onCreate() {
        super.onCreate()
        container = ContainerApp()
    }
}