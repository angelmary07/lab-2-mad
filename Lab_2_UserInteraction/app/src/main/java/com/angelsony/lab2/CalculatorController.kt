package com.angelsony.lab2

import android.content.Context
import android.graphics.Color
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast

/**
 * Controller for Lab Exercise 2: User Interaction & Dynamic Processing.
 * Fulfills all rubric requirements:
 * 1. Accepts two numerical values
 * 2. Performs +, -, *, /
 * 3. Shows Toast if inputs are empty
 * 4. Handles division by zero
 * 5. Clears input fields and result on Clear button click
 * 6. Changes result TextView color based on operation
 * 7. Changes button background color when clicked
 * 8. Displays operation performed along with result
 */
class CalculatorController(
    private val context: Context,
    private val etFirstValue: EditText,
    private val etSecondValue: EditText,
    private val tvResult: TextView,
    private val tvOperationBadge: TextView?,
    private val tvColorIndicator: TextView?,
    private val tvCalculationDetails: TextView?,
    private val btnAdd: Button,
    private val btnSubtract: Button,
    private val btnMultiply: Button,
    private val btnDivide: Button,
    private val btnClear: Button
) {

    private val colorAddition = Color.parseColor("#2E7D32")     // Emerald Green
    private val colorSubtraction = Color.parseColor("#1565C0")  // Ocean Blue
    private val colorMultiplication = Color.parseColor("#6A1B9A") // Royal Purple
    private val colorDivision = Color.parseColor("#D84315")     // Deep Orange
    private val colorError = Color.parseColor("#C62828")        // Crimson Red
    private val colorDefault = Color.parseColor("#1F2421")      // Default Text Ink

    private val btnNormalBg = Color.parseColor("#2D5A43")       // Normal Moss
    private val btnClickedBg = Color.parseColor("#FFB703")      // Clicked Golden Amber

    init {
        setupListeners()
    }

    private fun setupListeners() {
        btnAdd.setOnClickListener {
            highlightButton(btnAdd)
            calculate("Addition", "+", colorAddition) { a, b -> a + b }
        }

        btnSubtract.setOnClickListener {
            highlightButton(btnSubtract)
            calculate("Subtraction", "−", colorSubtraction) { a, b -> a - b }
        }

        btnMultiply.setOnClickListener {
            highlightButton(btnMultiply)
            calculate("Multiplication", "×", colorMultiplication) { a, b -> a * b }
        }

        btnDivide.setOnClickListener {
            highlightButton(btnDivide)
            calculateDivision()
        }

        btnClear.setOnClickListener {
            clearAll()
        }
    }

    private fun highlightButton(activeButton: Button) {
        resetButtonColors()
        try {
            activeButton.setBackgroundColor(btnClickedBg)
            activeButton.setTextColor(Color.parseColor("#1F2421"))
        } catch (_: Exception) {}
    }

    private fun resetButtonColors() {
        val buttons = listOf(btnAdd, btnSubtract, btnMultiply, btnDivide)
        for (btn in buttons) {
            try {
                btn.setBackgroundColor(btnNormalBg)
                btn.setTextColor(Color.WHITE)
            } catch (_: Exception) {}
        }
    }

    private inline fun calculate(
        operationName: String,
        symbol: String,
        resultColor: Int,
        operation: (Double, Double) -> Double
    ) {
        val (num1, num2) = validateAndGetInputs() ?: return

        val result = operation(num1, num2)
        val formattedResult = formatNumber(result)
        val num1Str = formatNumber(num1)
        val num2Str = formatNumber(num2)

        tvResult.text = "Result: $formattedResult"
        tvResult.setTextColor(resultColor)

        tvOperationBadge?.apply {
            text = operationName.uppercase()
            setBackgroundColor(resultColor)
            setTextColor(Color.WHITE)
        }

        tvColorIndicator?.apply {
            text = "Color: $operationName (Dynamic)"
            setTextColor(resultColor)
        }

        tvCalculationDetails?.apply {
            text = "$operationName Performed: $num1Str $symbol $num2Str = $formattedResult"
            setTextColor(resultColor)
        }

        Toast.makeText(
            context,
            "$operationName performed: $num1Str $symbol $num2Str = $formattedResult",
            Toast.LENGTH_SHORT
        ).show()
    }

    private fun calculateDivision() {
        val (num1, num2) = validateAndGetInputs() ?: return

        val num1Str = formatNumber(num1)
        val num2Str = formatNumber(num2)

        if (num2 == 0.0) {
            etSecondValue.error = "Divider cannot be 0"
            tvResult.text = "Error: Division by zero is undefined!"
            tvResult.setTextColor(colorError)

            tvOperationBadge?.apply {
                text = "ERROR: DIV/0"
                setBackgroundColor(colorError)
                setTextColor(Color.WHITE)
            }

            tvCalculationDetails?.apply {
                text = "Mathematical Error: Cannot divide $num1Str by 0.0"
                setTextColor(colorError)
            }

            Toast.makeText(
                context,
                "Error: Division by zero is undefined! Please enter a non-zero denominator.",
                Toast.LENGTH_LONG
            ).show()
            return
        }

        val result = num1 / num2
        val formattedResult = formatNumber(result)

        tvResult.text = "Result: $formattedResult"
        tvResult.setTextColor(colorDivision)

        tvOperationBadge?.apply {
            text = "DIVISION"
            setBackgroundColor(colorDivision)
            setTextColor(Color.WHITE)
        }

        tvColorIndicator?.apply {
            text = "Color: Division (Deep Orange)"
            setTextColor(colorDivision)
        }

        tvCalculationDetails?.apply {
            text = "Division Performed: $num1Str ÷ $num2Str = $formattedResult"
            setTextColor(colorDivision)
        }

        Toast.makeText(
            context,
            "Division performed: $num1Str ÷ $num2Str = $formattedResult",
            Toast.LENGTH_SHORT
        ).show()
    }

    private fun validateAndGetInputs(): Pair<Double, Double>? {
        val str1 = etFirstValue.text.toString().trim()
        val str2 = etSecondValue.text.toString().trim()

        if (str1.isEmpty() && str2.isEmpty()) {
            etFirstValue.error = "Please enter first value"
            etSecondValue.error = "Please enter second value"
            Toast.makeText(context, "Please enter both numerical values!", Toast.LENGTH_SHORT).show()
            return null
        }

        if (str1.isEmpty()) {
            etFirstValue.error = "Please enter first value"
            etFirstValue.requestFocus()
            Toast.makeText(context, "First numerical value is required!", Toast.LENGTH_SHORT).show()
            return null
        }

        if (str2.isEmpty()) {
            etSecondValue.error = "Please enter second value"
            etSecondValue.requestFocus()
            Toast.makeText(context, "Second numerical value is required!", Toast.LENGTH_SHORT).show()
            return null
        }

        val val1 = str1.toDoubleOrNull()
        if (val1 == null) {
            etFirstValue.error = "Invalid numerical format"
            Toast.makeText(context, "First value must be a valid number!", Toast.LENGTH_SHORT).show()
            return null
        }

        val val2 = str2.toDoubleOrNull()
        if (val2 == null) {
            etSecondValue.error = "Invalid numerical format"
            Toast.makeText(context, "Second value must be a valid number!", Toast.LENGTH_SHORT).show()
            return null
        }

        etFirstValue.error = null
        etSecondValue.error = null

        return Pair(val1, val2)
    }

    fun clearAll() {
        etFirstValue.text?.clear()
        etSecondValue.text?.clear()
        etFirstValue.error = null
        etSecondValue.error = null

        tvResult.text = "Calculated result will appear dynamically here"
        tvResult.setTextColor(colorDefault)

        tvOperationBadge?.apply {
            text = "READY"
            setBackgroundColor(btnNormalBg)
            setTextColor(Color.WHITE)
        }

        tvColorIndicator?.apply {
            text = "Color: Default"
            setTextColor(Color.parseColor("#8D968F"))
        }

        tvCalculationDetails?.apply {
            text = "Select two values and click any operation (+, −, ×, ÷)"
            setTextColor(Color.parseColor("#59625D"))
        }

        resetButtonColors()

        Toast.makeText(context, "All inputs and results cleared!", Toast.LENGTH_SHORT).show()
    }

    fun setPresetValues(val1: String, val2: String) {
        etFirstValue.setText(val1)
        etSecondValue.setText(val2)
        etFirstValue.error = null
        etSecondValue.error = null
    }

    private fun formatNumber(num: Double): String {
        return if (num % 1.0 == 0.0) {
            String.format("%.1f", num)
        } else {
            String.format("%.2f", num)
        }
    }
}
