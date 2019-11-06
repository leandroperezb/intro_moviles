package com.unicen.intro_moviles

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
    var prefs: SharedPreferences? = null
    var counter = 0
    var step : Long = 1
    var active = false
    val lock = ReentrantLock()
    val condicion = lock.newCondition()

    var asyn = object: AsyncTask< Int , Int, Int>() {
        override fun doInBackground(vararg params: Int?): Int? {
            while (active){
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cronometro)
        if (savedInstanceState != null) {
            counter = savedInstanceState.getString("count", "").toInt()
            counterView.text = counter.toString()
            active=savedInstanceState.getString("act", "true").toBoolean()

            if (active){
                Log.d("esta adentro","adentro")
                start()
            }

        }

        stop.isEnabled = false
        reset.isEnabled = false
        stepView.setText((1).toString())

        start.setOnClickListener {_ ->
            start()
        }

        stop.setOnClickListener{_ ->
            stepView.isEnabled = true
            active = false
            stop.isEnabled = false
            start.isEnabled = true
        }

        reset.setOnClickListener{_->
            counter=0
            counterView.text = (0).toString()
            stepView.isEnabled = true
            active = false
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

        asyn = object: AsyncTask<Int, Int, Int>() {
            override fun doInBackground(vararg params: Int?): Int? {
                while (active){
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
        asyn.execute(0)
        active = true
    }

    override fun onPause() {
        super.onPause()
        val preferencesEditor = prefs?.edit()
        preferencesEditor?.putInt("count", counter)
        preferencesEditor?.apply()
    }
}
