package com.angelsony.lab1a

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.animation.DecelerateInterpolator
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

/**
 * Main Activity: Navigation Hub to demonstrate and evaluate all 5 Android Layouts.
 * Domain: MindWell Healthcare & Mental Wellbeing Companion.
 * Includes glassmorphism design and smooth staggered entrance animations.
 */
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val headerCard = findViewById<View>(R.id.headerCard)
        val cardConstraint = findViewById<View>(R.id.cardConstraint)
        val cardLinear = findViewById<View>(R.id.cardLinear)
        val cardRelative = findViewById<View>(R.id.cardRelative)
        val cardTable = findViewById<View>(R.id.cardTable)
        val cardFrame = findViewById<View>(R.id.cardFrame)

        val btnOpenConstraint = findViewById<Button>(R.id.btnOpenConstraint)
        val btnOpenLinear = findViewById<Button>(R.id.btnOpenLinear)
        val btnOpenRelative = findViewById<Button>(R.id.btnOpenRelative)
        val btnOpenTable = findViewById<Button>(R.id.btnOpenTable)
        val btnOpenFrame = findViewById<Button>(R.id.btnOpenFrame)

        // 1. ConstraintLayout
        btnOpenConstraint.setOnClickListener {
            it.bounceClick {
                startActivity(Intent(this, ConstraintLayoutActivity::class.java))
            }
        }

        // 2. LinearLayout
        btnOpenLinear.setOnClickListener {
            it.bounceClick {
                startActivity(Intent(this, LinearLayoutActivity::class.java))
            }
        }

        // 3. RelativeLayout
        btnOpenRelative.setOnClickListener {
            it.bounceClick {
                startActivity(Intent(this, RelativeLayoutActivity::class.java))
            }
        }

        // 4. TableLayout
        btnOpenTable.setOnClickListener {
            it.bounceClick {
                startActivity(Intent(this, TableLayoutActivity::class.java))
            }
        }

        // 5. FrameLayout
        btnOpenFrame.setOnClickListener {
            it.bounceClick {
                startActivity(Intent(this, FrameLayoutActivity::class.java))
            }
        }

        // Cascade Staggered Entrance Animations
        val cards = listOf(headerCard, cardConstraint, cardLinear, cardRelative, cardTable, cardFrame)
        cards.forEachIndexed { index, view ->
            view?.alpha = 0f
            view?.translationY = 50f
            view?.animate()
                ?.alpha(1f)
                ?.translationY(0f)
                ?.setDuration(500)
                ?.setStartDelay((index * 80).toLong())
                ?.setInterpolator(DecelerateInterpolator())
                ?.start()
        }
    }

    /** Tactile bounce feedback on button clicks */
    private fun View.bounceClick(onEnd: () -> Unit) {
        animate()
            .scaleX(0.94f)
            .scaleY(0.94f)
            .setDuration(90)
            .withEndAction {
                animate()
                    .scaleX(1f)
                    .scaleY(1f)
                    .setDuration(110)
                    .withEndAction { onEnd() }
                    .start()
            }
            .start()
    }
}
