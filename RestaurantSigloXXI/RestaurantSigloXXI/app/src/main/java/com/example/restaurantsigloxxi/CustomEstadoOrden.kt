package com.example.restaurantsigloxxi

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import android.widget.TextView

class CustomEstadoOrden(var miContexto: Context,
                        var miRecurso:Int,
                        var miLista:ArrayList<Orden.EstadoOrden>, var listaO: ListView
): ArrayAdapter<Orden.EstadoOrden>(miContexto,miRecurso,miLista)
{
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val item= LayoutInflater.from(miContexto).inflate(R.layout.item_estado_orden,null)

        val nombre: TextView =item.findViewById(R.id.lbl_nombreEO)!!
        val estado: TextView =item.findViewById(R.id.lbl_estadoEO)!!
        val registro=miLista[position]


        nombre.text=registro.nombre

        estado.text=registro.estado


        return item
    }

}