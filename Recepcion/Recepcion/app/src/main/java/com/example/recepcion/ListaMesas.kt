package com.example.recepcion

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_lista_mesas.*
import org.json.JSONArray
import org.json.JSONException

class ListaMesas : AppCompatActivity() ,JsonMesas.ObtenerListaD.OntaskCompletedD,JsonMesas.ObtenerListaO.OntaskCompletedO{
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lista_mesas)


        txt_capacidad.setText("0")


        ListarMesasO()
        sw_listaO.setOnRefreshListener{
            ListarMesasO()
        }

        ListarMesasD()
        sw_listaD.setOnRefreshListener{
            ListarMesasD()
        }
        btn_buscar.setOnClickListener {
            var capacidad=txt_capacidad.text.toString()
            if(capacidad<"1"){
                    Toast.makeText(this,"Minimo 1 persona por mesa",Toast.LENGTH_SHORT).show()
            }else{
                ListarMesasB(capacidad)
            }

        }
    }
    fun ListarMesasD (){
        sw_listaD.isRefreshing=true
        var tarea=JsonMesas.ObtenerListaD("",this)
        tarea.execute("http://restaurantsigloxxi.somee.com/Servicios.svc/ListarMesasLibres")

    }

    fun ListarMesasB (capacidad:String){
        sw_listaD.isRefreshing=true
        var tarea=JsonMesas.ObtenerListaD("",this)
        tarea.execute("http://restaurantsigloxxi.somee.com/Servicios.svc/ListarMesasLibresC/${capacidad}")

    }

    fun ListarMesasO (){
        sw_listaO.isRefreshing=true
        var tarea=JsonMesas.ObtenerListaO("",this)
        tarea.execute("http://restaurantsigloxxi.somee.com/Servicios.svc/ListarMesasOcupadas")

    }
    override fun onTaskCompleted(response: String) {
            if (!response.equals("Error")) {
                if(response.equals("[]")){
                    Toast.makeText(this,"No hay mesas disponibles",Toast.LENGTH_SHORT).show()

                    sw_listaD.isRefreshing=false
                }else{
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
                    if(txt_capacidad.text.toString()==""){
                        txt_capacidad.setText("0")
                    }
                    var adap = CustomAdapterMesa(this, android.R.layout.simple_list_item_1, lista,lv_mesasDisponibles,lv_mesasOcupadas,sw_listaD,sw_listaO,txt_capacidad.text.toString().toByte(),txt_capacidad)
                    lv_mesasDisponibles.adapter = adap
                    sw_listaD.isRefreshing=false
                } catch (e: JSONException) {
                    Log.e("Json Error", "${e.message},${e.cause}")
                }
                }
            }

            else {
                /* var alerta = AlertDialog.Builder(this)
                 alerta.setTitle("Problemas con el servidor")
                 alerta.setMessage("Ocurrio un problema al conectar con el servidor")
                 alerta.setNeutralButton("Reintentar ", { dialog, which -> listaReceta() })
                 alerta.show()*/

                ListarMesasD()
            }

    }

    override fun onTaskCompletedO(response: String) {
        if (!response.equals("Error")) {
            try {


                var lista = ArrayList<Mesa.MesaO>()
                var jsonData = JSONArray(response)
                for (i in 0..jsonData.length() - 1) {

                    var mesa = jsonData.getJSONObject(i)
                    var numero = mesa.getInt("Numero").toByte()
                    var estado=mesa.getBoolean("Estado")
                    var capacidad = mesa.getInt("Capacidad").toShort()
                    lista.add(Mesa.MesaO(numero,capacidad,estado ))
                }
                var adap = CustomAdapterO(this, android.R.layout.simple_list_item_1, lista,lv_mesasOcupadas,sw_listaO)
                lv_mesasOcupadas.adapter = adap
                sw_listaO.isRefreshing=false
            } catch (e: JSONException) {
                Log.e("Json Error", "${e.message},${e.cause}")
            }
        } else {
            /* var alerta = AlertDialog.Builder(this)
             alerta.setTitle("Problemas con el servidor")
             alerta.setMessage("Ocurrio un problema al conectar con el servidor")
             alerta.setNeutralButton("Reintentar ", { dialog, which -> listaReceta() })
             alerta.show()*/
            ListarMesasO()
        }

    }
}