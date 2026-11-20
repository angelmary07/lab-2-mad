package com.angelsony.wellbeing

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import android.animation.ValueAnimator
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import java.util.Locale

/** Screen 5: Breathing circle with a session timer in Kotlin. */
class MeditateActivity : AppCompatActivity() {

    private var anim: ObjectAnimator? = null
    private var running = false
    private var breatheIn = true
    private var seconds = 0

    private val handler = Handler(Looper.getMainLooper())
    private lateinit var tvTimer: TextView

    private val tick = object : Runnable {
        override fun run() {
            seconds++
            tvTimer.text = String.format(Locale.getDefault(), "%02d:%02d", seconds / 60, seconds % 60)
            handler.postDelayed(this, 1000)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_meditate)
        Ui.applyInsets(findViewById(R.id.root))

        WindowCompat.getInsetsController(window, window.decorView)
            .isAppearanceLightStatusBars = false

        val circle = findViewById<TextView>(R.id.tvCircle)
        val btnStart = findViewById<Button>(R.id.btnStart)
        tvTimer = findViewById(R.id.tvTimer)

        findViewById<android.view.View>(R.id.tvBack)?.setOnClickListener { finish() }

        anim = ObjectAnimator.ofPropertyValuesHolder(
            circle,
            PropertyValuesHolder.ofFloat("scaleX", 1f, 1.35f),
            PropertyValuesHolder.ofFloat("scaleY", 1f, 1.35f)
        ).apply {
            duration = 4000
            repeatCount = ValueAnimator.INFINITE
            repeatMode = ValueAnimator.REVERSE
            addListener(object : AnimatorListenerAdapter() {
                override fun onAnimationRepeat(a: Animator) {
                    breatheIn = !breatheIn
                    circle.text = if (breatheIn) "Breathe in" else "Breathe out"
                }
            })
        }

        btnStart.setOnClickListener {
            if (!running) {
                running = true
                breatheIn = true
                seconds = 0
                tvTimer.text = "00:00"
                circle.text = "Breathe in"
                btnStart.text = "End session"
                anim?.start()
                handler.postDelayed(tick, 1000)
            } else {
                stopSession(circle, btnStart)
            }
        }
    }

    private fun stopSession(circle: TextView, btnStart: Button) {
        running = false
        anim?.cancel()
        handler.removeCallbacks(tick)
        circle.scaleX = 1f
        circle.scaleY = 1f
        circle.text = "Ready"
        btnStart.text = "Begin"
    }

    override fun onDestroy() {
        super.onDestroy()
        anim?.cancel()
        handler.removeCallbacks(tick)
    }
}

