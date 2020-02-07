package cl.mcl.mvd.PruebaMVD

import android.app.DatePickerDialog
import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.text.format.DateUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Toast
import cl.mcl.mvd.PruebaMVD.Clases.Cliente
import cl.mcl.mvd.PruebaMVD.Clases.ListaCliente
import cl.mcl.mvd.PruebaMVD.Clases.Vehiculos
import kotlinx.android.synthetic.main.activity_servicio.*
import kotlinx.android.synthetic.main.fragment_agregar.*
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.*
import kotlin.collections.ArrayList


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [Agregar.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [Agregar.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class Agregar : Fragment() {
    // TODO: Rename and change types of parameters
    var miContexto: Context? = null
    var miContext: Context? = activity
            override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val c = Calendar.getInstance()
        var v =  inflater.inflate(R.layout.fragment_agregar, container, false)



        var btn: Button = v.findViewById(R.id.btnFecha)
        btn.setOnClickListener {
            val anio = c.get(Calendar.YEAR)
            val mes = c.get(Calendar.MONTH)
            val dia = c.get(Calendar.DAY_OF_MONTH)

            val dpd = DatePickerDialog(miContexto,
                    DatePickerDialog.OnDateSetListener {
                        view, year, monthOfYear, dayOfMonth ->
                        var m=monthOfYear.toInt()+1
                        lblFecha.setText("$dayOfMonth/$m/$year")

                    }, anio, mes, dia)
            dpd.datePicker.maxDate=(c.timeInMillis+DateUtils.DAY_IN_MILLIS)
            dpd.show()
        }


        var btn2: Button = v.findViewById(R.id.btnRegistrar)
        btn2.setOnClickListener {

            var run: String = txtRun.text.toString()
            var nombre: String = txtNombre.text.toString()
            var direccion: String = txtDireccion.text.toString()
            var telefono: String = txtTelefono.text.toString()
            var fecha: String = lblFecha.text.toString()
            var genero:String=cbgenero.selectedItem.toString()

            if (run.isEmpty()) {
                Toast.makeText(miContexto, "Agregue un run", Toast.LENGTH_LONG).show()
            } else if (nombre.isEmpty()) {
                Toast.makeText(miContexto, "Agregue un nombre", Toast.LENGTH_LONG).show()
            } else if (direccion.isEmpty()) {
                Toast.makeText(miContexto, "Agregue una dirección ", Toast.LENGTH_LONG).show()
            } else if (telefono.isEmpty()) {
                Toast.makeText(miContexto, "Agregue un telefono", Toast.LENGTH_LONG).show()
            } else if (fecha.isEmpty()) {
                Toast.makeText(miContexto, "Agregue una fecha correcta ", Toast.LENGTH_LONG).show()
            }
            else {
                var lista: ArrayList<Vehiculos> = ArrayList()
                var par = Cliente(run, nombre,fecha, direccion,telefono.toInt(),genero, lista)

                ListaCliente.lista.add(par)
                Toast.makeText(miContexto, "Cliente  registrado con éxito.", Toast.LENGTH_LONG).show()
            }
        }


        return v
    }

}