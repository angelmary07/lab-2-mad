package com.angelsony.lab1b

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.CheckBox
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.SeekBar
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.materialswitch.MaterialSwitch

/**
 * Lab Exercise 1B: Implement Different Types of Views.
 * Implements and wires up all 7 required views:
 * 1. SeekBar
 * 2. FloatingActionButton (FAB)
 * 3. ScrollView
 * 4. Spinner
 * 5. CheckBox
 * 6. RadioButton
 * 7. Switch
 */
class MainActivity : AppCompatActivity() {

    private lateinit var seekBarStress: SeekBar
    private lateinit var tvSeekBarValue: TextView
    private lateinit var tvStressCategory: TextView
    private lateinit var spinnerSpecialist: Spinner
    private lateinit var cbSleep: CheckBox
    private lateinit var cbWater: CheckBox
    private lateinit var cbExercise: CheckBox
    private lateinit var cbMeditation: CheckBox
    private lateinit var rgConsultation: RadioGroup
    private lateinit var switchBedtime: MaterialSwitch
    private lateinit var switchSos: MaterialSwitch
    private lateinit var tvLiveSummary: TextView
    private lateinit var fabQuickAction: FloatingActionButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // View 1: SeekBar
        seekBarStress = findViewById(R.id.seekBarStress)
        tvSeekBarValue = findViewById(R.id.tvSeekBarValue)
        tvStressCategory = findViewById(R.id.tvStressCategory)

        // View 2: FAB
        fabQuickAction = findViewById(R.id.fabQuickAction)

        // View 4: Spinner
        spinnerSpecialist = findViewById(R.id.spinnerSpecialist)

        // View 5: CheckBoxes
        cbSleep = findViewById(R.id.cbSleep)
        cbWater = findViewById(R.id.cbWater)
        cbExercise = findViewById(R.id.cbExercise)
        cbMeditation = findViewById(R.id.cbMeditation)

        // View 6: RadioButton & RadioGroup
        rgConsultation = findViewById(R.id.rgConsultation)

        // View 7: Switches
        switchBedtime = findViewById(R.id.switchBedtime)
        switchSos = findViewById(R.id.switchSos)

        tvLiveSummary = findViewById(R.id.tvLiveSummary)

        setupSeekBar()
        setupSpinner()
        setupCheckBoxes()
        setupRadioGroup()
        setupSwitches()
        setupFab()

        updateLiveSummary()
    }

    private fun setupSeekBar() {
        seekBarStress.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                tvSeekBarValue.text = "$progress / 100"
                tvStressCategory.text = when {
                    progress < 30 -> "Status: Low Stress • Calm & Rested 😌"
                    progress < 70 -> "Status: Moderate • Balanced Equilibrium 😊"
                    else -> "Status: Elevated Stress • Mindful Pause Recommended 🧘"
                }
                updateLiveSummary()
            }
            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                Toast.makeText(this@MainActivity, "Stress level updated to ${seekBar?.progress}/100", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun setupSpinner() {
        val specialists = arrayOf(
            "Clinical Psychology & Mindfulness (Dr. Maya Sharma)",
            "Cognitive Behavioral Therapy - CBT (David Vance)",
            "Stress & Sleep Rhythm Clinic (Dr. Aisha Patel)",
            "General Wellbeing Coaching & Routine"
        )
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, specialists)
        spinnerSpecialist.adapter = adapter

        spinnerSpecialist.onItemSelectedListener = object : android.widget.AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: android.widget.AdapterView<*>?, view: android.view.View?, position: Int, id: Long) {
                updateLiveSummary()
            }
            override fun onNothingSelected(parent: android.widget.AdapterView<*>?) {}
        }
    }

    private fun setupCheckBoxes() {
        val listener = { _: android.widget.CompoundButton, _: Boolean ->
            updateLiveSummary()
        }
        cbSleep.setOnCheckedChangeListener(listener)
        cbWater.setOnCheckedChangeListener(listener)
        cbExercise.setOnCheckedChangeListener(listener)
        cbMeditation.setOnCheckedChangeListener(listener)
    }

    private fun setupRadioGroup() {
        rgConsultation.setOnCheckedChangeListener { _, _ ->
            updateLiveSummary()
        }
    }

    private fun setupSwitches() {
        switchBedtime.setOnCheckedChangeListener { _, isChecked ->
            Toast.makeText(this, "Bedtime reminder: ${if (isChecked) "Active (10:30 PM)" else "Disabled"}", Toast.LENGTH_SHORT).show()
            updateLiveSummary()
        }
        switchSos.setOnCheckedChangeListener { _, isChecked ->
            Toast.makeText(this, "24/7 SOS alerts: ${if (isChecked) "Enabled" else "Muted"}", Toast.LENGTH_SHORT).show()
            updateLiveSummary()
        }
    }

    private fun setupFab() {
        fabQuickAction.setOnClickListener {
            Toast.makeText(this, "FAB Clicked: Quick clinical intake submitted successfully! 🌟", Toast.LENGTH_LONG).show()
            updateLiveSummary()
        }
    }

    private fun updateLiveSummary() {
        val stress = seekBarStress.progress
        val specialist = spinnerSpecialist.selectedItem?.toString()?.split("(")?.first()?.trim() ?: "Psychology"
        
        var habitCount = 0
        if (cbSleep.isChecked) habitCount++
        if (cbWater.isChecked) habitCount++
        if (cbExercise.isChecked) habitCount++
        if (cbMeditation.isChecked) habitCount++

        val checkedRadioId = rgConsultation.checkedRadioButtonId
        val mode = findViewById<RadioButton>(checkedRadioId)?.text?.split(" ")?.getOrNull(1) ?: "Video"

        val remindersOn = switchBedtime.isChecked && switchSos.isChecked

        tvLiveSummary.text = "✓ SeekBar: Stress level set to $stress/100\n" +
                "✓ Spinner: Selected $specialist\n" +
                "✓ CheckBoxes: $habitCount of 4 healthy habits checked\n" +
                "✓ RadioButton: Consultation mode is $mode\n" +
                "✓ Switches: Automated alerts are ${if (remindersOn) "Enabled" else "Partially configured"}\n" +
                "✓ ScrollView & FAB: Fully responsive and active!"
    }
}
