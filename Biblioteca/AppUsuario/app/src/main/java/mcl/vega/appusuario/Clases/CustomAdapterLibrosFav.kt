package mcl.vega.appusuario.Clases

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.TextView
import mcl.vega.appusuario.BDD
import mcl.vega.appusuario.R

class CustomAdapterLibrosFav(var miContexto: Context,
                             var miRecurso:Int,
                             var miLista:ArrayList<LibrosFavoritos>
): ArrayAdapter<LibrosFavoritos>(miContexto,miRecurso,miLista)
{
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val item= LayoutInflater.from(miContexto).inflate(R.layout.activity_item_libros_fav,null)
        val nombre_usu:TextView =item.findViewById(R.id.lbl_nombrefav)!!
        val run_usu:TextView =item.findViewById(R.id.lbl_runfav)!!
        val nombre_libro:TextView =item.findViewById(R.id.lbl_nombrelibfav)!!
        val btn: Button =item.findViewById(R.id.btn_del)!!
        val registro=miLista[position]
        nombre_usu.text=registro.nombre
        run_usu.text=registro.run
        nombre_libro.text=registro.nombre_libro
        btn.setOnClickListener {
            var alerta = AlertDialog.Builder(miContexto)
            alerta.setTitle("Eliminar")
            alerta.setMessage("Esta Seguro??")
            alerta.setPositiveButton("Si",{dialog, which ->
                val db = BDD(miContexto, "miBD", null, 1)
                db.EliminarLibrosFav(registro.codigo)
                this.remove(registro)
            })
            alerta.setNegativeButton("No",{dialog, which -> dialog.cancel() })
            alerta.show()

        }

        return item
    }
}