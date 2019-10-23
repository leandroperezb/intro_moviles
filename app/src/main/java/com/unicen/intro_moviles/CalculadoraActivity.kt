package com.unicen.intro_moviles

import android.app.Activity
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_calculadora.*

class CalculadoraActivity : AppCompatActivity() {
    val codigoCalculadoraOp: Int = 89

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calculadora)
        if (savedInstanceState != null)
            resultado.text = savedInstanceState.getString("resultado", "")
    }

    fun multiplicar(view: View){
        abrirCalculadora(CalculadoraOpActivity.OP_MULTIPLICAR)
    }

    fun dividir(view: View){
        abrirCalculadora(CalculadoraOpActivity.OP_DIVIDIR)
    }

    fun sumar(view: View){
        abrirCalculadora(CalculadoraOpActivity.OP_SUMAR)
    }

    fun restar(view: View){
        abrirCalculadora(CalculadoraOpActivity.OP_RESTAR)
    }

    fun abrirCalculadora(codigo: Int){
        val intent = Intent(this, CalculadoraOpActivity::class.java)
        intent.putExtra("operacion", codigo)
        startActivityForResult(intent, codigoCalculadoraOp)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == codigoCalculadoraOp)
            if (resultCode == Activity.RESULT_OK)
                if (data != null)
                    resultado.setText(data.getIntExtra("resultado",0).toString())
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        if (outState != null)
            outState.putString("resultado", resultado.text.toString())
    }
}
