package com.example.restaurantsigloxxi

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*

import kotlinx.android.synthetic.main.activity_menu.*
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat.startActivityForResult
import androidx.core.view.isInvisible
import kotlinx.android.synthetic.main.activity_menu.view.*
import kotlinx.android.synthetic.main.activity_pago.*
import java.text.DecimalFormat


class CustomAdapterOrden (var miContexto: Context,
                          var miRecurso:Int,
                          var miLista:ArrayList<Carrito.CarritoJson>,var listaO:ListView,val lbl_total:TextView
): ArrayAdapter<Carrito.CarritoJson>(miContexto,miRecurso,miLista) {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val item = LayoutInflater.from(miContexto).inflate(R.layout.item_orden, null)
        val btn_quitar: Button = item.findViewById(R.id.btn_quitar)!!
        val nombre: TextView = item.findViewById(R.id.lbl_nombreO)!!
        val precio: TextView = item.findViewById(R.id.lbl_precioO)!!
        val registro = miLista[position]
        nombre.text = registro.Nombre
        precio.text = "$ " + formato(registro.Precio)
        btn_quitar.setOnClickListener({

            var alerta = AlertDialog.Builder(miContexto)
            alerta.setTitle("Confirmar acción")
            alerta.setMessage("¿ Desea Eliminar ${registro.Nombre} del Carrito ?")
            alerta.setNeutralButton("Si ", { dialog, which ->
                val custom = BDD(miContexto, "miBD", null, 1)
                custom.QuitarCarrito(registro)
                custom.QuitarCarritoFinal(registro)
                Toast.makeText(miContexto, "Eliminado con Exito", Toast.LENGTH_SHORT).show()


                var adap = CustomAdapterOrden(miContexto, android.R.layout.simple_list_item_1, custom.listarCarrito(),listaO,lbl_total)

                val tf=custom.totalFinal()
                lbl_total.setText("Total a Pagar $ ${formato(tf)}")
                listaO.adapter=adap
                custom.close()

            })
            alerta.setNegativeButton("No", { dialog, which -> })
            alerta.show()


        })

        return item
    }

    class CustomAdapterOrdenFinal(
        var miContexto: Context,
        var miRecurso: Int,
        var miLista: ArrayList<Carrito.CarritoJson>
    ) : ArrayAdapter<Carrito.CarritoJson>(miContexto, miRecurso, miLista) {
        override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
            val item = LayoutInflater.from(miContexto).inflate(R.layout.item_orden, null)
            val btn_quitar: Button = item.findViewById(R.id.btn_quitar)!!
            val nombre: TextView = item.findViewById(R.id.lbl_nombreO)!!
            val precio: TextView = item.findViewById(R.id.lbl_precioO)!!
            val registro = miLista[position]
            nombre.text = registro.Nombre
            precio.text = "$ " + formato(registro.Precio)
            btn_quitar.visibility = View.INVISIBLE

            return item
        }

        fun formato(n:Int):String{
            var f= DecimalFormat("###,###")
            return f.format(n).replace(',','.')
        }
    }
    fun formato(n:Int):String{
        var f= DecimalFormat("###,###")
        return f.format(n).replace(',','.')
    }

}