package com.angelsony.wellbeing

import android.os.Bundle
import android.widget.CheckBox
import android.widget.EditText
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

/** Screen 3: RelativeLayout Mood check-in in Kotlin. */
class MoodActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mood)
        Ui.applyInsets(findViewById(R.id.root))

        val rgMood = findViewById<RadioGroup>(R.id.rgMood)
        val cbSleep = findViewById<CheckBox>(R.id.cbSleep)
        val cbExercise = findViewById<CheckBox>(R.id.cbExercise)
        val cbPeople = findViewById<CheckBox>(R.id.cbPeople)
        val etNote = findViewById<EditText>(R.id.etNote)

        findViewById<android.view.View>(R.id.tvBack)?.setOnClickListener { finish() }

        findViewById<android.view.View>(R.id.btnSave)?.setOnClickListener {
            val id = rgMood.checkedRadioButtonId
            if (id == -1) {
                Toast.makeText(this, "Choose how you feel first", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            val mood = findViewById<RadioButton>(id).text.toString()

            var helped = 0
            val tags = mutableListOf<String>()
            if (cbSleep.isChecked) { helped++; tags.add("Sleep") }
            if (cbExercise.isChecked) { helped++; tags.add("Exercise") }
            if (cbPeople.isChecked) { helped++; tags.add("People") }

            val note = etNote.text.toString().trim()
            DataManager.getInstance(this).saveMood(mood, "😊", note, tags)

            Toast.makeText(
                this,
                "Saved. Feeling ${mood.lowercase()}, $helped factors helped.",
                Toast.LENGTH_LONG
            ).show()
            finish()
        }
    }
}

