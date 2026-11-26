package com.angelsony.lab1a

import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

/** Layout 2: LinearLayout Screen */
class LinearLayoutActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_linear)

        findViewById<TextView>(R.id.tvBack).setOnClickListener { finish() }

        findViewById<android.view.View>(R.id.tileSleep).setOnClickListener {
            Toast.makeText(this, "Sleep Vitals: 8.2 hrs optimal", Toast.LENGTH_SHORT).show()
        }
        findViewById<android.view.View>(R.id.tileMood).setOnClickListener {
            Toast.makeText(this, "Mood Vitals: 90% positive days", Toast.LENGTH_SHORT).show()
        }
        findViewById<android.view.View>(R.id.tileWater).setOnClickListener {
            Toast.makeText(this, "Hydration Vitals: 6 of 8 glasses drank", Toast.LENGTH_SHORT).show()
        }
        findViewById<android.view.View>(R.id.tileTherapy).setOnClickListener {
            Toast.makeText(this, "Upcoming Session: Tuesday 10:00 AM", Toast.LENGTH_SHORT).show()
        }

        findViewById<android.view.View>(R.id.btnQuickLog).setOnClickListener {
            Toast.makeText(this, "Daily check-in logged via LinearLayout!", Toast.LENGTH_SHORT).show()
        }
    }
}
