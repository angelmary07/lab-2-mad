package com.angelsony.lab2

import android.os.Bundle
import android.view.View
import android.view.animation.DecelerateInterpolator
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

/**
 * Main Activity for Lab Exercise 2:
 * Dynamic Numerical Processing and User Interaction in Kotlin & XML.
 * Enhanced with glassmorphic styling and staggered entrance animations.
 */
class MainActivity : AppCompatActivity() {

    private lateinit var controller: CalculatorController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val headerCard = findViewById<View>(R.id.headerCard)
        val cardInputs = findViewById<View>(R.id.cardInputs)
        val cardOperations = findViewById<View>(R.id.cardOperations)
        val cardResult = findViewById<View>(R.id.cardResult)

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

        // Staggered Cascade Entrance Animation
        val cards = listOf(headerCard, cardInputs, cardOperations, cardResult)
        cards.forEachIndexed { index, view ->
            view?.alpha = 0f
            view?.translationY = 50f
            view?.animate()
                ?.alpha(1f)
                ?.translationY(0f)
                ?.setDuration(450)
                ?.setStartDelay((index * 80).toLong())
                ?.setInterpolator(DecelerateInterpolator())
                ?.start()
        }
    }
}
