package com.angelsony.wellbeing

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.android.material.button.MaterialButton
import com.google.android.material.card.MaterialCardView
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

class HomeFragment : Fragment() {

    private lateinit var dataManager: DataManager
    private lateinit var tvHomeWaterCount: TextView
    private lateinit var pbHomeWater: ProgressBar
    private lateinit var tvLastMoodSub: TextView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dataManager = DataManager.getInstance(requireContext())

        val tvHomeDate = view.findViewById<TextView>(R.id.tvHomeDate)
        val tvHomeGreeting = view.findViewById<TextView>(R.id.tvHomeGreeting)
        tvHomeWaterCount = view.findViewById(R.id.tvHomeWaterCount)
        pbHomeWater = view.findViewById(R.id.pbHomeWater)
        tvLastMoodSub = view.findViewById(R.id.tvLastMoodSub)

        // Date & Greeting
        val dateFormat = SimpleDateFormat("EEEE, MMMM d", Locale.getDefault())
        tvHomeDate.text = dateFormat.format(Date()).uppercase(Locale.getDefault())

        val hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY)
        val greetingTime = when {
            hour < 12 -> "Good morning"
            hour < 17 -> "Good afternoon"
            else -> "Good evening"
        }
        val name = dataManager.getUserName()
        tvHomeGreeting.text = "$greetingTime,\n$name"

        // Last Mood Preview
        val (lastMood, lastEmoji) = dataManager.getLastMood()
        tvLastMoodSub.text = "$lastEmoji $lastMood"

        // Water Counter
        updateWaterUi()
        view.findViewById<MaterialButton>(R.id.btnQuickAddWater).setOnClickListener {
            val count = dataManager.incrementWater()
            updateWaterUi()
            Toast.makeText(requireContext(), "Added water: $count / 8 glasses 💧", Toast.LENGTH_SHORT).show()
        }

        // Tile Navigation to other tabs
        val mainActivity = activity as? MainActivity
        view.findViewById<MaterialCardView>(R.id.tileMood).setOnClickListener {
            mainActivity?.selectTab(R.id.menu_mood_journal)
        }
        view.findViewById<MaterialCardView>(R.id.tileSleep).setOnClickListener {
            mainActivity?.selectTab(R.id.menu_sleep_relax)
        }
        view.findViewById<MaterialCardView>(R.id.tileMeditate).setOnClickListener {
            mainActivity?.selectTab(R.id.menu_sleep_relax)
        }
        view.findViewById<MaterialCardView>(R.id.tileCalculator).setOnClickListener {
            mainActivity?.selectTab(R.id.menu_calculator)
        }
    }

    private fun updateWaterUi() {
        val count = dataManager.getWaterGlasses()
        tvHomeWaterCount.text = "$count / 8 Glasses"
        pbHomeWater.progress = count.coerceAtMost(8)
    }

    override fun onResume() {
        super.onResume()
        if (::dataManager.isInitialized) {
            val (lastMood, lastEmoji) = dataManager.getLastMood()
            tvLastMoodSub.text = "$lastEmoji $lastMood"
            updateWaterUi()
        }
    }
}

