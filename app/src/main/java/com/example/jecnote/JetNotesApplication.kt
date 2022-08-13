package com.example.jecnote

import android.app.Application
import com.example.jecnote.dependencyinjection.DependencyInjector

class JetNotesApplication:Application() {
    lateinit var dependencyInjector: DependencyInjector

    override fun onCreate() {
        super.onCreate()
        initDependencyInjector()
    }

    private fun initDependencyInjector(){
        dependencyInjector = DependencyInjector(this)
    }
}