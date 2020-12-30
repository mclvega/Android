package com.example.garson

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_bienvenida.*

class bienvenida : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bienvenida)


        btn_ingresar.setOnClickListener({

            val intent = Intent(this, MainActivity::class.java)
            startActivityForResult(intent, 0)
        })
    }
}