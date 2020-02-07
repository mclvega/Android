package mcl.vega.appusuario



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
import kotlinx.android.synthetic.main.activity_lista_libros.*
import kotlinx.android.synthetic.main.activity_item_libro.*
import mcl.vega.appusuario.Clases.CustomAdapterLibros
import mcl.vega.appusuario.Clases.Json
import mcl.vega.appusuario.Clases.Libro
import mcl.vega.appusuario.Clases.Session
import org.json.JSONArray
import org.json.JSONException

class ListaLibros : AppCompatActivity(),Json.Obtener.OnTaskCompleted {

    var runU:String=""
    var nomb:String=""
    var cod:String=""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lista_libros)

        nomb=intent.getStringExtra("nombreusu")

        cod=intent.getStringExtra("codigo")
        runU=intent.getStringExtra("runusu")
        btn_upd.setOnClickListener {
            var tarea=Json.Obtener("",this)
            tarea.execute("http://librosmcl.somee.com/servicios.svc/Listarlibros")
        }

        btn_listaFav.setOnClickListener {
            var main= Intent(this,ListaLibrosFavoritos::class.java)


            main.putExtra("nombre",intent.getStringExtra("nombreusu"))
            main.putExtra("run",intent.getStringExtra("runusu"))
            startActivity(main)
        }
        btn_listaPrest.setOnClickListener {
            var main= Intent(this,ListaPrestamos::class.java)


            main.putExtra("nombreusu",intent.getStringExtra("nombreusu"))
            main.putExtra("runusu",intent.getStringExtra("runusu"))
            startActivity(main)
        }
        var session=Session(applicationContext)
        session.checkLogin()
        var user:HashMap<String,String> = session.getUserDetails()

        var tarea=Json.Obtener("",this)
        tarea.execute("http://librosmcl.somee.com/servicios.svc/Listarlibros")

        btn_cerrarSesion.setOnClickListener {
            session.LogoutUser()
        }



    }

    fun lista(){
        var tarea=Json.Obtener("",this)
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

                    lista.add(Libro(codigo,nombre, stock, activo,runU,nomb,cod.toInt()))


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