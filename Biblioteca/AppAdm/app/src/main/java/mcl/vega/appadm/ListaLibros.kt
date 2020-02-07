package mcl.vega.appadm

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.TextView
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.activity_agregar_libros.*
import kotlinx.android.synthetic.main.activity_lista_libros.*
import kotlinx.android.synthetic.main.item_libros.*
import mcl.vega.appadm.Clases.CustomAdapterLibros
import mcl.vega.appadm.Clases.JsonLibros
import mcl.vega.appadm.Clases.Libro
import mcl.vega.appadm.Clases.Session
import org.json.JSONArray
import org.json.JSONException

class ListaLibros : AppCompatActivity(),JsonLibros.ObtenerLibros.OnTaskCompleted {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lista_libros)


        btn_upd.setOnClickListener {
            var tarea=JsonLibros.ObtenerLibros("",this)
            tarea.execute("http://librosmcl.somee.com/servicios.svc/Listarlibros")
        }

        btn_listaPrest.setOnClickListener {
            var main= Intent(this,ListaPrestamos::class.java)


            main.putExtra("nombre",intent.getStringExtra("nombre"))
            main.putExtra("run",intent.getStringExtra("run"))
            startActivity(main)
        }
        var session=Session(applicationContext)
        session.checkLogin()
        var user:HashMap<String,String> = session.getUserDetails()

        var tarea=JsonLibros.ObtenerLibros("",this)
        tarea.execute("http://librosmcl.somee.com/servicios.svc/Listarlibros")

        btn_cerrarSesion.setOnClickListener {
            session.LogoutUser()
        }

        btn_addlib.setOnClickListener {
            var main= Intent(this,AgregarLibros::class.java)


            main.putExtra("nombre",intent.getStringExtra("nombre"))
            main.putExtra("run",intent.getStringExtra("run"))
            startActivity(main)
        }

    }

 fun lista(){
     var tarea=JsonLibros.ObtenerLibros("",this)
     tarea.execute("http://librosmcl.somee.com/servicios.svc/Listarlibros")
 }
    override fun onTaskCompleted(response: String) {
        if (!response.equals("Error")) {
            try {

                var lista = ArrayList<Libro>()
                var jsonData = JSONArray(response)
                for (i in 0..jsonData.length() - 1) {

                    var libro = jsonData.getJSONObject(i)
                    var codigo = libro.getInt("codigo")
                    var nombre = libro.getString("nombre")
                    var stock = libro.getInt("stock")
                    var activo = libro.getInt("activo")

                    lista.add(Libro(codigo,nombre, stock, activo))


                }

                var adap = CustomAdapterLibros(this, android.R.layout.simple_list_item_1, lista)
                lv_libros.adapter = adap
            } catch (e: JSONException) {
                Log.e("Json Error", "${e.message},,${e.cause}")
            }
        } else {
            var alerta = AlertDialog.Builder(this)
            alerta.setTitle("Algo Ocurrio!!")
            alerta.setMessage("Ocurrio un Error")
            alerta.setNegativeButton("Cancelar", { dialog, which -> dialog.cancel() })
            alerta.show()
        }
    }


}
