package com.example.recepcion

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import android.widget.TextView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout

class CustomAdapterO (var miContexto: Context,
                          var miRecurso:Int,
                          var miListaO:ArrayList<Mesa.MesaO>, var listaO: ListView, var swipeO: SwipeRefreshLayout
): ArrayAdapter<Mesa.MesaO>(miContexto,miRecurso,miListaO) {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val item = LayoutInflater.from(miContexto).inflate(R.layout.item_mesas, null)

        val numero: TextView = item.findViewById(R.id.lbl_numero)!!
        val capacidad: TextView = item.findViewById(R.id.lbl_capacidad)!!


        val btn: Button = item.findViewById(R.id.btn_asignar)!!
        val registro = miListaO[position]

        if (registro.estado == true) {
            btn.visibility = View.INVISIBLE
        }

        numero.text = "Numero de Mesa : ${registro.numero}"
        capacidad.text = "Capacidad: ${registro.capacidad}"


        return item
    }
}
