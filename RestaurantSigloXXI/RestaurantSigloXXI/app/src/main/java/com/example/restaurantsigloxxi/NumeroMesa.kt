package com.example.restaurantsigloxxi

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import kotlinx.android.synthetic.main.activity_numero_mesa.*
import kotlinx.android.synthetic.main.activity_test.*
import org.json.JSONArray
import org.json.JSONException

class NumeroMesa : AppCompatActivity() ,JsonInsumos.ObtenerLista.OntaskCompleted {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_numero_mesa)


        val custom=BDD(this,"miBD",null,1)

        lbl_mesaActual.setText(custom.NumeroMesa().toString())

        var url = "http://restaurantsigloxxi.somee.com/Servicios.svc/ListarMesas"
        var tarea=JsonInsumos.ObtenerLista("",this)
        tarea.execute(url)

    }

    override fun onTaskCompleted(response: String) {
        if (!response.equals("Error")) {
            try {


                var lista : ArrayList<String> = ArrayList()
                var jsonData = JSONArray(response)
                for (i in 0..jsonData.length() - 1) {

                    var mesa = jsonData.getJSONObject(i)
                    var id = mesa.getInt("Id").toShort()
                    var numero = mesa.getInt("Numero").toShort()
                    var estado = mesa.getBoolean("Estado")
                    var capacidad = mesa.getInt("Capacidad").toByte()
                    lista.add("${numero}")

                }

                val custom=BDD(this,"miBD",null,1)

                spinner.adapter= ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,lista)

                btn_guardarNMesa.setOnClickListener {
                    custom.EditarNumeroMesa(spinner.selectedItem.toString().toInt())
                    custom.close()
                    onBackPressed()
                }
                btn_volverInicio.setOnClickListener {
                    onBackPressed()
                }
            } catch (e: JSONException) {
                Log.e("Json Error", "${e.message},${e.cause}")
            }
        } else {
            /* var alerta = AlertDialog.Builder(this)
             alerta.setTitle("Problemas con el servidor")
             alerta.setMessage("Ocurrio un problema al conectar con el servidor")
             alerta.setNeutralButton("Reintentar ", { dialog, which -> listaReceta() })
             alerta.show()*/
        }

    }
}