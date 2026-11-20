package com.angelsony.wellbeing

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.CheckBox
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton

/**
 * Screen 1: Welcome & Login Activity.
 * Provides user login, password/PIN validation, remembered credentials,
 * and quick guest demo mode access.
 */
class LoginActivity : AppCompatActivity() {

    private lateinit var dataManager: DataManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        Ui.applyInsets(findViewById(R.id.root))

        dataManager = DataManager.getInstance(this)

        val etName = findViewById<EditText>(R.id.etName)
        val etPass = findViewById<EditText>(R.id.etPass)
        val cbRemember = findViewById<CheckBox>(R.id.cbRemember)
        val btnLogin = findViewById<MaterialButton>(R.id.btnLogin)
        val btnGuest = findViewById<MaterialButton>(R.id.btnGuest)

        // Restore remembered user
        if (dataManager.isRemembered()) {
            val savedName = dataManager.getUserName()
            if (savedName.isNotEmpty() && savedName != "Friend") {
                etName.setText(savedName)
                cbRemember.isChecked = true
            }
        }

        btnLogin.setOnClickListener {
            val name = etName.text.toString().trim()
            val pass = etPass.text.toString()

            if (name.isEmpty()) {
                etName.error = "Please enter your name or email"
                Toast.makeText(this, "Name is required to personalize your companion", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (pass.length < 4) {
                etPass.error = "Please use at least 4 characters or digits"
                Toast.makeText(this, "Password/PIN must be at least 4 characters", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            dataManager.saveUser(name, cbRemember.isChecked)

            Toast.makeText(this, "Welcome, $name! Loading your wellbeing journey...", Toast.LENGTH_SHORT).show()
            launchMain(name)
        }

        btnGuest.setOnClickListener {
            dataManager.saveUser("Angel", false)
            Toast.makeText(this, "Exploring in Demo Mode as Angel", Toast.LENGTH_SHORT).show()
            launchMain("Angel")
        }

        // Emergency Helpline Card on Login Screen
        findViewById<android.view.View>(R.id.layoutEmergency)?.setOnClickListener {
            showEmergencySosDialog()
        }
    }

    private fun launchMain(name: String) {
        val intent = Intent(this, MainActivity::class.java).apply {
            putExtra("user_name", name)
        }
        startActivity(intent)
        finish()
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

