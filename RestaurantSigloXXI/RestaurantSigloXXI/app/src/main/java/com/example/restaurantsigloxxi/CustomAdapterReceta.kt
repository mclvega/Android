package com.example.restaurantsigloxxi

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import kotlinx.android.synthetic.main.activity_menu.*
import kotlinx.android.synthetic.main.activity_menu.view.*
import java.text.DecimalFormat


class CustomAdapterReceta(var miContexto: Context,
                          var miRecurso:Int,
                          var miLista:ArrayList<Receta.Recetajson>,var listaO:ListView,var lbl_total:TextView
): ArrayAdapter<Receta.Recetajson>(miContexto,miRecurso,miLista)
{
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val item= LayoutInflater.from(miContexto).inflate(R.layout.item_receta,null)

        val nombre: TextView =item.findViewById(R.id.lbl_nombreReceta)!!
        val precio: TextView =item.findViewById(R.id.lbl_precioReceta)!!

        val btn: Button = item.findViewById(R.id.btn_agregarReceta)!!
        val registro=miLista[position]


        nombre.text=registro.Nombre
        precio.text="$ "+formato(registro.Precio)

        btn.setOnClickListener{

            val custom= BDD(miContexto, "miBD", null, 1)
            custom.InsertarCarrito(Carrito.CarritoJson(1,registro.Id, registro.Nombre,registro.Precio,1))
            custom.InsertarCarritoFinal(Carrito.CarritoJson(1,registro.Id, registro.Nombre,registro.Precio,1))
            custom.close()

            var adap = CustomAdapterOrden(miContexto, android.R.layout.simple_list_item_1, custom.listarCarrito(),listaO,lbl_total)


            val tf=custom.totalFinal()
            lbl_total.setText("Total a Pagar $ ${formato(tf)}")
            listaO.adapter=adap


            custom.close()



        }

        return item
    }

    fun formato(n:Int):String{
        var f= DecimalFormat("###,###")
        return f.format(n).replace(',','.')
    }






}