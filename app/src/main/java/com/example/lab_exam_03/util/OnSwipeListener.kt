package com.example.lab_exam_03.util

import android.content.Context
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View

open class OnSwipeListener(context:Context?):View.OnTouchListener {

    var gestureDelector:GestureDetector

    override fun onTouch(v: View?, motionEvent: MotionEvent?): Boolean {
        return gestureDelector.onTouchEvent(motionEvent!!)
    }
    inner class GestureListener : GestureDetector.SimpleOnGestureListener(){

        val SWIPE_THRESOLD = 100
        val SWIPE_VELOCITY_THRESOLD = 100
        override fun onDown(e: MotionEvent): Boolean {
            return true
        }

        override fun onFling(
            e1: MotionEvent?,
            e2: MotionEvent,
            velocityX: Float,
            velocityY: Float
        ): Boolean {
            var result = false
            val yDiff = e2.y -e1!!.y
            val xDiff = e2.x -e1.x

            if(Math.abs(xDiff)>Math.abs(yDiff)){
                //means that we are either going to left or right direction and top to bottom direction
                if(Math.abs(xDiff)>SWIPE_THRESOLD && Math.abs(velocityX)>SWIPE_VELOCITY_THRESOLD){
                    if(xDiff > 0){
                        onSwipeRight()
                    }else{
                        onSwipeLeft()
                    }
                    result = true
                }
//                else if(Math.abs(yDiff)>SWIPE_THRESOLD && Math.abs(velocityY)>SWIPE_VELOCITY_THRESOLD){
//                    if(yDiff > 0){
//                        onSwipeTop()
//                    }else{
//                        onSwipeBottom()
//                    }
//                    result = true
//                }
            }else if(Math.abs(yDiff)>SWIPE_THRESOLD && Math.abs(velocityY)>SWIPE_VELOCITY_THRESOLD){
                if(yDiff > 0){
                    onSwipeTop()
                }else{
                    onSwipeBottom()
                }
                result = true
            }
            return result
        }
    }

    open fun onSwipeBottom() {
        TODO("Not yet implemented")
    }

    open fun onSwipeTop() {
        TODO("Not yet implemented")
    }

    open fun onSwipeLeft() {
        TODO("Not yet implemented")
    }

    open fun onSwipeRight() {
        TODO("Not yet implemented")
    }

    init {
        gestureDelector = GestureDetector(context,GestureListener())
    }
}