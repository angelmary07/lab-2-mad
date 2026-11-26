package com.angelsony.lab1a

import android.os.Bundle
import android.widget.Button
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

/** Layout 3: RelativeLayout Screen */
class RelativeLayoutActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_relative)

        findViewById<TextView>(R.id.tvBack).setOnClickListener { finish() }

        val rgMood = findViewById<RadioGroup>(R.id.rgMood)

        findViewById<Button>(R.id.btnSaveAssessment).setOnClickListener {
            val checkedId = rgMood.checkedRadioButtonId
            val moodText = findViewById<RadioButton>(checkedId)?.text ?: "None"
            Toast.makeText(this, "Saved Assessment: $moodText via RelativeLayout", Toast.LENGTH_LONG).show()
            finish()
        }
    }
}
