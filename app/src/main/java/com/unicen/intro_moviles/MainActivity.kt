package com.unicen.intro_moviles

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun abrirHolaMundo(view: View){
        val intent: Intent = Intent(this, HolaMundoActivity::class.java)
        startActivity(intent)
    }

    fun abrirCalculadora(view: View){
        val intent: Intent = Intent(this, CalculadoraActivity::class.java)
        startActivity(intent)
    }

    fun abrirCronometro(view: View){
        val intent: Intent = Intent(this, CronometroActivity::class.java)
        startActivity(intent)
    }

    fun abrirServicios(view: View){
        val intent: Intent = Intent(this, ServiciosActivity::class.java)
        startActivity(intent)
    }
}
