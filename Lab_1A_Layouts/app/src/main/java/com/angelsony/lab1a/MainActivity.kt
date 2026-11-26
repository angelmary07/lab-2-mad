package com.angelsony.lab1a

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

/**
 * Main Activity: Navigation Hub to demonstrate and evaluate all 5 Android Layouts.
 * Domain: MindWell Healthcare & Mental Wellbeing Companion.
 */
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // 1. ConstraintLayout
        findViewById<Button>(R.id.btnOpenConstraint).setOnClickListener {
            startActivity(Intent(this, ConstraintLayoutActivity::class.java))
        }

        // 2. LinearLayout
        findViewById<Button>(R.id.btnOpenLinear).setOnClickListener {
            startActivity(Intent(this, LinearLayoutActivity::class.java))
        }

        // 3. RelativeLayout
        findViewById<Button>(R.id.btnOpenRelative).setOnClickListener {
            startActivity(Intent(this, RelativeLayoutActivity::class.java))
        }

        // 4. TableLayout
        findViewById<Button>(R.id.btnOpenTable).setOnClickListener {
            startActivity(Intent(this, TableLayoutActivity::class.java))
        }

        // 5. FrameLayout
        findViewById<Button>(R.id.btnOpenFrame).setOnClickListener {
            startActivity(Intent(this, FrameLayoutActivity::class.java))
        }
    }
}
