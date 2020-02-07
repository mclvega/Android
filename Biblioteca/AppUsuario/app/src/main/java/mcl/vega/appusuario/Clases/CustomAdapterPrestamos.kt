package mcl.vega.appusuario.Clases

import android.content.Context
import android.view.View
import android.widget.ArrayAdapter
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import mcl.vega.appusuario.R
import java.text.SimpleDateFormat
import java.time.Instant
import java.time.format.DateTimeFormatter
import java.util.*
import java.util.regex.Matcher
import java.util.regex.Pattern
import kotlin.collections.ArrayList

class CustomAdapterPrestamos(var miContexto: Context,
                             var miRecurso:Int,
                             var miLista:ArrayList<Prestamos>
): ArrayAdapter<Prestamos>(miContexto,miRecurso,miLista)
{
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val item= LayoutInflater.from(miContexto).inflate(R.layout.activity_item_prestamos,null)
        val nombreLibro: TextView =item.findViewById(R.id.lbl_nombrelibPres)!!
        val run: TextView =item.findViewById(R.id.lbl_runPre)!!
        val nombre: TextView =item.findViewById(R.id.lbl_nombrePre)!!
        val presta: TextView =item.findViewById(R.id.lbl_prestamoPre)!!
        val devolucion: TextView =item.findViewById(R.id.lbl_devolucionPre)!!
        val registro=miLista[position]
        nombreLibro.text=registro.nombre_libro
        nombre.text=registro.nombre
        run.text=registro.run
        var pat:Pattern= Pattern.compile("/Date\\((\\d+)([+-]\\d{4})\\)/")
        var m:Matcher=pat.matcher((registro.fecha_prestamo))

        if(m.matches()){
            var i:Instant= Instant.ofEpochMilli(m.group(1).toLong())
            val form=DateTimeFormatter.ofPattern("dd-MM-yyyy")
            var d=Date.from(i)
            presta.text=SimpleDateFormat("dd-MM-yyyy").format(d).toString()
        }
        var m2:Matcher=pat.matcher((registro.fecha_devolucion))
        if(m2.matches()){
            var i2:Instant= Instant.ofEpochMilli(m2.group(1).toLong())
            val form=DateTimeFormatter.ofPattern("dd-MM-yyyy")
            var d=Date.from(i2)
            devolucion.text=SimpleDateFormat("dd-MM-yyyy").format(d).toString()

        }


        return item
    }
}