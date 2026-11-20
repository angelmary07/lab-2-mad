package com.angelsony.wellbeing

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import android.animation.ValueAnimator
import android.app.TimePickerDialog
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.android.material.button.MaterialButton
import com.google.android.material.materialswitch.MaterialSwitch
import java.util.Calendar
import java.util.Locale

class SleepRelaxFragment : Fragment() {

    private lateinit var dataManager: DataManager
    private var bedHour = 23
    private var bedMinute = 0
    private var wakeHour = 7
    private var wakeMinute = 15
    private var sleepRating = 4

    // Breathing Animation & Timer
    private var breathingAnimator: ObjectAnimator? = null
    private var isBreathingRunning = false
    private var breathingSeconds = 0
    private var breathingPhaseIndex = 0
    private val breathingPhases = listOf(
        "Breathe in (4s)",
        "Hold breath (7s)",
        "Breathe out (8s)"
    )

    private val timerHandler = Handler(Looper.getMainLooper())
    private lateinit var tvBreathingTimer: TextView
    private lateinit var tvBreathingCircle: TextView
    private lateinit var btnStartBreathing: MaterialButton
    private lateinit var tvSoundStatus: TextView

    private var activeSoundName: String? = null

    private val timerRunnable = object : Runnable {
        override fun run() {
            breathingSeconds++
            val m = breathingSeconds / 60
            val s = breathingSeconds % 60
            tvBreathingTimer.text = String.format(Locale.getDefault(), "%02d:%02d", m, s)
            timerHandler.postDelayed(this, 1000)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_sleep_relax, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dataManager = DataManager.getInstance(requireContext())

        setupSleepSection(view)
        setupRelaxationSection(view)
    }

    private fun setupSleepSection(view: View) {
        val btnSelectBedtime = view.findViewById<MaterialButton>(R.id.btnSelectBedtime)
        val btnSelectWakeTime = view.findViewById<MaterialButton>(R.id.btnSelectWakeTime)
        val tvSleepDuration = view.findViewById<TextView>(R.id.tvSleepDuration)
        val tvSleepQualityTip = view.findViewById<TextView>(R.id.tvSleepQualityTip)
        val switchBedtimeReminder = view.findViewById<MaterialSwitch>(R.id.switchBedtimeReminder)
        val btnLogSleep = view.findViewById<MaterialButton>(R.id.btnLogSleep)

        fun updateDurationUi() {
            val bedMins = bedHour * 60 + bedMinute
            val wakeMins = wakeHour * 60 + wakeMinute
            val totalMins = (wakeMins - bedMins + 1440) % 1440
            val hours = totalMins / 60
            val mins = totalMins % 60

            tvSleepDuration.text = "$hours hours $mins mins"
            tvSleepQualityTip.text = when {
                hours >= 8 -> "Optimal sleep duration! Full restorative REM cycles achieved."
                hours >= 7 -> "Well rested. Keep this healthy rhythm going tonight."
                else -> "A little short. Aim for at least 7.5 to 8 hours tonight."
            }
        }

        btnSelectBedtime.setOnClickListener {
            TimePickerDialog(requireContext(), { _, h, m ->
                bedHour = h
                bedMinute = m
                btnSelectBedtime.text = formatTime(h, m)
                updateDurationUi()
            }, bedHour, bedMinute, false).show()
        }

        btnSelectWakeTime.setOnClickListener {
            TimePickerDialog(requireContext(), { _, h, m ->
                wakeHour = h
                wakeMinute = m
                btnSelectWakeTime.text = formatTime(h, m)
                updateDurationUi()
            }, wakeHour, wakeMinute, false).show()
        }

        // Star Rating Selection
        val stars = listOf(
            view.findViewById<ImageView>(R.id.star1),
            view.findViewById<ImageView>(R.id.star2),
            view.findViewById<ImageView>(R.id.star3),
            view.findViewById<ImageView>(R.id.star4),
            view.findViewById<ImageView>(R.id.star5)
        )

        for (i in stars.indices) {
            stars[i].setOnClickListener {
                sleepRating = i + 1
                for (j in stars.indices) {
                    stars[j].alpha = if (j <= i) 1.0f else 0.3f
                }
                val ratingName = when (sleepRating) {
                    5 -> "Excellent & Deep"
                    4 -> "Good & Refreshing"
                    3 -> "Fair"
                    2 -> "Restless"
                    else -> "Poor"
                }
                Toast.makeText(requireContext(), "Sleep Quality: $ratingName ($sleepRating/5 ⭐)", Toast.LENGTH_SHORT).show()
            }
        }

        switchBedtimeReminder.setOnCheckedChangeListener { _, isChecked ->
            dataManager.setBedtimeReminderEnabled(isChecked)
            val msg = if (isChecked) "Bedtime reminder active for 10:30 PM" else "Bedtime reminder muted"
            Toast.makeText(requireContext(), msg, Toast.LENGTH_SHORT).show()
        }

        btnLogSleep.setOnClickListener {
            val bedMins = bedHour * 60 + bedMinute
            val wakeMins = wakeHour * 60 + wakeMinute
            val totalMins = (wakeMins - bedMins + 1440) % 1440
            dataManager.saveSleepLog(
                btnSelectBedtime.text.toString(),
                btnSelectWakeTime.text.toString(),
                totalMins,
                sleepRating
            )
            Toast.makeText(
                requireContext(),
                "Sleep Logged: ${totalMins / 60}h ${totalMins % 60}m with $sleepRating/5 stars rating! 🌙",
                Toast.LENGTH_LONG
            ).show()
        }

        updateDurationUi()
    }

    private fun setupRelaxationSection(view: View) {
        tvBreathingCircle = view.findViewById(R.id.tvBreathingCircle)
        tvBreathingTimer = view.findViewById(R.id.tvBreathingTimer)
        btnStartBreathing = view.findViewById(R.id.btnStartBreathing)
        tvSoundStatus = view.findViewById(R.id.tvSoundStatus)

        // Pulsating ObjectAnimator
        breathingAnimator = ObjectAnimator.ofPropertyValuesHolder(
            tvBreathingCircle,
            PropertyValuesHolder.ofFloat("scaleX", 1f, 1.4f),
            PropertyValuesHolder.ofFloat("scaleY", 1f, 1.4f)
        ).apply {
            duration = 4500
            repeatCount = ValueAnimator.INFINITE
            repeatMode = ValueAnimator.REVERSE
            addListener(object : AnimatorListenerAdapter() {
                override fun onAnimationRepeat(animation: Animator) {
                    breathingPhaseIndex = (breathingPhaseIndex + 1) % breathingPhases.size
                    tvBreathingCircle.text = breathingPhases[breathingPhaseIndex]
                }
            })
        }

        btnStartBreathing.setOnClickListener {
            if (!isBreathingRunning) {
                startBreathingSession()
            } else {
                stopBreathingSession()
            }
        }

        // Ambient Sound Buttons
        val soundButtons = mapOf(
            R.id.btnSoundRain to "Gentle Rain on Leaves 🌧️",
            R.id.btnSoundOcean to "Deep Ocean Waves 🌊",
            R.id.btnSoundBell to "Zen Singing Bowl 🔔",
            R.id.btnSoundBirds to "Morning Forest Birds 🌲"
        )

        for ((id, soundName) in soundButtons) {
            view.findViewById<MaterialButton>(id)?.setOnClickListener {
                if (activeSoundName == soundName) {
                    activeSoundName = null
                    tvSoundStatus.text = "Ambient audio: Silent"
                    Toast.makeText(requireContext(), "Sound stopped", Toast.LENGTH_SHORT).show()
                } else {
                    activeSoundName = soundName
                    tvSoundStatus.text = "Now playing: $soundName (Calming 432Hz)"
                    Toast.makeText(requireContext(), "Playing $soundName", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun startBreathingSession() {
        isBreathingRunning = true
        breathingSeconds = 0
        breathingPhaseIndex = 0
        tvBreathingTimer.text = "00:00"
        tvBreathingCircle.text = "Breathe in (4s)"
        btnStartBreathing.text = "End Breathing Session"
        breathingAnimator?.start()
        timerHandler.postDelayed(timerRunnable, 1000)
        Toast.makeText(requireContext(), "Guided 4-7-8 Breathing started. Inhale gently...", Toast.LENGTH_SHORT).show()
    }

    private fun stopBreathingSession() {
        isBreathingRunning = false
        breathingAnimator?.cancel()
        timerHandler.removeCallbacks(timerRunnable)
        tvBreathingCircle.scaleX = 1f
        tvBreathingCircle.scaleY = 1f
        tvBreathingCircle.text = "Ready"
        btnStartBreathing.text = "Begin Breathing Cycle"

        val minsCompleted = (breathingSeconds / 60).coerceAtLeast(1)
        dataManager.addMeditationSession(minsCompleted)
        Toast.makeText(
            requireContext(),
            "Session complete! Mindful minutes added: +$minsCompleted min 🧘",
            Toast.LENGTH_LONG
        ).show()
    }

    private fun formatTime(h: Int, m: Int): String {
        val cal = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, h)
            set(Calendar.MINUTE, m)
        }
        return SimpleDateFormat("hh:mm a", Locale.getDefault()).format(cal.time)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        breathingAnimator?.cancel()
        timerHandler.removeCallbacks(timerRunnable)
    }
}

