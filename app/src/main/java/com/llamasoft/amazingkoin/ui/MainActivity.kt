package com.llamasoft.amazingkoin.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.llamasoft.amazingkoin.R
import com.llamasoft.amazingkoin.app.App

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnChangeUrl = findViewById<Button>(R.id.btnChangeUrl)

        btnChangeUrl.setOnClickListener {
            App().updateApiUrl("http://10.28.21.39/AWESOME/")
        }
    }
}