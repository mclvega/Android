package com.example.cocina

import android.content.Context
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AlertDialog
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.view.*
import kotlinx.android.synthetic.main.item_ordenes.view.*
import org.json.JSONArray
import org.json.JSONException



class CustomAdapterOrden (var miContexto: Context,
                          var miRecurso:Int,
                          var miLista:ArrayList<Orden>,var listaCocina:ListView
): ArrayAdapter<Orden>(miContexto,miRecurso,miLista),JsonOrden.ObtenerLista.OntaskCompleted
{
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val item= LayoutInflater.from(miContexto).inflate(R.layout.item_ordenes,null)

        val nombre: TextView =item.findViewById(R.id.lbl_nombreOrden)!!
        val mesa: TextView =item.findViewById(R.id.lbl_mesaOrden)!!
        val estado: TextView =item.findViewById(R.id.lbl_estadoOrden)!!
        val btn_enProceso: Button =item.findViewById(R.id.btn_enProceso)!!
        val btn_Completado: Button =item.findViewById(R.id.btn_completado)!!
        val registro=miLista[position]
        nombre.text=registro.nombre
        mesa.text="Mesa "+registro.mesa.toString()
        estado.text=registro.estado

        if(registro.estado=="En Proceso"){
            btn_enProceso.visibility = View.INVISIBLE
        }
        btn_enProceso.setOnClickListener({

            val queque = Volley.newRequestQueue(miContexto)
            var addOrden = "http://restaurantsigloxxi.somee.com/Servicios.svc/actualizarestadoorden/${registro.id.toString()},2"

            val respuesta = StringRequest(addOrden, Response.Listener {
                Log.i("Response is", addOrden)
            }, Response.ErrorListener { error -> error.printStackTrace() })
            queque.add(respuesta)
            Toast.makeText(miContexto,"Estado Actualizado ", Toast.LENGTH_SHORT).show()

            var tarea=JsonOrden.ObtenerLista("",this)
            tarea.execute("http://restaurantsigloxxi.somee.com/Servicios.svc/ListarOrdenesCocina")
            Lista()
        })
        btn_Completado.setOnClickListener({
            val queque = Volley.newRequestQueue(miContexto)
            var addOrden = "http://restaurantsigloxxi.somee.com/Servicios.svc/actualizarestadoorden/${registro.id.toString()},3"

            val respuesta = StringRequest(addOrden, Response.Listener {
                Log.i("Response is", addOrden)
            }, Response.ErrorListener { error -> error.printStackTrace() })
            queque.add(respuesta)
            Toast.makeText(miContexto,"Estado Actualizado ", Toast.LENGTH_SHORT).show()
            var tarea=JsonOrden.ObtenerLista("",this)
            tarea.execute("http://restaurantsigloxxi.somee.com/Servicios.svc/ListarOrdenesCocina")
            Lista()
        })
        return item
    }

    fun Lista (){
        var tarea=JsonOrden.ObtenerLista("",this)
        tarea.execute("http://restaurantsigloxxi.somee.com/Servicios.svc/ListarOrdenesCocina")


    }
    override fun onTaskCompleted(response: String) {
        if (!response.equals("Error")) {
            try {

                val item2= LayoutInflater.from(miContexto).inflate(R.layout.activity_main,null)
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
                            id, nombre, mesa, estado

                        )
                    )
                }

                var adap = CustomAdapterOrden(miContexto, android.R.layout.simple_list_item_1, lista,listaCocina)
                listaCocina.lv_lista.adapter = adap

            }
            catch (e: JSONException) {
                Log.e("Json Error", "${e.message},,${e.cause}")
            }
        } else {
         /*   var alerta = AlertDialog.Builder(miContexto)
            alerta.setTitle("Algo Ocurrio!!")
            alerta.setMessage("Ocurrio un al Obtener la Lista de Ordenes")
            alerta.setNegativeButton("Reintentar", { dialog, which -> Lista() })
            alerta.show()

          */
            Lista()
        }
    }

}