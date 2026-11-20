package com.angelsony.wellbeing

import android.content.Context
import android.content.SharedPreferences

data class MoodEntry(
    val mood: String,
    val emoji: String,
    val note: String,
    val tags: List<String>,
    val timestamp: Long = System.currentTimeMillis()
)

data class JournalEntry(
    val id: String = System.currentTimeMillis().toString(),
    val title: String,
    val body: String,
    val moodEmoji: String = "🌿",
    val dateString: String,
    val isPrivate: Boolean = false,
    val hasVoiceNote: Boolean = false,
    val voiceDurationSeconds: Int = 0
)

data class CounselorAppointment(
    val id: String = System.currentTimeMillis().toString(),
    val specialistName: String,
    val specialty: String,
    val date: String,
    val timeSlot: String,
    val mode: String
)

data class TodoItem(
    val id: String = System.currentTimeMillis().toString(),
    val title: String,
    var isDone: Boolean = false,
    val priority: String = "Normal"
)

/**
 * Centralized Data Repository backed by SharedPreferences for persistence.
 * Stores user session, mood logs, journal entries, sleep sessions, habits & appointments.
 */
class DataManager private constructor(context: Context) {

    private val prefs: SharedPreferences =
        context.applicationContext.getSharedPreferences("mindwell_prefs", Context.MODE_PRIVATE)

    companion object {
        @Volatile
        private var instance: DataManager? = null

        fun getInstance(context: Context): DataManager {
            return instance ?: synchronized(this) {
                instance ?: DataManager(context).also { instance = it }
            }
        }
    }

    // --- User Session & Security ---
    fun saveUser(name: String, remember: Boolean) {
        prefs.edit()
            .putString("user_name", name)
            .putBoolean("remember_user", remember)
            .putBoolean("is_logged_in", true)
            .apply()
    }

    fun getUserName(): String = prefs.getString("user_name", "Friend") ?: "Friend"

    fun isRemembered(): Boolean = prefs.getBoolean("remember_user", false)

    fun getSecurityPin(): String = prefs.getString("security_pin", "1234") ?: "1234"

    fun setSecurityPin(pin: String) {
        prefs.edit().putString("security_pin", pin).apply()
    }

    // --- Water Habit Tracker ---
    fun getWaterGlasses(): Int = prefs.getInt("water_glasses", 5)

    fun setWaterGlasses(glasses: Int) {
        val clamped = glasses.coerceIn(0, 16)
        prefs.edit().putInt("water_glasses", clamped).apply()
    }

    fun incrementWater(): Int {
        val next = (getWaterGlasses() + 1).coerceAtMost(16)
        setWaterGlasses(next)
        return next
    }

    fun decrementWater(): Int {
        val prev = (getWaterGlasses() - 1).coerceAtLeast(0)
        setWaterGlasses(prev)
        return prev
    }

    // --- Mood Logs ---
    fun saveMood(mood: String, emoji: String, note: String, tags: List<String>) {
        prefs.edit()
            .putString("last_mood_name", mood)
            .putString("last_mood_emoji", emoji)
            .putString("last_mood_note", note)
            .putLong("last_mood_time", System.currentTimeMillis())
            .apply()
    }

    fun getLastMood(): Pair<String, String> {
        val mood = prefs.getString("last_mood_name", "Good") ?: "Good"
        val emoji = prefs.getString("last_mood_emoji", "😊") ?: "😊"
        return Pair(mood, emoji)
    }

    // --- Sleep Tracker ---
    fun saveSleepLog(bedtime: String, waketime: String, durationMinutes: Int, rating: Int) {
        prefs.edit()
            .putString("last_bedtime", bedtime)
            .putString("last_waketime", waketime)
            .putInt("last_sleep_mins", durationMinutes)
            .putInt("last_sleep_rating", rating)
            .apply()
    }

    fun getLastSleep(): Triple<String, String, Int> {
        val bed = prefs.getString("last_bedtime", "11:00 PM") ?: "11:00 PM"
        val wake = prefs.getString("last_waketime", "07:15 AM") ?: "07:15 AM"
        val mins = prefs.getInt("last_sleep_mins", 495) // 8h 15m default
        return Triple(bed, wake, mins)
    }

    // --- Bedtime Reminder ---
    fun isBedtimeReminderEnabled(): Boolean = prefs.getBoolean("bedtime_reminder_enabled", true)

    fun setBedtimeReminderEnabled(enabled: Boolean) {
        prefs.edit().putBoolean("bedtime_reminder_enabled", enabled).apply()
    }

    // --- Meditation Stats ---
    fun getMeditationStreak(): Int = prefs.getInt("meditation_streak", 4)

    fun getMeditationMinutes(): Int = prefs.getInt("meditation_minutes", 65)

    fun addMeditationSession(minutes: Int) {
        val total = getMeditationMinutes() + minutes
        prefs.edit().putInt("meditation_minutes", total).apply()
    }

    // --- Counselor Appointments ---
    fun getBookedAppointment(): String {
        return prefs.getString(
            "booked_appointment",
            "Dr. Maya Sharma — Tuesday, 10:00 AM (Video Call)"
        ) ?: "Dr. Maya Sharma — Tuesday, 10:00 AM (Video Call)"
    }

    fun setBookedAppointment(details: String) {
        prefs.edit().putString("booked_appointment", details).apply()
    }
}

