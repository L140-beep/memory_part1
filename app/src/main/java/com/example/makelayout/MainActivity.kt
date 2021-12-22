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

class MainActivity : AppCompatActivity() {

    var  firstCard: View = View(this)
    var  openCardsCount = 0 // число открытых карт
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val layout = LinearLayout(applicationContext)
        layout.orientation = LinearLayout.VERTICAL

        val params = LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT)
        params.weight = 1.toFloat() // единичный вес

        // TODO: 3) реализовать переворот карт с "рубашки" на лицевую сторону и обратно
        val colorListener = View.OnClickListener() {
            when (openCardsCount) {
                0 -> { // перевернуть карту
                    firstCard = it; openCardsCount++; it.isClickable = false
                }
                1 -> {
                    // проверить, совпадает ли перевёрнутая сейчас карта
                    if (it.tag == firstCard.tag) {
                        it.visibility = View.INVISIBLE; it.isClickable = false
                        firstCard.visibility = View.INVISIBLE; firstCard.isClickable = false
                    } else {
                        // карту перевернуть
                        // подождать, вернуть обратно обе карты
                        // не забыть включить карты it.isClickable = true
                        it.visibility = View.VISIBLE; it.isClickable = true
                        firstCard.visibility = View.VISIBLE; firstCard.isClickable = true

                    }
                    openCardsCount = 2
                }
                // и перевёрнутая ранее
                else -> Log.d("mytag", "two cards are open already") // ничего не делаем
            }
           // запуск функции во внешнем потоке
            GlobalScope.launch (Dispatchers.Main)
                { setBackgroundWithDelay(it) }
            //it.setBackgroundColor(Color.YELLOW)

        }


        val catViews = ArrayList<ImageView>()

        // TODO: 2) случайным образом разместить 8 пар картинок

        for (i in 1..16) {
            catViews.add( // вызываем конструктор для создания нового ImageView
                    ImageView(applicationContext).apply {
                        setImageResource(R.drawable.squarecat)
                        layoutParams = params
                        tag = "cat" // TODO: указать тег в зависимости от картинки
                        setOnClickListener(colorListener)
                    })
        }
        val rows = Array(4, { LinearLayout(applicationContext)})
        var count = 0
        for (view in catViews) {
            val row: Int = count / 4
            rows[row].addView(view)
            count ++
        }
        for (row in rows) {
            layout.addView(row)
        }

        // TODO: 1) заполнить 4 строки элементами из массива catViews по 4 штуки в ряду
 /*
        val cat2 = ImageView(applicationContext)
        cat2.setImageResource(R.drawable.squarecat); cat2.layoutParams = params
        val cat3 = ImageView(applicationContext)
        cat3.setImageResource(R.drawable.squarecat)
        val cat4 = ImageView(applicationContext)
        //cat.layoutParams = ViewGroup.LayoutParams(applicationContext, )
        cat4.setImageResource(R.drawable.squarecat)



        val row1 = LinearLayout(applicationContext)
        row1.orientation = LinearLayout.HORIZONTAL
        row1.setBackgroundColor(Color.GRAY)
        row1.addView(cat2); row1.addView(cat);

        val row2 = LinearLayout(applicationContext)
        row2.orientation = LinearLayout.HORIZONTAL
        row2.setBackgroundColor(Color.GRAY)
        row2.addView(cat3); row2.addView(cat4);

        layout.addView(row1); layout.addView(row2)


  */
        setContentView(layout)
        //setContentView(R.layout.activity_main)
    }

    suspend fun setBackgroundWithDelay(v: View) {
        delay(1000)
        v.setBackgroundColor(Color.YELLOW)
        delay(1000)
        v.visibility = View.INVISIBLE
        v.isClickable = false
    }

    suspend fun openCards() {

    }
}