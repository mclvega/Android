package com.example.recepcion

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_lista_mesas.*
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.text.DecimalFormat

class CustomAdapterMesa (var miContexto: Context,
                         var miRecurso:Int,
                         var miLista:ArrayList<Mesa>, var listad: ListView,var listaO:ListView,var swipeD:SwipeRefreshLayout,var swipeO:SwipeRefreshLayout,var num:Byte,var txt_num:EditText
): ArrayAdapter<Mesa>(miContexto,miRecurso,miLista) ,JsonMesas.ObtenerListaD.OntaskCompletedD,JsonMesas.ObtenerListaO.OntaskCompletedO
{
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val item= LayoutInflater.from(miContexto).inflate(R.layout.item_mesas,null)

        val numero: TextView =item.findViewById(R.id.lbl_numero)!!
        val capacidad: TextView =item.findViewById(R.id.lbl_capacidad)!!

        val btn: Button = item.findViewById(R.id.btn_asignar)!!
        val registro=miLista[position]


        numero.text="Numero de Mesa : ${registro.numero}"
        capacidad.text="Capacidad: ${registro.capacidad}"

        btn.setOnClickListener{


                val queque = Volley.newRequestQueue(miContexto)
                var url="http://restaurantsigloxxi.somee.com/Servicios.svc/ActualizarEstadoMesa/${registro.id},${num}"
                val respuesta = StringRequest(url, Response.Listener {
                    Log.i("Response is", url)

                }, Response.ErrorListener { error -> error.printStackTrace() })
                queque.add(respuesta)
                var tarea=JsonMesas.ObtenerListaD("",this)
                tarea.execute("http://restaurantsigloxxi.somee.com/Servicios.svc/ListarMesasLibres")
                var tarea2=JsonMesas.ObtenerListaO("",this)
                tarea2.execute("http://restaurantsigloxxi.somee.com/Servicios.svc/ListarMesasOcupadas")
                ListaD()
                ListaO()

                Toast.makeText(miContexto,"Exito al asignar la mesa ${registro.numero}",Toast.LENGTH_SHORT).show()




        }

        return item
    }
    fun ListaD (){
        var tarea=JsonMesas.ObtenerListaD("",this)
        tarea.execute("http://restaurantsigloxxi.somee.com/Servicios.svc/ListarMesasLibres")


    }
    fun ListaO (){
        var tarea=JsonMesas.ObtenerListaO("",this)
        tarea.execute("http://restaurantsigloxxi.somee.com/Servicios.svc/ListarMesasOcupadas")


    }

    override fun onTaskCompleted(response: String) {
        if (!response.equals("Error")) {
            try {


                var lista = ArrayList<Mesa>()
                var jsonData = JSONArray(response)
                for (i in 0..jsonData.length() - 1) {


                    var mesa = jsonData.getJSONObject(i)
                    var id=mesa.getInt("Id").toShort()
                    var numero = mesa.getInt("Numero").toByte()
                    var capacidad = mesa.getInt("Capacidad").toShort()
                    lista.add(Mesa(id,numero,capacidad ))
                }
                var adap = CustomAdapterMesa(miContexto, android.R.layout.simple_list_item_1, lista,listad,listaO,swipeD,swipeO,num,txt_num)
                listad.adapter = adap
                swipeD.isRefreshing=false
                txt_num.setText("0")
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




    override fun onTaskCompletedO(response: String) {
        if (!response.equals("Error")) {
            try {


                var listaOrd = ArrayList<Mesa.MesaO>()
                var jsonData = JSONArray(response)
                for (i in 0..jsonData.length() - 1) {

                    var mesa = jsonData.getJSONObject(i)
                    var numero = mesa.getInt("Numero").toByte()
                    var estado=mesa.getBoolean("Estado")
                    var capacidad = mesa.getInt("Capacidad").toShort()
                    listaOrd.add(Mesa.MesaO(numero,capacidad,estado ))
                }
                var adap = CustomAdapterO(miContexto, android.R.layout.simple_list_item_1, listaOrd,listaO,swipeO)
                listaO.adapter = adap

               swipeO.isRefreshing=false
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