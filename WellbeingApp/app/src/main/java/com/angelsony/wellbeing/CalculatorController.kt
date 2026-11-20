package com.angelsony.wellbeing

import android.content.Context
import android.graphics.Color
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat

/**
 * Controller encapsulating all requirements for the Dynamic Numerical Processor
 * Lab Assignment:
 * 1. Accepts two numerical inputs
 * 2. Performs +, -, *, /
 * 3. Validates inputs & handles empty fields with Toast
 * 4. Handles division by zero gracefully
 * 5. Clears all fields and resets colors
 * 6. Changes result TextView color dynamically based on operation
 * 7. Changes button background color when clicked
 * 8. Displays the operation performed alongside the result
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

    // Operation Color Definitions
    val colorAddition: Int = Color.parseColor("#2E7D32")     // Vibrant Emerald Green
    val colorSubtraction: Int = Color.parseColor("#1565C0")  // Vibrant Ocean Blue
    val colorMultiplication: Int = Color.parseColor("#6A1B9A") // Royal Purple
    val colorDivision: Int = Color.parseColor("#D84315")     // Deep Orange
    val colorError: Int = Color.parseColor("#C62828")        // Crimson Red
    val colorDefault: Int = Color.parseColor("#1F2421")      // Default Text Ink

    val btnNormalBg: Int = Color.parseColor("#2D5A43")       // Normal Moss
    val btnClickedBg: Int = Color.parseColor("#FFB703")      // Clicked Golden Amber Highlight

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

    /**
     * Changes clicked button background color dynamically to provide visual feedback.
     */
    private fun highlightButton(activeButton: Button) {
        // Reset all buttons to default color
        resetButtonColors()

        // Highlight the currently active button
        try {
            activeButton.setBackgroundColor(btnClickedBg)
            activeButton.setTextColor(Color.parseColor("#1F2421"))
        } catch (_: Exception) {
        }
    }

    private fun resetButtonColors() {
        val buttons = listOf(btnAdd, btnSubtract, btnMultiply, btnDivide)
        for (btn in buttons) {
            try {
                btn.setBackgroundColor(btnNormalBg)
                btn.setTextColor(Color.WHITE)
            } catch (_: Exception) {
            }
        }
    }

    /**
     * Core calculation logic with input validation, Toast on empty input, and dynamic color change.
     */
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

        // Update Result TextView dynamically
        tvResult.text = "Result: $formattedResult"
        tvResult.setTextColor(resultColor)

        // Display operation performed along with the result
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

    /**
     * Handles division with strict division by zero check.
     */
    private fun calculateDivision() {
        val (num1, num2) = validateAndGetInputs() ?: return

        val num1Str = formatNumber(num1)
        val num2Str = formatNumber(num2)

        if (num2 == 0.0) {
            // Division by Zero handling
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

    /**
     * Validates input fields:
     * - Checks for empty fields and shows Toast
     * - Validates numeric parsing
     * - Returns Pair<Double, Double> or null if invalid
     */
    private fun validateAndGetInputs(): Pair<Double, Double>? {
        val str1 = etFirstValue.text.toString().trim()
        val str2 = etSecondValue.text.toString().trim()

        // Check if user leaves input fields empty
        if (str1.isEmpty() && str2.isEmpty()) {
            etFirstValue.error = "Please enter first value"
            etSecondValue.error = "Please enter second value"
            Toast.makeText(
                context,
                "Please enter both numerical values!",
                Toast.LENGTH_SHORT
            ).show()
            return null
        }

        if (str1.isEmpty()) {
            etFirstValue.error = "Please enter first value"
            etFirstValue.requestFocus()
            Toast.makeText(
                context,
                "First numerical value is required!",
                Toast.LENGTH_SHORT
            ).show()
            return null
        }

        if (str2.isEmpty()) {
            etSecondValue.error = "Please enter second value"
            etSecondValue.requestFocus()
            Toast.makeText(
                context,
                "Second numerical value is required!",
                Toast.LENGTH_SHORT
            ).show()
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

        // Clear any previous errors
        etFirstValue.error = null
        etSecondValue.error = null

        return Pair(val1, val2)
    }

    /**
     * Clears all entered values, resets the result TextView, and resets button colors.
     */
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
            text = "Default Color"
            setTextColor(Color.parseColor("#8D968F"))
        }

        tvCalculationDetails?.apply {
            text = "Select two values and click any operation (+, −, ×, ÷)"
            setTextColor(Color.parseColor("#59625D"))
        }

        resetButtonColors()

        Toast.makeText(context, "All inputs and results cleared!", Toast.LENGTH_SHORT).show()
    }

    /**
     * Preset helper to fill values for wellness metric calculations.
     */
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

