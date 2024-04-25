package com.example.lab_exam_03

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.DisplayMetrics
import android.widget.GridLayout
import android.widget.ImageView
import android.widget.TextView
import com.example.lab_exam_03.util.OnSwipeListener
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var editor: SharedPreferences.Editor
    private val HIGH_SCORE_KEY = "high_score"
    private val PLAYER_NAME_KEY = "player_name"

    private val planets = intArrayOf(
        R.drawable.blueplanet,
        R.drawable.jupiterplanet,
        R.drawable.saturnplanet,
        R.drawable.plutoplanet,
        R.drawable.purpleplanet
    )

    private var widthOfBlock: Int = 0
    private val noOfBlock: Int = 8
    private var widthOfScreen: Int = 0
    private lateinit var planet: ArrayList<ImageView>
    private var planetTobeDragged: Int = 0
    private var planetTobeReplaced: Int = 0
    private val notPlanet: Int = R.drawable.transparent_drawable
    private lateinit var uHandler: Handler
    private lateinit var scoreResult: TextView
    private var score = 0
    private val interval = 100L

    //shared preference usage
    private var highestScore = 0
    private var playerName = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialize SharedPreferences
        sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        editor = sharedPreferences.edit()

        // Load highest score and player name from SharedPreferences
        highestScore = sharedPreferences.getInt(HIGH_SCORE_KEY, 0)
        playerName = sharedPreferences.getString(PLAYER_NAME_KEY, "") ?: ""

        // Example of updating highest score and player name
        if (score > highestScore) {
            highestScore = score
            playerName = "PlayerName" // Replace this with the actual player name
            // Save highest score and player name to SharedPreferences
            editor.putInt(HIGH_SCORE_KEY, highestScore)
            editor.putString(PLAYER_NAME_KEY, playerName)
            editor.apply()
        }


        scoreResult = findViewById(R.id.score)

        val displayMetrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(displayMetrics)
        widthOfScreen = displayMetrics.widthPixels

        widthOfBlock = widthOfScreen / noOfBlock
        planet = ArrayList()
        createBoard()

        for (imageView in planet) {
            imageView.setOnTouchListener(object : OnSwipeListener(this) {
                override fun onSwipeLeft() {
                    super.onSwipeLeft()
                    planetTobeDragged = imageView.id
                    planetTobeReplaced = planetTobeDragged - 1
                    planetInterChange()
                }

                override fun onSwipeRight() {
                    super.onSwipeRight()
                    planetTobeDragged = imageView.id
                    planetTobeReplaced = planetTobeDragged + 1
                    planetInterChange()
                }

                override fun onSwipeTop() {
                    super.onSwipeTop()
                    planetTobeDragged = imageView.id
                    planetTobeReplaced = planetTobeDragged - noOfBlock
                    planetInterChange()
                }

                override fun onSwipeBottom() {
                    super.onSwipeBottom()
                    planetTobeDragged = imageView.id
                    planetTobeReplaced = planetTobeDragged + noOfBlock
                    planetInterChange()
                }
            })
        }

        uHandler = Handler()
        startRepeat()
    }



    private fun planetInterChange() {
        val background: Int = planet[planetTobeReplaced].tag as Int
        val background1: Int = planet[planetTobeDragged].tag as Int

        planet[planetTobeDragged].setImageResource(background)
        planet[planetTobeReplaced].setImageResource(background1)

        planet[planetTobeDragged].tag = background
        planet[planetTobeReplaced].tag = background1
    }

    private fun checkRowForThree() {
        for (i in 0..61) {
            val chosenPlanet = planet[i].tag
            val isBlank: Boolean = planet[i].tag == notPlanet
            val notValid = arrayOf(6, 7, 14, 15, 22, 23, 30, 31, 38, 39, 46, 47, 54, 55)
            val list = notValid.toList()

            if (!list.contains(i)) {
                var x = i

                if (planet[x++].tag as Int == chosenPlanet && !isBlank
                    && planet[x++].tag as Int == chosenPlanet
                    && planet[x].tag as Int == chosenPlanet
                ) {
                    score += 3
                    scoreResult.text = "$score"
                    planet[x].setImageResource(notPlanet)
                    planet[x].tag = notPlanet
                    x--
                    planet[x].setImageResource(notPlanet)
                    planet[x].tag = notPlanet
                    x--
                    planet[x].setImageResource(notPlanet)
                    planet[x].tag = notPlanet
                }
            }
        }
        moveDownPlanets()
    }

    private fun checkColumnForThree() {
        for (i in 0..47) {
            val chosenPlanet = planet[i].tag
            val isBlank: Boolean = planet[i].tag == notPlanet

            var x = i

            if (planet[x].tag as Int == chosenPlanet && !isBlank
                && planet[x + noOfBlock].tag as Int == chosenPlanet
                && planet[x + 2 * noOfBlock].tag as Int == chosenPlanet
            ) {
                score += 3
                scoreResult.text = "$score"
                planet[x].setImageResource(notPlanet)
                planet[x].tag = notPlanet
                x += noOfBlock
                planet[x].setImageResource(notPlanet)
                planet[x].tag = notPlanet
                x += noOfBlock
                planet[x].setImageResource(notPlanet)
                planet[x].tag = notPlanet
            }
        }
        moveDownPlanets()
    }

    private fun moveDownPlanets() {
        val firstRow = intArrayOf(0, 1, 2, 3, 4, 5, 6, 7)
        val list = firstRow.toList()

        for (i in 55 downTo 0) {
            if (planet[i + noOfBlock].tag as Int == notPlanet) {
                planet[i + noOfBlock].setImageResource(planet[i].tag as Int)
                planet[i + noOfBlock].tag = planet[i].tag as Int

                planet[i].setImageResource(notPlanet)
                planet[i].tag = notPlanet

                if (list.contains(i) && planet[i].tag == notPlanet) {
                    val randomColor: Int = Math.abs((Math.random() * planets.size).toInt())
                    planet[i].setImageResource(planets[randomColor])
                    planet[i].tag = planets[randomColor]
                }
            }
        }
        for (i in 0..7) {
            if (planet[i].tag as Int == notPlanet) {
                val randomColor: Int = Math.abs((Math.random() * planets.size).toInt())
                planet[i].setImageResource(planets[randomColor])
                planet[i].tag = planets[randomColor]
            }
        }
    }

    private val repeatChecker: Runnable = object : Runnable {
        override fun run() {
            try {
                checkRowForThree()
                checkColumnForThree()
                moveDownPlanets()
            } finally {
                uHandler.postDelayed(this, interval)
            }
        }
    }

    private fun startRepeat() {
        repeatChecker.run()
    }

    private fun createBoard() {
        val gridLayout = findViewById<GridLayout>(R.id.board)
        gridLayout.rowCount = noOfBlock
        gridLayout.columnCount = noOfBlock
        gridLayout.layoutParams.width = widthOfScreen
        gridLayout.layoutParams.height = widthOfScreen

        for (i in 0 until noOfBlock * noOfBlock) {
            val imageView = ImageView(this)
            imageView.id = i
            imageView.layoutParams = android.view.ViewGroup.LayoutParams(widthOfBlock, widthOfBlock)

            imageView.maxHeight = widthOfBlock
            imageView.maxWidth = widthOfBlock

            val random: Int = (Math.random() * planets.size).toInt()
            imageView.setImageResource(planets[random])
            imageView.tag = planets[random]

            planet.add(imageView)
            gridLayout.addView(imageView)
        }
    }
}
