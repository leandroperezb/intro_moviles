package com.unicen.intro_moviles

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import android.util.Log
import kotlin.random.Random

class MyBoundService : Service() {

    inner class MyBinder: Binder(){
        fun getService(): MyBoundService { return this@MyBoundService }
    }

    val miBinder = MyBinder()

    override fun onBind(intent: Intent): IBinder {
        return miBinder
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.i("Servicio Bound", "Destruido")
    }

    fun getNumber(): Int{
        return Random.nextInt(100)
    }
}
