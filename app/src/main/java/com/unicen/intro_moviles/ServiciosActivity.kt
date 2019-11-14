package com.unicen.intro_moviles

import android.content.*
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.IBinder
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

    var miBoundService: MyBoundService? = null

    val conexion = object: ServiceConnection{
        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            miBoundService = (service as MyBoundService.MyBinder).getService()
        }

        override fun onServiceDisconnected(name: ComponentName?) {
            miBoundService = null
        }
    }

    override fun onResume() {
        super.onResume()
        LocalBroadcastManager.getInstance(applicationContext).registerReceiver(recibidor, IntentFilter(BROADCASTNAME))
        bindService(Intent(this, MyBoundService::class.java), conexion, Context.BIND_AUTO_CREATE)
    }

    override fun onPause() {
        super.onPause()
        LocalBroadcastManager.getInstance(applicationContext).unregisterReceiver(recibidor)
        unbindService(conexion)
    }

    val recibidor = RespuestaServicios()

    var background: Intent? = null

    fun backgroundService(view: View){
        resultado.text = ""
        if (background == null)
            background = Intent(this, BackgroundService::class.java)
        for (i in 1..4) {
            background?.putExtra("contenido", i)
            startService(background)
        }
    }

    fun intentService(view: View){
        resultado.text = ""
        val myIntentService = Intent(this, MyIntentService::class.java)
        for (i in 1..4) {
            myIntentService?.putExtra("contenido", i)
            startService(myIntentService)
        }
    }

    fun boundService(view: View){
        resultado.text = ""
        if (miBoundService != null){
            resultado.text = miBoundService!!.getNumber().toString()
        }
    }

}
