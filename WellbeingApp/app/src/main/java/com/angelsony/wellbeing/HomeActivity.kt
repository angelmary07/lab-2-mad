package com.angelsony.wellbeing

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

/**
 * Screen 2: Legacy Home Activity with weighted tiles (Kept for backwards compatibility).
 * Automatically points to the rich MainActivity hub.
 */
class HomeActivity : AppCompatActivity() {

    private lateinit var tvLast: TextView
    private lateinit var dataManager: DataManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        Ui.applyInsets(findViewById(R.id.root))

        dataManager = DataManager.getInstance(this)
        var name = intent.getStringExtra("name")
        if (name.isNullOrEmpty()) name = dataManager.getUserName()

        val hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY)
        val part = when {
            hour < 12 -> "Good morning"
            hour < 17 -> "Good afternoon"
            else -> "Good evening"
        }

        val tvDate = findViewById<TextView>(R.id.tvDate)
        val tvGreeting = findViewById<TextView>(R.id.tvGreeting)
        tvLast = findViewById(R.id.tvLast)

        val today = SimpleDateFormat("EEEE, d MMMM", Locale.getDefault()).format(Date())
        tvDate.text = today.uppercase(Locale.getDefault())
        tvGreeting.text = "$part,\n$name"

        findViewById<android.view.View>(R.id.tileMood)?.setOnClickListener {
            startActivity(Intent(this, MoodActivity::class.java))
        }
        findViewById<android.view.View>(R.id.tileSleep)?.setOnClickListener {
            startActivity(Intent(this, SleepActivity::class.java))
        }
        findViewById<android.view.View>(R.id.tileMeditate)?.setOnClickListener {
            startActivity(Intent(this, MeditateActivity::class.java))
        }
        findViewById<android.view.View>(R.id.tileAppointment)?.setOnClickListener {
            showSessions()
        }
    }

    override fun onResume() {
        super.onResume()
        val (mood, emoji) = dataManager.getLastMood()
        tvLast.text = "Last check-in: $emoji $mood"
    }

    private fun showSessions() {
        val slots = arrayOf(
            "Dr. Maya Sharma, Tuesday 10:00 AM",
            "David Vance, Wednesday 4:30 PM",
            "Dr. Aisha Patel, Friday 11:15 AM"
        )
        AlertDialog.Builder(this)
            .setTitle("Book a Counselor Session")
            .setItems(slots) { _, which ->
                Toast.makeText(this, "Booked: ${slots[which]}", Toast.LENGTH_LONG).show()
            }
            .setNegativeButton("Cancel", null)
            .show()
    }
}

