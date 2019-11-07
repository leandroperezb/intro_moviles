package com.unicen.intro_moviles

import android.content.Context
import android.content.SharedPreferences
import android.os.AsyncTask
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kotlinx.android.synthetic.main.activity_cronometro.*
import java.util.concurrent.TimeUnit
import java.util.concurrent.locks.ReentrantLock
import kotlin.concurrent.withLock

class CronometroActivity : AppCompatActivity() {

    val PREFS_FILENAME = "counter.prefs"
    var counter = 0
    var step : Long = 1
    val lock = ReentrantLock()
    val condicion = lock.newCondition()

    inner class Tarea: AsyncTask<Void, Int, Void>() {
        override fun doInBackground(vararg params: Void?): Void? {
            while (!isCancelled){
                lock.withLock {
                    counter++
                    publishProgress(counter)
                    condicion.await(step, TimeUnit.SECONDS)
                }
            }
            return null
        }
        override fun onProgressUpdate(vararg values: Int?) {
            super.onProgressUpdate(*values)
            counterView.text = values[0].toString()
        }
    }

    var asyn: Tarea? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cronometro)

        counter = getSharedPreferences(PREFS_FILENAME, Context.MODE_PRIVATE).getInt("count", 0)
        counterView.text = counter.toString()

        stop.isEnabled = false
        reset.isEnabled = false
        stepView.setText((1).toString())

        start.setOnClickListener {_ ->
            start()
        }

        stop.setOnClickListener{_ ->
            stepView.isEnabled = true
            asyn?.cancel(false)
            stop.isEnabled = false
            start.isEnabled = true
        }

        reset.setOnClickListener{_->
            counter=0
            counterView.text = (0).toString()
            stepView.isEnabled = true
            asyn?.cancel(false)
            stop.isEnabled = false
            reset.isEnabled = false
            start.isEnabled = true
        }
    }





    fun start(){
        stop.isEnabled = true
        reset.isEnabled = true
        start.isEnabled = false

        step = stepView.getText().toString().toLong()
        stepView.isEnabled = false

        asyn?.cancel(false)
        asyn = Tarea()
        asyn?.execute()
    }

    override fun onPause() {
        super.onPause()

        val preferencesEditor = getSharedPreferences(PREFS_FILENAME, Context.MODE_PRIVATE).edit()
        preferencesEditor.putInt("count", counter)
        preferencesEditor.apply()
    }

    override fun onDestroy() {
        super.onDestroy()
        asyn?.cancel(false)
    }
}
