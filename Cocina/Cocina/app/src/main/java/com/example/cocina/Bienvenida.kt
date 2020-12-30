package com.example.cocina

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_bienvenida.*

class Bienvenida : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bienvenida)


        btn_ingresar.setOnClickListener({

            val intent = Intent(this, MainActivity::class.java)
            startActivityForResult(intent, 0)
        })
    }
}