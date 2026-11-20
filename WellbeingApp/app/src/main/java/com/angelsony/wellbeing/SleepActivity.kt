package com.angelsony.wellbeing

import android.os.Bundle
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

/** Screen 4: Sleep duration calculation in Kotlin. */
class SleepActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sleep)
        Ui.applyInsets(findViewById(R.id.root))

        val etBed = findViewById<EditText>(R.id.etBed)
        val etWake = findViewById<EditText>(R.id.etWake)
        val tvResult = findViewById<TextView>(R.id.tvResult)
        val tvTodayHrs = findViewById<TextView>(R.id.tvTodayHrs)
        val pbToday = findViewById<ProgressBar>(R.id.pbToday)

        findViewById<android.view.View>(R.id.tvBack)?.setOnClickListener { finish() }

        findViewById<android.view.View>(R.id.btnCalc)?.setOnClickListener {
            try {
                val bed = toMinutes(etBed.text.toString())
                val wake = toMinutes(etWake.text.toString())
                val mins = (wake - bed + 1440) % 1440
                val h = mins / 60
                val m = mins % 60

                val tip = if (h >= 7) {
                    "Well rested. Keep this rhythm going."
                } else {
                    "A little short. Aim for 7 to 9 hours tonight."
                }
                tvResult.text = "You slept ${h}h ${m}m. $tip"
                pbToday.progress = h.coerceAtMost(10)
                tvTodayHrs.text = "${h}h"
                DataManager.getInstance(this).saveSleepLog(
                    etBed.text.toString(),
                    etWake.text.toString(),
                    mins,
                    if (h >= 7) 4 else 3
                )
            } catch (_: Exception) {
                tvResult.text = "Please use the format HH:mm, for example 23:30."
            }
        }
    }

    private fun toMinutes(t: String): Int {
        val p = t.trim().split(":")
        val hh = p[0].toInt()
        val mm = p[1].toInt()
        require(hh in 0..23 && mm in 0..59)
        return hh * 60 + mm
    }
}

