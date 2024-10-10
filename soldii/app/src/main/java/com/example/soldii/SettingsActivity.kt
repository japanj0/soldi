package com.example.soldii


import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.ContextCompat
import java.io.BufferedReader
import java.io.File
import java.io.FileReader
import java.io.FileWriter
import android.util.Pair



class SettingsActivity : AppCompatActivity() {
    private lateinit var btnSettings: Button
    private lateinit var leave_btn: Button
    private val fileName = "theme.txt"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_settings)

        btnSettings = findViewById(R.id.buttontheme)
        leave_btn = findViewById(R.id.leave)

        val theme = readThemeFromStorage()
        AppCompatDelegate.setDefaultNightMode(
            if(theme == "dark") AppCompatDelegate.MODE_NIGHT_YES
            else AppCompatDelegate.MODE_NIGHT_NO
        )

        updateColors()

        btnSettings.setOnClickListener {
            toggleTheme()
        }
        leave_btn.setOnClickListener {
                val intent = Intent(this, MainActivity::class.java)
                val options = ActivityOptions.makeSceneTransitionAnimation(
                    this,
                    Pair.create(leave_btn, "buttonStartTransition")
                )
                startActivity(intent, options.toBundle())

        }
    }



    private fun toggleTheme() {
        val currentMode = AppCompatDelegate.getDefaultNightMode()
        val newMode = if (currentMode == AppCompatDelegate.MODE_NIGHT_YES) {
            AppCompatDelegate.MODE_NIGHT_NO
        } else {
            AppCompatDelegate.MODE_NIGHT_YES

        }
        AppCompatDelegate.setDefaultNightMode(newMode)
        saveThemeToStorage(if (newMode == AppCompatDelegate.MODE_NIGHT_YES) "dark" else "light")

        updateColors()
    }

    private fun updateColors() {
        btnSettings = findViewById(R.id.buttontheme)
        val theme = readThemeFromStorage()
        if (theme=="dark") btnSettings.text = "light theme"
        else btnSettings.text = "dark theme"

        val mode = AppCompatDelegate.getDefaultNightMode()
        val backgroundColor = if (mode == AppCompatDelegate.MODE_NIGHT_YES) {
            R.color.background_color_dark

        } else {
            R.color.background_color_light
        }

        val buttonBackgroundColor = if (mode == AppCompatDelegate.MODE_NIGHT_YES){
            R.color.button_background_color_dark
        } else {
            R.color.button_background_color_light
        }

        val buttonTextColor = if (mode == AppCompatDelegate.MODE_NIGHT_YES) {
            R.color.button_text_color_dark
        } else {
            R.color.button_text_color_light
        }

        val constraintLayout = findViewById<androidx.constraintlayout.widget.ConstraintLayout>(R.id.second_activity)
        constraintLayout?.setBackgroundColor(ContextCompat.getColor(this, backgroundColor))

        btnSettings.setBackgroundColor(ContextCompat.getColor(this, buttonBackgroundColor))
        btnSettings.setTextColor(ContextCompat.getColor(this, buttonTextColor))

        leave_btn.setBackgroundColor(ContextCompat.getColor(this, buttonBackgroundColor))
        leave_btn.setTextColor(ContextCompat.getColor(this, buttonTextColor))
        findViewById<androidx.constraintlayout.widget.ConstraintLayout>(R.id.settings_activity)?.setBackgroundColor(
            ContextCompat.getColor(this, backgroundColor)
        )
    }

    private fun saveThemeToStorage(theme: String) {
        val file = File(filesDir, fileName)
        FileWriter(file).use { writer ->
            writer.write(theme)
        }
    }

    private fun readThemeFromStorage(): String {
        val file = File(filesDir, fileName)
        return try {
            BufferedReader(FileReader(file)).use { it.readText() }
        } catch (e: Exception) {
            "light"
        }
    }
}