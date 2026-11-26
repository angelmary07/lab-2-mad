package com.angelsony.lab2

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

/**
 * Main Activity for Lab Exercise 2:
 * Dynamic Numerical Processing and User Interaction in Kotlin & XML.
 */
class MainActivity : AppCompatActivity() {

    private lateinit var controller: CalculatorController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val etFirstValue = findViewById<EditText>(R.id.etFirstValue)
        val etSecondValue = findViewById<EditText>(R.id.etSecondValue)
        val tvResult = findViewById<TextView>(R.id.tvResult)
        val tvOperationBadge = findViewById<TextView>(R.id.tvOperationBadge)
        val tvColorIndicator = findViewById<TextView>(R.id.tvColorIndicator)
        val tvCalculationDetails = findViewById<TextView>(R.id.tvCalculationDetails)

        val btnAdd = findViewById<Button>(R.id.btnAdd)
        val btnSubtract = findViewById<Button>(R.id.btnSubtract)
        val btnMultiply = findViewById<Button>(R.id.btnMultiply)
        val btnDivide = findViewById<Button>(R.id.btnDivide)
        val btnClear = findViewById<Button>(R.id.btnClear)

        controller = CalculatorController(
            context = this,
            etFirstValue = etFirstValue,
            etSecondValue = etSecondValue,
            tvResult = tvResult,
            tvOperationBadge = tvOperationBadge,
            tvColorIndicator = tvColorIndicator,
            tvCalculationDetails = tvCalculationDetails,
            btnAdd = btnAdd,
            btnSubtract = btnSubtract,
            btnMultiply = btnMultiply,
            btnDivide = btnDivide,
            btnClear = btnClear
        )

        // Wellbeing Quick Presets
        findViewById<Button>(R.id.btnPresetSleep)?.setOnClickListener {
            controller.setPresetValues("8.0", "6.5")
        }

        findViewById<Button>(R.id.btnPresetWater)?.setOnClickListener {
            controller.setPresetValues("70.0", "35.0")
        }
    }
}
