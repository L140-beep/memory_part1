package com.example.makelayout

import android.app.ActionBar
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val xSize = 4
        val ySize = 3

        val cover = R.drawable.taro
        val cards = listOf(R.drawable.cat, R.drawable.squarecat)
        val tags = listOf("car", "squarecat", "petrushin")
        val layout = LinearLayout(applicationContext)
        layout.orientation = LinearLayout.VERTICAL

        val params = LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT)
        params.weight = 1.toFloat() // единичный вес


        val views: Array<ImageView> = Array<ImageView>(xSize * ySize) {
            (ImageView(applicationContext).apply {
                setImageResource(R.drawable.squarecat)
                layoutParams = params
                tag = "cat"
            })
        }
        val covers = Array(xSize * ySize) { true }
        val field = Array(xSize * ySize) { 0 }
        var indexes = mutableListOf<Int>()

        for (i in 0 until  xSize * ySize / 2) {
            var picture = Random.nextInt(cards.size)

            var index = Random.nextInt(xSize * ySize)
            var index1 = Random.nextInt(xSize * ySize)

            while(indexes.contains(index)){
                index = Random.nextInt(xSize * ySize)
            }
            indexes.add(index)

            while(indexes.contains(index1)){
                index1 = Random.nextInt(xSize * ySize)
            }
            indexes.add(index1)

            field[index] = cards[picture]
            field[index1] = cards[picture]

            views[index] = (ImageView(applicationContext).apply {
                    setImageResource(cover)
                    layoutParams = params
                    tag = tags[picture]
                    setOnClickListener {
                        if (covers[index]){
                            println(field[index])
                            setImageResource(field[index])

                        }
                        else{
                            setImageResource(cover)
                        }
                        println("here")
                        covers[index] = !covers[index]
                    }
            })


            views[index1] = (ImageView(applicationContext).apply {
                setImageResource(cover)
                layoutParams = params
                tag = tags[picture]
            })

            views[index1].setOnClickListener(View.OnClickListener {
                if (covers[index1]){
                    views[index1].setImageResource(field[index1])
                }
                else{
                    views[index1].setImageResource(cover)
                }
                covers[index1] = !covers[index1]
            })


        }
        val rows = Array(xSize, { LinearLayout(applicationContext)})
        var count = 0
        for (view in views) {
            val row: Int = count / xSize
            rows[row].addView(view)
            count ++
        }
        for (row in rows) {
            layout.addView(row)
        }

        setContentView(layout)
    }
}