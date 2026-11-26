package com.angelsony.lab1a

import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import android.animation.ValueAnimator
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

/** Layout 5: FrameLayout Screen */
class FrameLayoutActivity : AppCompatActivity() {

    private var isRunning = false
    private var animator: ObjectAnimator? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_frame)

        findViewById<TextView>(R.id.tvBack).setOnClickListener { finish() }

        val tvCircle = findViewById<TextView>(R.id.tvBreathingState)
        val btnToggle = findViewById<Button>(R.id.btnToggleSession)
        val tvTimer = findViewById<TextView>(R.id.tvFrameTimer)

        animator = ObjectAnimator.ofPropertyValuesHolder(
            tvCircle,
            PropertyValuesHolder.ofFloat("scaleX", 1f, 1.35f),
            PropertyValuesHolder.ofFloat("scaleY", 1f, 1.35f)
        ).apply {
            duration = 3500
            repeatCount = ValueAnimator.INFINITE
            repeatMode = ValueAnimator.REVERSE
        }

        btnToggle.setOnClickListener {
            if (!isRunning) {
                isRunning = true
                animator?.start()
                btnToggle.text = "End Focus Session"
                tvTimer.text = "Active..."
                Toast.makeText(this, "FrameLayout stacked animation started!", Toast.LENGTH_SHORT).show()
            } else {
                isRunning = false
                animator?.cancel()
                tvCircle.scaleX = 1f
                tvCircle.scaleY = 1f
                btnToggle.text = "Begin Zen Focus Session"
                tvTimer.text = "00:00"
                Toast.makeText(this, "Focus session paused.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        animator?.cancel()
    }
}

