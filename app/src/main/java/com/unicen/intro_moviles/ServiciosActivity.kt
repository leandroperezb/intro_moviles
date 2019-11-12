package com.unicen.intro_moviles

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.content.LocalBroadcastManager
import android.view.View
import kotlinx.android.synthetic.main.activity_servicios.*

class ServiciosActivity : AppCompatActivity() {

    companion object {
        val BROADCASTNAME = "com.unicen.intro_moviles.RespuestaServicios"
    }

    inner class RespuestaServicios : BroadcastReceiver() {

        override fun onReceive(context: Context, intent: Intent) {
            if (intent.action != null && intent.action!!.equals(BROADCASTNAME)){
                val numeroEnString = intent.getIntExtra("numero", 0).toString()
                resultado.text = resultado.text.toString() + " " + numeroEnString
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_servicios)
    }

    override fun onResume() {
        super.onResume()
        LocalBroadcastManager.getInstance(applicationContext).registerReceiver(recibidor, IntentFilter(BROADCASTNAME))
    }

    override fun onPause() {
        super.onPause()
        LocalBroadcastManager.getInstance(applicationContext).unregisterReceiver(recibidor)
    }

    val recibidor = RespuestaServicios()

    var background: Intent? = null

    fun backgroundService(view: View){
        resultado.text = ""
        if (background == null)
            background = Intent(this, BackgroundService::class.java)
        for (i in 0..9) {
            background?.putExtra("contenido", i)
            startService(background)
        }
    }

    fun stopBackground(view: View){
        if (background != null)
            stopService(background)
    }
}
