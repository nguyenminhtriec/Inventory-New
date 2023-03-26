package com.theways.inventorynew

import android.app.Application
import com.theways.inventorynew.data.AppContainer
import com.theways.inventorynew.data.Container

class InventoryApplication : Application() {

    lateinit var appContainer: Container

    override fun onCreate() {
        super.onCreate()
        appContainer = AppContainer(this)
    }
}