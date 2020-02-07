package mcl.vega.appusuario.Clases


import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import android.text.format.DateUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.content.ContextCompat.startActivity
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import mcl.vega.appusuario.BDD
import mcl.vega.appusuario.ListaLibros
import mcl.vega.appusuario.R
import org.json.JSONObject
import java.time.LocalDate
import java.util.*
import kotlin.collections.ArrayList

class  CustomAdapterLibros(var miContexto: Context,
                           var miRecurso:Int,
                           var miLista:ArrayList<Libro>
): ArrayAdapter<Libro>(miContexto,miRecurso,miLista)
{
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val item = LayoutInflater.from(miContexto).inflate(R.layout.activity_item_libro, null)
        val nombre: TextView = item.findViewById(R.id.lbl_nombrel)!!
        val stock: TextView = item.findViewById(R.id.lbl_stockl)!!
        val fec:TextView=item.findViewById(R.id.lbl_fecha)
        val btn: Button = item.findViewById(R.id.btn_addFav)!!
        val btn2: Button = item.findViewById(R.id.btn_reservar)!!
        val btn3: Button = item.findViewById(R.id.btn_date)!!
        val registro = miLista[position]
        nombre.text = registro.nombre
        stock.text = registro.stock.toString()
        btn.setOnClickListener {
            val custom=BDD(miContexto,"miBD",null,1)
            custom.InsertarLibroFavorito(LibrosFavoritos(0, registro.run,registro.nomb,registro.nombre))
        }
        btn3.setOnClickListener {
            val c = Calendar.getInstance()

                val anio = c.get(Calendar.YEAR)
                val mes = c.get(Calendar.MONTH)
                val dia = c.get(Calendar.DAY_OF_MONTH)

                val dpd = DatePickerDialog(miContexto,
                    DatePickerDialog.OnDateSetListener {
                            view, year, monthOfYear, dayOfMonth ->
                        var m=monthOfYear.toInt()+1
                        fec.setText("$dayOfMonth.$m.$year")

                    }, anio, mes, dia)
                dpd.datePicker.minDate=(c.timeInMillis+ DateUtils.DAY_IN_MILLIS)
                dpd.show()

        }
        btn2.setOnClickListener {


            if(fec.text==""){
                Toast.makeText(miContexto,"fecha de devolucion vacia, reintente",Toast.LENGTH_SHORT).show()
            }else{
                val queque= Volley.newRequestQueue(miContexto)
                var url="http://librosmcl.somee.com/servicios.svc/AgregarPrestamos"
                val jsonObject= JSONObject()
                jsonObject.put("usuario",registro.cod)
                jsonObject.put("libro",registro.codigo)
                jsonObject.put("fecha_devolucion",convert(fec.text.toString()))
                jsonObject.put("fecha_prestamo",convert(fec.text.toString()))

                val jsonObjectRequest= JsonObjectRequest(url,jsonObject,
                    Response.Listener { response ->
                        Toast.makeText(miContexto,"Exito al agregar Prestamo", Toast.LENGTH_SHORT).show()
                        Log.i("Response is",  url)
                    }, Response.ErrorListener { error ->
                        error.printStackTrace()

                    }
                )
                if(queque.add(jsonObjectRequest)!=null){
                    Toast.makeText(miContexto,"Exito al agregar Prestamo", Toast.LENGTH_SHORT).show()

                }
            }

        }
        return item
    }

    fun convert(d:String):String{
        var splitted=d.split(".")
        var dt=Date(splitted[2].toInt()-1900,splitted[1].toInt()-1,splitted[0].toInt())
        var nd=Date(Date.UTC(dt.year,dt.month,dt.date,dt.hours,dt.minutes,dt.seconds))
        var d3=nd.time.toString()
        var d4="/Date($d3)/"
        return d4
    }
}