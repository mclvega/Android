package com.example.restaurantsigloxxi

import android.app.Dialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.View.inflate
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_menu.*
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val custom=BDD(this,"miBD",null,1)
        custom.InsertaMesa(1)

        btn_numeroMesa.setOnClickListener {

            val intent = Intent(this, NumeroMesa::class.java)

            startActivityForResult(intent, 0)

        }
        btn_ingresar.setOnClickListener{

            val intent = Intent(this, Menu::class.java)

            startActivityForResult(intent, 0)

            custom.Vaciar()
            custom.VaciarFinal()
            custom.close()
        }





    }


}