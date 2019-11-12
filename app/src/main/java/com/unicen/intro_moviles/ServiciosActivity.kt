package com.unicen.intro_moviles

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class ServiciosActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_servicios)
    }

    var background: Intent? = null

    fun backgroundService(view: View){
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
