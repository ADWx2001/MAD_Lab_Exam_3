package com.example.lab_exam_03

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.DisplayMetrics
import android.widget.GridLayout
import android.widget.ImageView
import android.widget.TextView
import com.example.lab_exam_03.util.OnSwipeListener
import java.util.Arrays.asList

class MainActivity : AppCompatActivity() {

    var planets = intArrayOf(
        R.drawable.blueplanet,
        R.drawable.jupiterplanet,
        R.drawable.saturnplanet,
        R.drawable.plutoplanet,
        R.drawable.purpleplanet
    )

    var widthOfBlock : Int = 0
    var noOfBlock :Int = 8
    var widthOfScreen: Int = 0
    lateinit var planet:ArrayList<ImageView>
    var planetTobeDragged : Int =0
    var planetTobeReplaced : Int = 0
    var notPlanet : Int = R.drawable.transparent_drawable
    lateinit var uHandler:Handler
    private lateinit var scoreResult:TextView
    var score = 0
    var interval = 100L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        scoreResult = findViewById(R.id.score)

        val dispalyMetrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(dispalyMetrics)
        widthOfScreen = dispalyMetrics.widthPixels
        var heightOfScreen = dispalyMetrics.heightPixels

        widthOfBlock = widthOfScreen / noOfBlock
        planet = ArrayList()
        createBoard()

        for(imageView in planet){

            imageView.setOnTouchListener(object :OnSwipeListener(this){
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

        var background : Int = planet.get(planetTobeReplaced).tag as Int
        var background1 : Int = planet.get(planetTobeDragged).tag as Int

        planet.get(planetTobeDragged).setImageResource(background)
        planet.get(planetTobeReplaced).setImageResource(background1)

        planet.get(planetTobeDragged).setTag(background)
        planet.get(planetTobeReplaced).setTag(background1)

    }

    private fun checkRowForThree(){
        for(i in 0..61){
            var chosedPlanet = planet.get(i).tag
            var isBlank : Boolean = planet.get(i).tag == notPlanet
            val notValid = arrayOf(6,7,14,15,22,23,30,31,38,39,46,47,54,55)
            var list = asList(*notValid)

            if (!list.contains(i)){
                var x = i

                if(planet.get(x++).tag as Int == chosedPlanet && !isBlank
                    && planet.get(x++).tag as Int == chosedPlanet
                    && planet.get(x).tag as Int == chosedPlanet){

                    score = score + 3
                    scoreResult.text = "$score"
                    planet.get(x).setImageResource(notPlanet)
                    planet.get(x).setTag(notPlanet)
                    x--
                    planet.get(x).setImageResource(notPlanet)
                    planet.get(x).setTag(notPlanet)
                    x--
                    planet.get(x).setImageResource(notPlanet)
                    planet.get(x).setTag(notPlanet)

                }
            }
        }
        moveDownPlanets()
    }
    private fun checkColumnForThree(){
        for(i in 0..47){
            var chosedPlanet = planet.get(i).tag
            var isBlank : Boolean = planet.get(i).tag == notPlanet


            var x = i

                if(planet.get(x).tag as Int == chosedPlanet && !isBlank
                    && planet.get(x+noOfBlock).tag as Int == chosedPlanet
                    && planet.get(x+2*noOfBlock).tag as Int == chosedPlanet){

                    score = score + 3
                    scoreResult.text = "$score"
                    planet.get(x).setImageResource(notPlanet)
                    planet.get(x).setTag(notPlanet)
                    x = x + noOfBlock
                    planet.get(x).setImageResource(notPlanet)
                    planet.get(x).setTag(notPlanet)
                    x = x + noOfBlock
                    planet.get(x).setImageResource(notPlanet)
                    planet.get(x).setTag(notPlanet)

                }

        }
        moveDownPlanets()
    }

    private fun moveDownPlanets() {

        val firstRow = arrayOf(1,2,3,4,5,6,7)
        val list = asList(*firstRow)
        for(i in 55 downTo 0){
            if (planet.get(i+noOfBlock).tag as Int == notPlanet){

                planet.get(i+noOfBlock).setImageResource(planet.get(i).tag as Int)
                planet.get(i+noOfBlock).setTag(planet.get(i).tag as Int)

                planet.get(i).setImageResource(notPlanet)
                planet.get(i).setTag(notPlanet)
                if(list.contains(i) && planet.get(i).tag == notPlanet){
                    var randomColor : Int = Math.abs((Math.random() * planets.size).toInt())
                    planet.get(i).setImageResource(planets[randomColor])
                    planet.get(i).setTag(planets[randomColor])
                }
            }
        }
        for(i in 0..7){
            if(planet.get(i).tag as Int == notPlanet){
                var randomColor : Int = Math.abs((Math.random() * planets.size).toInt())
                planet.get(i).setImageResource(planets[randomColor])
                planet.get(i).setTag(planets[randomColor])
            }
        }
    }
    val repeatChecker :Runnable = object : Runnable{
        override fun run() {
            try {
                checkRowForThree()
                checkColumnForThree()
                moveDownPlanets()
            }finally {
                uHandler.postDelayed(this,interval)
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

        for (i in 0 until noOfBlock*noOfBlock){
            val imageView = ImageView(this)
            imageView.id = i
            imageView.layoutParams = android.view.ViewGroup.LayoutParams(widthOfBlock,widthOfBlock)

            imageView.maxHeight = widthOfBlock
            imageView.maxWidth = widthOfBlock

            var random : Int = Math.floor(Math.random() * planets.size).toInt()
            //random index from planet array
            imageView.setImageResource(planets[random])
            imageView.setTag(planets[random])

            planet.add(imageView)
            gridLayout.addView(imageView)


        }
    }
}