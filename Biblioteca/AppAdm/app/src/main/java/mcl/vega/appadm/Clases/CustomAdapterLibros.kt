package mcl.vega.appadm.Clases

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import mcl.vega.appadm.R

class  CustomAdapterLibros(var miContexto: Context,
                           var miRecurso:Int,
                           var miLista:ArrayList<Libro>
): ArrayAdapter<Libro>(miContexto,miRecurso,miLista)
{
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val item= LayoutInflater.from(miContexto).inflate(R.layout.item_libros,null)
        val nombre: TextView =item.findViewById(R.id.lbl_nombrel)!!
        val ed:EditText=item.findViewById(R.id.txt_edit)!!
        val stock:TextView =item.findViewById(R.id.lbl_stockl)!!
        val btn:Button=item.findViewById(R.id.btn_edit)!!
        val registro=miLista[position]
        nombre.text=registro.nombre
        stock.text=registro.stock.toString()
        var nuevoStock=ed.text
        btn.setOnClickListener {
            if(nuevoStock.toString()!=""){
            val queque= Volley.newRequestQueue(miContexto)
            var url="http://librosmcl.somee.com/servicios.svc/CambiarStock/${registro.codigo},$nuevoStock"
            val respuesta=StringRequest(url,Response.Listener {
                Log.i("Response is",  url)
            },Response.ErrorListener { error ->error.printStackTrace()  }
            )

            if(queque.add(respuesta)!=null){
                Toast.makeText(miContexto,"Stock Actualizado",Toast.LENGTH_SHORT).show()
            }else{
                Toast.makeText(miContexto,"No se pudo actualizar Stock ",Toast.LENGTH_SHORT).show()

            }
            }else{
                Toast.makeText(miContexto,"ingrse un valor para actualizar el Stock ",Toast.LENGTH_SHORT).show()
            }


        }


        return item
    }
}