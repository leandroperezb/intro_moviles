package com.unicen.intro_moviles

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.app.Activity
import android.content.Intent
import kotlinx.android.synthetic.main.activity_calculadora_op.*


class CalculadoraOpActivity : AppCompatActivity() {

    companion object {
        val OP_SUMAR: Int = 0
        val OP_RESTAR: Int = 1
        val OP_DIVIDIR: Int = 2
        val OP_MULTIPLICAR: Int = 3
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calculadora_op)
    }

    fun realizarOperacion(): Int{
        var resultado: Int = 0
        val v1 = Integer.valueOf(valor1.getText().toString())
        val v2 = Integer.valueOf(valor2.getText().toString())
        when (getIntent().getIntExtra("operacion", 0)){
            OP_SUMAR -> resultado = v1 + v2
            OP_RESTAR -> resultado = v1 - v2
            OP_DIVIDIR -> resultado = v1 / v2
            OP_MULTIPLICAR -> resultado = v1 * v2
        }
        return resultado
    }

    fun operar(view: View){
        val intent = Intent()
        intent.putExtra("resultado", realizarOperacion())
        setResult(Activity.RESULT_OK, intent)
        finish()
    }
}
