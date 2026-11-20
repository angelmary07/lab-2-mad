package com.angelsony.wellbeing

import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.google.android.material.button.MaterialButton
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class MoodJournalFragment : Fragment() {

    private lateinit var dataManager: DataManager
    private var selectedMood = "Good"
    private var selectedEmoji = "😊"
    private var isRecordingVoice = false
    private var recordedVoiceDuration = 0
    private var isVaultUnlocked = false

    private val journalEntries = mutableListOf(
        JournalEntry(
            title = "Morning Gratitude & Sunshine",
            body = "Took a 15-minute walk under the soft morning sun. Felt deeply peaceful and ready for the day's projects.",
            moodEmoji = "😌",
            dateString = "Today, 08:30 AM",
            isPrivate = false,
            hasVoiceNote = true,
            voiceDurationSeconds = 45
        ),
        JournalEntry(
            title = "Overcoming the Midday Block",
            body = "Felt slightly overwhelmed with assignment deadlines, but 4-7-8 breathing reset my focus completely.",
            moodEmoji = "😊",
            dateString = "Yesterday, 04:15 PM",
            isPrivate = false,
            hasVoiceNote = false
        ),
        JournalEntry(
            title = "Private Reflection & Future Dreams",
            body = "Reflecting on personal goals for the upcoming semester and keeping a calm, resilient mindset.",
            moodEmoji = "🤩",
            dateString = "Nov 18, 09:40 PM",
            isPrivate = true,
            hasVoiceNote = false
        )
    )

    private lateinit var layoutJournalList: LinearLayout
    private lateinit var tvVoiceStatus: TextView
    private lateinit var btnRecordVoice: MaterialButton
    private lateinit var btnTogglePinLock: MaterialButton

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_mood_journal, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dataManager = DataManager.getInstance(requireContext())

        setupMoodSelectors(view)
        setupJournalSection(view)
    }

    private fun setupMoodSelectors(view: View) {
        val emojis = mapOf(
            R.id.btnMoodRadiant to Pair("Radiant", "🤩"),
            R.id.btnMoodHappy to Pair("Good", "😊"),
            R.id.btnMoodCalm to Pair("Calm", "😌"),
            R.id.btnMoodNeutral to Pair("Neutral", "😐"),
            R.id.btnMoodLow to Pair("Low", "😔"),
            R.id.btnMoodStressed to Pair("Stressed", "😣")
        )

        for ((btnId, pair) in emojis) {
            view.findViewById<View>(btnId)?.setOnClickListener {
                selectedMood = pair.first
                selectedEmoji = pair.second
                highlightSelectedMood(view, btnId)
                Toast.makeText(requireContext(), "Selected mood: ${pair.second} ${pair.first}", Toast.LENGTH_SHORT).show()
            }
        }

        val cbSleep = view.findViewById<CheckBox>(R.id.cbTagSleep)
        val cbExercise = view.findViewById<CheckBox>(R.id.cbTagExercise)
        val cbWork = view.findViewById<CheckBox>(R.id.cbTagWork)
        val cbSocial = view.findViewById<CheckBox>(R.id.cbTagSocial)
        val etMoodNote = view.findViewById<EditText>(R.id.etMoodNote)
        val tvMoodTrendSummary = view.findViewById<TextView>(R.id.tvMoodTrendSummary)

        view.findViewById<MaterialButton>(R.id.btnSaveMood).setOnClickListener {
            val tags = mutableListOf<String>()
            if (cbSleep.isChecked) tags.add("Sleep")
            if (cbExercise.isChecked) tags.add("Exercise")
            if (cbWork.isChecked) tags.add("Work")
            if (cbSocial.isChecked) tags.add("Social")

            val note = etMoodNote.text.toString().trim()
            dataManager.saveMood(selectedMood, selectedEmoji, note, tags)

            tvMoodTrendSummary.text = "Latest: $selectedEmoji $selectedMood (${tags.joinToString(", ").ifEmpty { "General" }})\n" +
                    "Dominant Pattern: 85% Positive/Calm • 6-day streak maintained!"

            etMoodNote.text?.clear()
            Toast.makeText(
                requireContext(),
                "Saved mood: $selectedEmoji $selectedMood logged successfully!",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    private fun highlightSelectedMood(root: View, selectedId: Int) {
        val buttons = listOf(
            R.id.btnMoodRadiant,
            R.id.btnMoodHappy,
            R.id.btnMoodCalm,
            R.id.btnMoodNeutral,
            R.id.btnMoodLow,
            R.id.btnMoodStressed
        )
        for (id in buttons) {
            val v = root.findViewById<View>(id)
            if (id == selectedId) {
                v?.alpha = 1.0f
                v?.scaleX = 1.1f
                v?.scaleY = 1.1f
            } else {
                v?.alpha = 0.6f
                v?.scaleX = 1.0f
                v?.scaleY = 1.0f
            }
        }
    }

    private fun setupJournalSection(view: View) {
        layoutJournalList = view.findViewById(R.id.layoutJournalList)
        tvVoiceStatus = view.findViewById(R.id.tvVoiceStatus)
        btnRecordVoice = view.findViewById(R.id.btnRecordVoice)
        btnTogglePinLock = view.findViewById(R.id.btnTogglePinLock)

        val etJournalTitle = view.findViewById<EditText>(R.id.etJournalTitle)
        val etJournalBody = view.findViewById<EditText>(R.id.etJournalBody)
        val cbPrivateEntry = view.findViewById<CheckBox>(R.id.cbPrivateEntry)
        val etSearchJournal = view.findViewById<EditText>(R.id.etSearchJournal)

        // PIN Lock Dialog Toggle
        btnTogglePinLock.setOnClickListener {
            showPinDialog()
        }

        // Voice Note Simulation
        btnRecordVoice.setOnClickListener {
            toggleVoiceRecording()
        }

        // Save Journal Entry
        view.findViewById<MaterialButton>(R.id.btnSaveJournal).setOnClickListener {
            val title = etJournalTitle.text.toString().trim()
            val body = etJournalBody.text.toString().trim()

            if (title.isEmpty()) {
                etJournalTitle.error = "Please enter an entry title"
                Toast.makeText(requireContext(), "Entry title is required", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (body.isEmpty()) {
                etJournalBody.error = "Please enter some reflection thoughts"
                Toast.makeText(requireContext(), "Journal body is required", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val dateStr = SimpleDateFormat("MMM d, hh:mm a", Locale.getDefault()).format(Date())
            val newEntry = JournalEntry(
                title = title,
                body = body,
                moodEmoji = selectedEmoji,
                dateString = dateStr,
                isPrivate = cbPrivateEntry.isChecked,
                hasVoiceNote = recordedVoiceDuration > 0,
                voiceDurationSeconds = recordedVoiceDuration
            )

            journalEntries.add(0, newEntry)
            renderJournalEntries(etSearchJournal.text.toString())

            etJournalTitle.text?.clear()
            etJournalBody.text?.clear()
            cbPrivateEntry.isChecked = false
            recordedVoiceDuration = 0
            tvVoiceStatus.text = "No voice attached"
            btnRecordVoice.text = "Record Voice Note"

            Toast.makeText(requireContext(), "Journal entry saved with $selectedEmoji reflection!", Toast.LENGTH_SHORT).show()
        }

        // Live Search Filter
        etSearchJournal.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                renderJournalEntries(s?.toString() ?: "")
            }
            override fun afterTextChanged(s: Editable?) {}
        })

        renderJournalEntries("")
    }

    private fun toggleVoiceRecording() {
        if (!isRecordingVoice) {
            isRecordingVoice = true
            btnRecordVoice.text = "Stop Recording ⏹️"
            tvVoiceStatus.text = "🔴 Recording in progress... 0:03"
            Toast.makeText(requireContext(), "Recording voice note...", Toast.LENGTH_SHORT).show()

            Handler(Looper.getMainLooper()).postDelayed({
                if (isRecordingVoice) {
                    isRecordingVoice = false
                    recordedVoiceDuration = 28
                    btnRecordVoice.text = "Record Again"
                    tvVoiceStatus.text = "🎙️ Voice note attached (0:28)"
                    Toast.makeText(requireContext(), "Voice note recorded successfully (28s)", Toast.LENGTH_SHORT).show()
                }
            }, 3000)
        } else {
            isRecordingVoice = false
            recordedVoiceDuration = 15
            btnRecordVoice.text = "Record Again"
            tvVoiceStatus.text = "🎙️ Voice note attached (0:15)"
        }
    }

    private fun showPinDialog() {
        val dialogView = LayoutInflater.from(requireContext()).inflate(R.layout.dialog_pin_lock, null)
        val etPin = dialogView.findViewById<EditText>(R.id.etPinInput)
        val tvError = dialogView.findViewById<TextView>(R.id.tvPinError)
        val dialog = AlertDialog.Builder(requireContext())
            .setView(dialogView)
            .create()

        dialogView.findViewById<MaterialButton>(R.id.btnSubmitPin).setOnClickListener {
            val inputPin = etPin.text.toString()
            val correctPin = dataManager.getSecurityPin()

            if (inputPin == correctPin) {
                isVaultUnlocked = !isVaultUnlocked
                btnTogglePinLock.text = if (isVaultUnlocked) "Vault: Unlocked" else "Vault: Locked"
                dialog.dismiss()
                renderJournalEntries("")
                val msg = if (isVaultUnlocked) "Private vault unlocked!" else "Private vault locked!"
                Toast.makeText(requireContext(), msg, Toast.LENGTH_SHORT).show()
            } else {
                tvError.visibility = View.VISIBLE
                tvError.text = "Incorrect PIN! (Default: 1234)"
            }
        }

        dialogView.findViewById<MaterialButton>(R.id.btnCancelPin).setOnClickListener {
            dialog.dismiss()
        }

        dialog.show()
    }

    private fun renderJournalEntries(query: String) {
        layoutJournalList.removeAllViews()
        val filtered = journalEntries.filter {
            it.title.contains(query, ignoreCase = true) ||
                    it.body.contains(query, ignoreCase = true) ||
                    it.moodEmoji.contains(query)
        }

        for (entry in filtered) {
            val card = LinearLayout(requireContext()).apply {
                orientation = LinearLayout.VERTICAL
                setPadding(32, 28, 32, 28)
                setBackgroundResource(R.drawable.bg_card_rounded)
                val params = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
                ).apply {
                    setMargins(0, 0, 0, 24)
                }
                layoutParams = params
            }

            // Header Row: Mood Emoji + Title + Lock
            val headerRow = LinearLayout(requireContext()).apply {
                orientation = LinearLayout.HORIZONTAL
                gravity = android.view.Gravity.CENTER_VERTICAL
            }

            val tvEmoji = TextView(requireContext()).apply {
                text = entry.moodEmoji
                textSize = 20f
            }

            val tvTitle = TextView(requireContext()).apply {
                text = entry.title
                textSize = 15f
                setTextColor(Color.parseColor("#1F2421"))
                setTypeface(null, android.graphics.Typeface.BOLD)
                setPadding(16, 0, 0, 0)
                layoutParams = LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1f)
            }

            val tvDate = TextView(requireContext()).apply {
                text = entry.dateString
                textSize = 11f
                setTextColor(Color.parseColor("#8D968F"))
            }

            headerRow.addView(tvEmoji)
            headerRow.addView(tvTitle)
            headerRow.addView(tvDate)
            card.addView(headerRow)

            // Body or Locked Message
            val tvBody = TextView(requireContext()).apply {
                textSize = 13f
                setPadding(0, 12, 0, 0)
                if (entry.isPrivate && !isVaultUnlocked) {
                    text = "🔒 Private Entry — Locked with PIN. Tap PIN Lock above to view."
                    setTextColor(Color.parseColor("#C25E34"))
                } else {
                    text = entry.body
                    setTextColor(Color.parseColor("#59625D"))
                }
            }
            card.addView(tvBody)

            // Voice Note indicator if present
            if (entry.hasVoiceNote) {
                val tvVoice = TextView(requireContext()).apply {
                    text = "🎙️ Voice note (${entry.voiceDurationSeconds}s) • Tap to play audio"
                    textSize = 11f
                    setTextColor(Color.parseColor("#1565C0"))
                    setPadding(0, 8, 0, 0)
                    setOnClickListener {
                        Toast.makeText(requireContext(), "Playing audio note (${entry.voiceDurationSeconds}s) 🔊", Toast.LENGTH_SHORT).show()
                    }
                }
                card.addView(tvVoice)
            }

            layoutJournalList.addView(card)
        }
    }
}

