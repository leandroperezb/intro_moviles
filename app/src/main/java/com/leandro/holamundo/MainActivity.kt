package com.leandro.holamundo

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    var contador: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val textoContador = findViewById<TextView>(R.id.mostrarContador)
        findViewById<Button>(R.id.contar).setOnClickListener {
            contador++
            textoContador.text = getString(R.string.mostrar_contador, contador)
        }
        findViewById<Button>(R.id.descontar).setOnClickListener {
            if (contador > 0) contador--
            textoContador.text = getString(R.string.mostrar_contador, contador)
        }
    }

}
