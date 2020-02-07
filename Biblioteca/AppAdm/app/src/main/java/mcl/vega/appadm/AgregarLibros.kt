package mcl.vega.appadm

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.activity_agregar_libros.*
import mcl.vega.appadm.Clases.Libro
import org.json.JSONObject

class AgregarLibros : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_agregar_libros)

        btn_addLibro.setOnClickListener {

            var n=txt_nombreLibro.text.toString()
            var s=txt_stockLibro.text.toString()
            val db = BDD(this, "miBD", null, 1)
            db.InsertarLibro(Libro( 0,n,s.toInt(), 1))
            val queque= Volley.newRequestQueue(this)
            var url="http://librosmcl.somee.com/servicios.svc/Agregarlibros"
            val jsonObject= JSONObject()
            jsonObject.put("nombre",n)
            jsonObject.put("activo","1")
            jsonObject.put("stock",s)

            val jsonObjectRequest= JsonObjectRequest(url,jsonObject,
                Response.Listener { response ->
                    Toast.makeText(this,"Exito al agregar libro",Toast.LENGTH_SHORT).show()
                    Log.i("Response is",  url)
                },Response.ErrorListener { error ->
                    error.printStackTrace()

                }
            )
            queque.add(jsonObjectRequest)

            var li=Intent(this,ListaLibros::class.java)
            startActivity(li)


        }
    }
}
