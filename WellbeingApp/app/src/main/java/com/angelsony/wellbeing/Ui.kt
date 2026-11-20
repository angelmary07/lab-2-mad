package com.angelsony.wellbeing

import android.view.View
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

object Ui {
    /**
     * Applies system bar insets (status bar & navigation bar) as padding to the given root view.
     */
    @JvmStatic
    fun applyInsets(view: View?) {
        if (view == null) return
        ViewCompat.setOnApplyWindowInsetsListener(view) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(
                systemBars.left,
                systemBars.top,
                systemBars.right,
                systemBars.bottom
            )
            insets
        }
    }
}

