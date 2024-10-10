package com.example.soldii

import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import android.util.Pair
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.ContextCompat
import java.io.BufferedReader
import java.io.File
import java.io.FileReader


class SecondActivity: AppCompatActivity() {
    private var btn_bck: Button?=null
    private var textView: TextView?=null
    private val fileName = "theme.txt"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        applyThemeFromStorage()
        setContentView(R.layout.activity_second)
        btn_bck = findViewById(R.id.buttonleave)
        textView=findViewById(R.id.textView3)
        btn_bck!!.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            val options = ActivityOptions.makeSceneTransitionAnimation(
                this,
                Pair.create(btn_bck, "buttonStartTransition"),

            )
            startActivity(intent, options.toBundle())
        }
        updateThemeColors()
    }
    private fun applyThemeFromStorage() {
        val theme = readThemeFromStorage()
        AppCompatDelegate.setDefaultNightMode(
            if (theme == "dark") AppCompatDelegate.MODE_NIGHT_YES else AppCompatDelegate.MODE_NIGHT_NO
        )
    }

    private fun readThemeFromStorage(): String {
        val file = File(filesDir, fileName)
        return try {
            BufferedReader(FileReader(file)).use { it.readText() }
        } catch (e: Exception) {
            "light"
        }
    }

    private fun updateThemeColors() {
        val mode = AppCompatDelegate.getDefaultNightMode()
        val backgroundColor = if (mode == AppCompatDelegate.MODE_NIGHT_YES) {
            R.color.background_color_dark
        } else {
            R.color.background_color_light
        }

        val textColor = if (mode == AppCompatDelegate.MODE_NIGHT_YES) {
            R.color.button_text_color_dark
        } else {
            R.color.button_text_color_light
        }

        val buttonBackgroundColor = if (mode == AppCompatDelegate.MODE_NIGHT_YES) {
            R.color.button_background_color_dark
        } else {
            R.color.button_background_color_light
        }

        findViewById<androidx.constraintlayout.widget.ConstraintLayout>(R.id.main)?.setBackgroundColor(
            ContextCompat.getColor(this, backgroundColor)
        )

        textView?.setTextColor(ContextCompat.getColor(this, textColor))

        btn_bck?.backgroundTintList = ContextCompat.getColorStateList(this, buttonBackgroundColor)
        btn_bck?.setTextColor(ContextCompat.getColor(this, textColor))
        findViewById<androidx.constraintlayout.widget.ConstraintLayout>(R.id.second_activity)?.setBackgroundColor(
            ContextCompat.getColor(this, backgroundColor)
        )


    }
}