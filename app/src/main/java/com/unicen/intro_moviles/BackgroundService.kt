package com.unicen.intro_moviles

import android.app.Service
import android.content.Intent
import android.os.*
import android.util.Log

class BackgroundService : Service() {
    var looper: Looper? = null
    var handler: ServiceHandler? = null

    inner class ServiceHandler(loo: Looper): Handler(loo){
        override fun handleMessage(msg: Message?) {
            super.handleMessage(msg)
            try{
                Thread.sleep(5000)
                Log.i("Servicio", msg?.obj.toString())
            }catch (e: InterruptedException){
                Thread.currentThread().interrupt()
            }
            stopSelf(msg!!.arg1)
        }
    }

    fun nuevoHandler(){
        val thread = HandlerThread("ServicioBackground", Process.THREAD_PRIORITY_BACKGROUND)
        thread.start()
        looper = thread.looper
        handler = ServiceHandler(looper!!)
    }

    override fun onCreate() {
        super.onCreate()
        nuevoHandler()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        super.onStartCommand(intent, flags, startId)
        var msg = handler?.obtainMessage()
        msg?.arg1 = startId
        msg?.obj = intent?.getIntExtra("contenido", 0)

        nuevoHandler()

        handler?.sendMessage(msg)
        return START_STICKY
    }

    override fun onBind(intent: Intent): IBinder? {
        return null
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.i("Servicio", "Destruido")
    }

}
