package com.angelsony.lab1a

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

/** Layout 4: TableLayout Screen */
class TableLayoutActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_table)

        findViewById<TextView>(R.id.tvBack).setOnClickListener { finish() }

        val etBed = findViewById<EditText>(R.id.etBedTime)
        val etWake = findViewById<EditText>(R.id.etWakeTime)
        val tvDurationResult = findViewById<TextView>(R.id.tvDurationResult)

        findViewById<Button>(R.id.btnComputeDuration).setOnClickListener {
            try {
                val bedStr = etBed.text.toString().trim()
                val wakeStr = etWake.text.toString().trim()
                if (bedStr.isEmpty() || wakeStr.isEmpty()) {
                    Toast.makeText(this, "Please enter both Bed and Wake times!", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }
                val bedParts = bedStr.split(":").map { it.toInt() }
                val wakeParts = wakeStr.split(":").map { it.toInt() }

                val bedMins = bedParts[0] * 60 + bedParts[1]
                val wakeMins = wakeParts[0] * 60 + wakeParts[1]
                val diff = (wakeMins - bedMins + 1440) % 1440
                val h = diff / 60
                val m = diff % 60

                tvDurationResult.text = "Logged to Table: ${h}h ${m}m calculated duration."
                Toast.makeText(this, "Added to Table Matrix: ${h}h ${m}m", Toast.LENGTH_SHORT).show()
            } catch (_: Exception) {
                tvDurationResult.text = "Format error! Use HH:MM (e.g. 23:00 and 07:15)"
            }
        }
    }
}
