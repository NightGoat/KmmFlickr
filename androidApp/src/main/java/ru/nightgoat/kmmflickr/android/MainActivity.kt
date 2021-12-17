package ru.nightgoat.kmmflickr.android

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.nightgoat.kmmflickr.Greeting

class MainActivity : AppCompatActivity() {
    private val greeting = Greeting()
    private val mainScope = MainScope()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val tv: TextView = findViewById(R.id.text_view)
        tv.text = "Loading..."

        mainScope.launch {
            kotlin.runCatching {
                withContext(Dispatchers.IO) {
                    greeting.greeting()
                }
            }.onSuccess {
                tv.text = it
            }.onFailure {
                tv.text = "Error: ${it.localizedMessage}"
            }
        }
    }
}
