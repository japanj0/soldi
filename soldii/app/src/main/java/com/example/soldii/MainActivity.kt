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
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import java.io.BufferedReader
import java.io.File
import java.io.FileReader

class MainActivity : AppCompatActivity() {
    private var go_btn: Button? = null
    private var textView: TextView? = null
    private var btn_settings: Button? = null
    private var btn_leave: Button? = null
    private val fileName = "theme.txt"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        applyThemeFromStorage()

        setContentView(R.layout.activity_main)

        go_btn = findViewById(R.id.buttonStart)
        textView = findViewById(R.id.textView)
        btn_settings = findViewById(R.id.buttonSettings)
        btn_leave = findViewById(R.id.buttonleave)
        btn_leave!!.setOnClickListener {
            finishAffinity()
        }

        btn_settings?.setOnClickListener {
            val intent = Intent(this, SettingsActivity::class.java)
            val options = ActivityOptions.makeSceneTransitionAnimation(
                this,
                Pair.create(go_btn, "buttonStartTransition")
            )
            startActivity(intent, options.toBundle())
        }

        go_btn?.setOnClickListener {
            val intent = Intent(this, SecondActivity::class.java)
            val options = ActivityOptions.makeSceneTransitionAnimation(
                this,
                Pair.create(go_btn, "buttonStartTransition")
            )
            startActivity(intent, options.toBundle())
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
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

        go_btn?.backgroundTintList = ContextCompat.getColorStateList(this, buttonBackgroundColor)
        go_btn?.setTextColor(ContextCompat.getColor(this, textColor))

        btn_settings?.backgroundTintList = ContextCompat.getColorStateList(this, buttonBackgroundColor)
        btn_settings?.setTextColor(ContextCompat.getColor(this, textColor))

        btn_leave?.backgroundTintList = ContextCompat.getColorStateList(this, buttonBackgroundColor)
        btn_leave?.setTextColor(ContextCompat.getColor(this, textColor))

    }
}
