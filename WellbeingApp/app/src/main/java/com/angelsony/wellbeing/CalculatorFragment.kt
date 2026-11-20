package com.angelsony.wellbeing

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment

/**
 * Fragment embedding the complete Lab Assignment Dynamic Numerical Processor
 * into the MindWell bottom navigation.
 */
class CalculatorFragment : Fragment() {

    private lateinit var controller: CalculatorController

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_calculator, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val etFirstValue = view.findViewById<EditText>(R.id.etFirstValue)
        val etSecondValue = view.findViewById<EditText>(R.id.etSecondValue)
        val tvResult = view.findViewById<TextView>(R.id.tvResult)
        val tvOperationBadge = view.findViewById<TextView>(R.id.tvOperationBadge)
        val tvColorIndicator = view.findViewById<TextView>(R.id.tvColorIndicator)
        val tvCalculationDetails = view.findViewById<TextView>(R.id.tvCalculationDetails)

        val btnAdd = view.findViewById<Button>(R.id.btnAdd)
        val btnSubtract = view.findViewById<Button>(R.id.btnSubtract)
        val btnMultiply = view.findViewById<Button>(R.id.btnMultiply)
        val btnDivide = view.findViewById<Button>(R.id.btnDivide)
        val btnClear = view.findViewById<Button>(R.id.btnClear)

        controller = CalculatorController(
            context = requireContext(),
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
        view.findViewById<Button>(R.id.btnPresetSleep)?.setOnClickListener {
            controller.setPresetValues("8.0", "6.5")
        }

        view.findViewById<Button>(R.id.btnPresetWater)?.setOnClickListener {
            controller.setPresetValues("70.0", "35.0")
        }
    }
}

