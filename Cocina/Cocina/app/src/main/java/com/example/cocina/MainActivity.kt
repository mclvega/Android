package com.example.cocina

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AlertDialog
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONArray
import org.json.JSONException

class MainActivity : AppCompatActivity() ,JsonOrden.ObtenerLista.OntaskCompleted {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)



        Lista()


        sw_lista.setOnRefreshListener({
            Lista()
        })



    }


    fun Lista (){
        sw_lista.isRefreshing=true
        var tarea=JsonOrden.ObtenerLista("",this)
        tarea.execute("http://restaurantsigloxxi.somee.com/Servicios.svc/ListarOrdenesCocina")

    }
    override fun onTaskCompleted(response: String) {
        if (!response.equals("Error")) {
            try {

                var lista = ArrayList<Orden>()
                var jsonData = JSONArray(response)
                for (i in 0..jsonData.length() - 1) {

                    var orden = jsonData.getJSONObject(i)
                    var id = orden.getInt("Id")
                    var nombre = orden.getString("nombre_receta")
                    var mesa = orden.getInt("mesa").toByte()
                    var estado = orden.getString("descripcion_estado_orden")

                    lista.add(
                        Orden(
                            id,nombre,mesa,estado

                        )
                    )


                }

                var adap = CustomAdapterOrden(this, android.R.layout.simple_list_item_1, lista,lv_lista)
                lv_lista.adapter = adap
                sw_lista.isRefreshing=false
            } catch (e: JSONException) {
                Log.e("Json Error", "${e.message},,${e.cause}")
            }
        } else {
            var alerta = AlertDialog.Builder(this)
            alerta.setTitle("Algo Ocurrio!!")
            alerta.setMessage("Ocurrio un Error")
            alerta.setNegativeButton("Reintentar", { dialog, which -> Lista() })
            alerta.show()
        }





    }


}