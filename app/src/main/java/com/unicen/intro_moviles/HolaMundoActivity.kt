package com.unicen.intro_moviles

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class HolaMundoActivity : AppCompatActivity() {
    var contador: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hola_mundo)

        val textoContador = findViewById<TextView>(R.id.mostrarContador)
        if (savedInstanceState != null)
            contador = savedInstanceState.getInt("contador", 0)
        textoContador.text = getString(R.string.mostrar_contador, contador)

        findViewById<Button>(R.id.contar).setOnClickListener {
            contador++
            textoContador.text = getString(R.string.mostrar_contador, contador)
        }
        findViewById<Button>(R.id.descontar).setOnClickListener {
            if (contador > 0) contador--
            textoContador.text = getString(R.string.mostrar_contador, contador)
        }
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        if (outState != null)
            outState.putInt("contador", contador)
    }
}
