package com.example.timecalculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val inputTime1 = findViewById<EditText>(R.id.inputTime1)
        val inputTime2 = findViewById<EditText>(R.id.inputTime2)
        val resultTextView = findViewById<TextView>(R.id.resultTextView)

        findViewById<Button>(R.id.buttonAdd).setOnClickListener {
            val time1 = inputTime1.text.toString()
            val time2 = inputTime2.text.toString()
            val result = addTime(time1, time2)
            resultTextView.text = result
        }

        findViewById<Button>(R.id.buttonSubtract).setOnClickListener {
            val time1 = inputTime1.text.toString()
            val time2 = inputTime2.text.toString()
            val result = subtractTime(time1, time2)
            resultTextView.text = result
        }
    }

    private fun timeToSeconds(time: String): Int {
        var totalSeconds = 0
        val regex = Regex("(\\d+)([hms])")
        val matches = regex.findAll(time)

        for (match in matches) {
            val value = match.groups[1]?.value?.toInt() ?: 0
            when (match.groups[2]?.value) {
                "h" -> totalSeconds += value * 3600
                "m" -> totalSeconds += value * 60
                "s" -> totalSeconds += value
            }
        }
        return totalSeconds
    }

    private fun secondsToTime(seconds: Int): String {
        val hours = seconds / 3600
        val minutes = (seconds % 3600) / 60
        val secs = seconds % 60

        val result = StringBuilder()
        if (hours > 0) result.append("${hours}h ")
        if (minutes > 0) result.append("${minutes}m ")
        if (secs > 0) result.append("${secs}s")

        return result.toString().trim()
    }

    private fun addTime(time1: String, time2: String): String {
        val totalSeconds = timeToSeconds(time1) + timeToSeconds(time2)
        return secondsToTime(totalSeconds)
    }

    private fun subtractTime(time1: String, time2: String): String {
        val totalSeconds = timeToSeconds(time1) - timeToSeconds(time2)
        return secondsToTime(totalSeconds.coerceAtLeast(0)) // Не допускаем отрицательное время
    }
}