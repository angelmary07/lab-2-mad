package com.angelsony.wellbeing

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.button.MaterialButton

/**
 * Main Activity hosting the 5 central pillars via BottomNavigationView:
 * 1. Home / Insights Dashboard
 * 2. Mood Tracker & Multimedia Journal
 * 3. Sleep Tracker & Guided Relaxation
 * 4. Appointments & Personal Assistant
 * 5. Lab MAD Dynamic Numerical Processor & Calculator
 */
class MainActivity : AppCompatActivity() {

    private lateinit var bottomNav: BottomNavigationView
    private lateinit var dataManager: DataManager

    private val homeFragment = HomeFragment()
    private val moodJournalFragment = MoodJournalFragment()
    private val sleepRelaxFragment = SleepRelaxFragment()
    private val scheduleAssistantFragment = ScheduleAssistantFragment()
    private val calculatorFragment = CalculatorFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Ui.applyInsets(findViewById(R.id.coordinatorLayout))

        dataManager = DataManager.getInstance(this)

        bottomNav = findViewById(R.id.bottomNav)
        setupNavigation()
        setupTopBar()

        // Set default fragment
        if (savedInstanceState == null) {
            loadFragment(homeFragment)
        }
    }

    private fun setupNavigation() {
        bottomNav.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.menu_home -> {
                    loadFragment(homeFragment)
                    true
                }
                R.id.menu_mood_journal -> {
                    loadFragment(moodJournalFragment)
                    true
                }
                R.id.menu_sleep_relax -> {
                    loadFragment(sleepRelaxFragment)
                    true
                }
                R.id.menu_schedule_assistant -> {
                    loadFragment(scheduleAssistantFragment)
                    true
                }
                R.id.menu_calculator -> {
                    loadFragment(calculatorFragment)
                    true
                }
                else -> false
            }
        }
    }

    fun selectTab(menuItemId: Int) {
        bottomNav.selectedItemId = menuItemId
    }

    private fun loadFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, fragment)
            .commit()
    }

    private fun setupTopBar() {
        // Emergency SOS Dialog
        findViewById<MaterialButton>(R.id.btnTopSos)?.setOnClickListener {
            showEmergencySosDialog()
        }

        // Vault Lock / User Status
        findViewById<ImageView>(R.id.ivUserAvatar)?.setOnClickListener {
            val user = dataManager.getUserName()
            AlertDialog.Builder(this)
                .setTitle("MindWell Profile & Security")
                .setMessage("Signed in as: $user\nPrivate Vault: PIN Protected (Default: 1234)\nAll data saved locally with full privacy.")
                .setPositiveButton("OK", null)
                .setNeutralButton("Launch Lab Calculator Standalone") { _, _ ->
                    startActivity(Intent(this, CalculatorActivity::class.java))
                }
                .show()
        }
    }

    private fun showEmergencySosDialog() {
        val dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_emergency_sos, null)
        val dialog = AlertDialog.Builder(this)
            .setView(dialogView)
            .create()

        dialogView.findViewById<MaterialButton>(R.id.btnCallTeleManas)?.setOnClickListener {
            dialNumber("14416")
        }

        dialogView.findViewById<MaterialButton>(R.id.btnCallKiran)?.setOnClickListener {
            dialNumber("18005990019")
        }

        dialogView.findViewById<MaterialButton>(R.id.btnCall988)?.setOnClickListener {
            dialNumber("988")
        }

        dialogView.findViewById<MaterialButton>(R.id.btnCloseSos)?.setOnClickListener {
            dialog.dismiss()
        }

        dialog.show()
    }

    private fun dialNumber(number: String) {
        try {
            val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:$number"))
            startActivity(intent)
        } catch (_: Exception) {
            Toast.makeText(this, "Calling $number...", Toast.LENGTH_SHORT).show()
        }
    }
}

