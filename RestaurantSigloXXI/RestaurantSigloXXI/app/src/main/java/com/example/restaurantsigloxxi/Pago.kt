package com.example.restaurantsigloxxi

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.HandlerCompat.postDelayed
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.activity_pago.*
import org.json.JSONArray
import org.json.JSONException
import java.sql.Date
import java.sql.Time
import java.text.DecimalFormat
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.time.Clock
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.collections.ArrayList

class Pago : AppCompatActivity() ,JsonInsumos.Pagar.Pagoo,JsonInsumos.ObtenerLista.OntaskCompleted{
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pago)



        listaCarrito()

        val EXECUTION_TIME: Long = 10000 // medio minuto
        var handler:Handler= Handler()
        handler.postDelayed(object : Runnable {
            override fun run() {
                listaOrden()
                handler.postDelayed(this, EXECUTION_TIME)
            }
        }, EXECUTION_TIME)


        val custom=BDD(this, "miBD", null, 1)
        val  numeroMesa=custom.NumeroMesa().toString()
        val total = custom.totalFinal()
        lbl_totalPago.setText("$" + formato(total))

        listaOrden()


        var url = "http://restaurantsigloxxi.somee.com/Servicios.svc/ListarOrdenesCocinaPorMesa/${custom.NumeroMesa()}"
        var tarea=JsonInsumos.ObtenerLista("", this)
        tarea.execute(url)

        sw_ordenFinal.setOnRefreshListener{
            listaOrden()
        }
        sw_carritoFinal.setOnRefreshListener{
            listaCarrito()
        }

        val hora=SimpleDateFormat("dd/MM/yyyy k:mm").format(Date())


        btn_pagar.setOnClickListener{


            if(dl_metodos.selectedItemPosition==1 || dl_metodos.selectedItemPosition==2 ){
                var alerta = AlertDialog.Builder(this)
                alerta.setTitle("Resumen del Pago")
                var listaPlatos=custom.listarCarritoFinal()
                var listaN=""
                for (item in listaPlatos){
                    listaN=listaN+" \n${item.Nombre} " +
                            "\n$${formato(item.Precio)}"

                }
                alerta.setMessage(
                                "**********************************************************" +
                                "\nOrdenes Realizadas:" +
                                "\n${listaN}" +
                                "\n" +
                                        "\nFecha : ${hora}" +
                                "\nMetodo de Pago : ${dl_metodos.selectedItem}" +
                                "\nTotal a Pagar : $${formato(total)}" +
                                        "\n**********************************************************")
                alerta.setNeutralButton("Pagar ", { dialog, which ->

                    // Toast.makeText(this,"Pago realizado",Toast.LENGTH_SHORT).show()

                    var url =
                        "http://restaurantsigloxxi.somee.com/Servicios.svc/ListarOrdenesSinPagarPorMesa/${numeroMesa}"
                    var tarea = JsonInsumos.Pagar("", this)
                    tarea.execute(url)

                    val custom = BDD(this, "miBD", null, 1)
                    custom.VaciarFinal()
                    custom.close()

                    Toast.makeText(this, "Pago realizado", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this, Menu::class.java)
                    intent.putExtra("mesa", custom.NumeroMesa())
                    startActivityForResult(intent, 0)
                })
                alerta.setNegativeButton("Cancelar", { dialog, which -> })
                alerta.show()
            }
            else{
                Toast.makeText(this, "Seleccione un metodo de pago", Toast.LENGTH_SHORT).show()

            }

        }

        btn_volver.setOnClickListener{


            var alerta = AlertDialog.Builder(this)
            alerta.setTitle("Confirmación de Acción")
            alerta.setMessage("¿Quieres volver a ordenar?")
            alerta.setNeutralButton("Si ", { dialog, which ->


                val custom = BDD(this, "miBD", null, 1)

                custom.Vaciar()
                onBackPressed()

                custom.close()
                val intent = Intent(this, Menu::class.java)

                intent.putExtra("mesa", custom.NumeroMesa())
                startActivityForResult(intent, 0)
            })
            alerta.setNegativeButton("No", { dialog, which -> })
            alerta.show()
        }

    }

    fun listaOrden (){

        val custom=BDD(this, "miBD", null, 1)
        var url = "http://restaurantsigloxxi.somee.com/Servicios.svc/ListarOrdenesCocinaPorMesa/${custom.NumeroMesa()}"
        var tarea=JsonInsumos.ObtenerLista("", this)
        tarea.execute(url)

    }

    fun formato(n:Int):String{
        var f=DecimalFormat("###,###")
        return f.format(n).replace(',','.')
    }
    fun listaCarrito (){


        val custom=BDD(this, "miBD", null, 1)
        rv_listacarritoFinal.adapter=CustomAdapterOrden.CustomAdapterOrdenFinal(
            this,
            android.R.layout.simple_list_item_1,
            custom.listarCarritoFinal()
        )

        sw_carritoFinal.isRefreshing=false

    }


    override fun Pagooo(response: String) {
        if (!response.equals("Error")) {
            try {


                var lista = ArrayList<Orden.jOrden>()
                var jsonData = JSONArray(response)
                for (i in 0..jsonData.length() - 1) {

                    var insumo = jsonData.getJSONObject(i)
                    var id = insumo.getInt("Id").toInt()

                    val queque = Volley.newRequestQueue(this)
                    var addOrden = "http://restaurantsigloxxi.somee.com/Servicios.svc/Pagar/${id}"

                    val respuesta = StringRequest(addOrden, Response.Listener {
                        Log.i("Response is", addOrden)
                    }, Response.ErrorListener { error -> error.printStackTrace() })
                    queque.add(respuesta)



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


    override fun onTaskCompleted(response: String) {
        if (!response.equals("Error")) {
            try {


                var lista = ArrayList<Orden.EstadoOrden>()
                var jsonData = JSONArray(response)
                for (i in 0..jsonData.length() - 1) {

                    var insumo = jsonData.getJSONObject(i)
                    var nombre = insumo.getString("nombre_receta")
                    var estado = insumo.getString("descripcion_estado_orden")
                    lista.add(Orden.EstadoOrden(nombre, estado))
                }
                var adap = CustomEstadoOrden(
                    this,
                    android.R.layout.simple_list_item_1,
                    lista,
                    rv_listaOrdenFinal
                )
                rv_listaOrdenFinal.adapter = adap
                sw_ordenFinal.isRefreshing=false
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