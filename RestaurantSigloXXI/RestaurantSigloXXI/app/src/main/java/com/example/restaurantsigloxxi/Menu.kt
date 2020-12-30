package com.example.restaurantsigloxxi

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_menu.*
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.text.DecimalFormat
import java.util.*
import kotlin.collections.ArrayList


class  Menu : AppCompatActivity(),JsonInsumos.ObtenerLista.OntaskCompleted {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)


        sw_orden.isRefreshing=true
        val custom=BDD(this,"miBD",null,1)
        custom.Vaciar()
        val  numeroMesa=custom.NumeroMesa().toString()

        total()
        listaReceta()
        listaOrden()

        sw_orden.setOnRefreshListener{

            listaOrden()
            total()
        }

        sw_receta.setOnRefreshListener{
            listaReceta()
        }

        btn_bebestibles.setOnClickListener{
            listaRecetaUrl("http://restaurantsigloxxi.somee.com/Servicios.svc/ListarRecetasTipo/3")
        }

        btn_menu.setOnClickListener{
            listaRecetaUrl("http://restaurantsigloxxi.somee.com/Servicios.svc/ListarRecetasTipo/1")
        }

        btn_postre.setOnClickListener{
            listaRecetaUrl("http://restaurantsigloxxi.somee.com/Servicios.svc/ListarRecetasTipo/2")
        }

        btn_ordenar.setOnClickListener{

            if (custom.listarCarritoFinal().count() >= 1) {

                var alerta = AlertDialog.Builder(this)
                alerta.setTitle("Confirmación del pedido")
                alerta.setMessage("¿Está seguro de ordenar?")
                alerta.setNegativeButton("No", { dialog, which -> })
                alerta.setNeutralButton("Si", { dialog, which ->
                    if(custom.listarCarrito().count()>=1){
                        Toast.makeText(this, "Orden Enviada", Toast.LENGTH_SHORT).show()

                    }
                    val gson=Gson()
                    val json:String=gson.toJson(custom.listarCarrito())
                    val queque= Volley.newRequestQueue(this)
                    var url="http://restaurantsigloxxi.somee.com/Servicios.svc/AgregarOrden"
                    val jsonObject= JSONObject()
                    jsonObject.put("json",json)
                    jsonObject.put("mesa",numeroMesa)
                    val jsonObjectRequest= JsonObjectRequest(url,jsonObject,
                        Response.Listener { response ->
                            Toast.makeText(this,"Exito al agregar orden",Toast.LENGTH_SHORT).show()
                            Log.i("Response is",  url)
                        },Response.ErrorListener { error ->
                            error.printStackTrace()
                        }
                    )
                    queque.add(jsonObjectRequest)
                    val custom = BDD(this, "miBD", null, 1)
                    val t = custom.total()
                    val intent = Intent(this, Pago::class.java)
                    intent.putExtra("total", t.toString())
                    intent.putExtra("mesa",numeroMesa)
                    startActivityForResult(intent, 0)
                })
                alerta.show()

            } else {
                Toast.makeText(this, "Carrito vacio", Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun total (){
        val custom=BDD(this,"miBD",null,1)
        val t=custom.total()
        val tf=formato(custom.totalFinal())
        lbl_total.setText("Total a Pagar $ ${tf}")
    }
    fun formato(n:Int):String{
        var f= DecimalFormat("###,###")
        return f.format(n).replace(',','.')
    }
    fun listaReceta (){
            sw_receta.isRefreshing=true
            var tarea=JsonInsumos.ObtenerLista("",this)
            tarea.execute("http://restaurantsigloxxi.somee.com/Servicios.svc/ListarRecetasMenu")
            val custom=BDD(this,"miBD",null,1)
            val lista=custom.listarCarrito()
            var adap = CustomAdapterOrden(this, android.R.layout.simple_list_item_1,lista,rv_listaOrden,lbl_total)
            rv_listaOrden.adapter=adap
            custom.close()
    }

     fun listaRecetaUrl (url:String){
         sw_receta.isRefreshing=true
         var tarea=JsonInsumos.ObtenerLista("",this)
         tarea.execute(url)
         val custom=BDD(this,"miBD",null,1)
         val lista=custom.listarCarrito()
         var adap = CustomAdapterOrden(this, android.R.layout.simple_list_item_1,lista,rv_listaOrden,lbl_total)
         rv_listaOrden.adapter=adap
         custom.close()

     }

     fun listaOrden (){

         sw_orden.isRefreshing=true
        val custom=BDD(this,"miBD",null,1)
        val lista=custom.listarCarrito()
        var adap = CustomAdapterOrden(this, android.R.layout.simple_list_item_1,lista,rv_listaOrden,lbl_total)
        adap.notifyDataSetChanged()
        rv_listaOrden.adapter=adap
         sw_orden.isRefreshing=false
         custom.close()
    }
    override fun onTaskCompleted(response: String) {
            if (!response.equals("Error")) {
            try {


                var lista = ArrayList<Receta.Recetajson>()
                var jsonData = JSONArray(response)
                for (i in 0..jsonData.length() - 1) {

                    var insumo = jsonData.getJSONObject(i)
                    var id = insumo.getInt("Id").toShort()
                    var nombre = insumo.getString("Nombre")
                    var estado = insumo.getBoolean("Estado")
                    var precio = insumo.getInt("Precio")
                    var tipo = insumo.getInt("Tipo").toByte()
                    lista.add(Receta.Recetajson(id,nombre,precio,estado,tipo ))
                }
                var adap = CustomAdapterReceta(this, android.R.layout.simple_list_item_1, lista,rv_listaOrden,lbl_total)
                rv_listaRecetas.adapter = adap
                sw_receta.isRefreshing=false
            } catch (e: JSONException) {
                Log.e("Json Error", "${e.message},${e.cause}")
            }
        } else {
            /* var alerta = AlertDialog.Builder(this)
             alerta.setTitle("Problemas con el servidor")
             alerta.setMessage("Ocurrio un problema al conectar con el servidor")
             alerta.setNeutralButton("Reintentar ", { dialog, which -> listaReceta() })
             alerta.show()*/
            listaReceta()
        }
    }
 }
