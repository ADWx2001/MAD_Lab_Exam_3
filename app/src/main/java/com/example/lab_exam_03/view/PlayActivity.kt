package com.example.lab_exam_03.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import com.example.lab_exam_03.MainActivity
import com.example.lab_exam_03.R

class PlayActivity : AppCompatActivity() {
    private lateinit var playBtn :ImageView
    private lateinit var scoreBtn:Button
    private lateinit var quitBtn:Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_play)

        val action = supportActionBar
        action?.hide()
        playBtn = findViewById(R.id.playBtn)
        playBtn.setOnClickListener {
            startActivity(Intent(this@PlayActivity,MainActivity::class.java))
        }

        action?.hide()
        scoreBtn = findViewById(R.id.viewScoreBtn)
        scoreBtn.setOnClickListener {
            startActivity(Intent(this@PlayActivity,HighScore::class.java))
        }

        action?.hide()
        quitBtn = findViewById(R.id.extBtn)
        quitBtn.setOnClickListener {
            startActivity(Intent(this@PlayActivity,HighScore::class.java))
        }

        quitBtn.setOnClickListener {
            finishAffinity()
            System.exit(0)
        }

    }
}