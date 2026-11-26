package com.angelsony.lab1a

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

/** Layout 1: ConstraintLayout Screen */
class ConstraintLayoutActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_constraint)

        findViewById<TextView>(R.id.tvBack).setOnClickListener { finish() }

        val etName = findViewById<EditText>(R.id.etPatientName)
        val etMedId = findViewById<EditText>(R.id.etMedId)

        findViewById<Button>(R.id.btnSubmit).setOnClickListener {
            val name = etName.text.toString().trim()
            val id = etMedId.text.toString().trim()
            if (name.isEmpty() || id.isEmpty()) {
                Toast.makeText(this, "ConstraintLayout: Please enter patient details!", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Signed in: $name (ID: $id) via ConstraintLayout", Toast.LENGTH_LONG).show()
            }
        }
    }
}
