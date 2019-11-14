package com.unicen.intro_moviles

import android.app.IntentService
import android.content.Intent
import android.support.v4.content.LocalBroadcastManager
import android.util.Log


/**
 * An [IntentService] subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */
class MyIntentService : IntentService("MyIntentService") {

    override fun onHandleIntent(intent: Intent?) {
        try{
            Thread.sleep(5000)
            Log.i("ServicioIntent", intent?.getIntExtra("contenido", 0).toString())
        }catch (e: InterruptedException){
            Thread.currentThread().interrupt()
        }
        val intentNuevo = Intent(ServiciosActivity.BROADCASTNAME)
        intentNuevo.putExtra("numero", intent!!.getIntExtra("contenido", 0) + 1)
        LocalBroadcastManager.getInstance(applicationContext).sendBroadcast(intentNuevo)
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.i("ServicioIntent", "Destruido")
    }

}
