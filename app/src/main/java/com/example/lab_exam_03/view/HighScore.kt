package com.example.lab_exam_03.view

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import com.example.lab_exam_03.R

class HighScore : AppCompatActivity() {

    private lateinit var backBtn: Button
    private lateinit var scoreContainer: LinearLayout
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_high_score)

        // initialize SharedPreferences
        sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE)

        scoreContainer = findViewById(R.id.scrollerView)

        val action = supportActionBar
        action?.hide()
        backBtn = findViewById(R.id.goToPlyActivity)
        backBtn.setOnClickListener {
            startActivity(Intent(this@HighScore, PlayActivity::class.java))
        }

        displayScores()
    }

    private fun displayScores() {

        // Get all scores from SharedPreferences
        val allScores = sharedPreferences.all

        for ((playerName, playerScore) in allScores) {
            // Create a TextView for each player's name and score
            val playerNameView = TextView(this)
            playerNameView.text = getString(R.string.player_name, playerName)
            playerNameView.setTextColor(resources.getColor(R.color.white))

            val playerScoreView = TextView(this)
            playerScoreView.text = getString(R.string.score_display_score_activity, playerScore)
            playerScoreView.setTextColor(resources.getColor(R.color.white))

            // Add the TextViews to the container
            scoreContainer.addView(playerNameView)
            scoreContainer.addView(playerScoreView)
        }
    }
}
