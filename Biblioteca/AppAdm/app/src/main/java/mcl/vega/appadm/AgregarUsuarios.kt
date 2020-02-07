package mcl.vega.appadm

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.activity_agregar_usuarios.*
import mcl.vega.appadm.Clases.Usuario
import org.json.JSONObject

class AgregarUsuarios : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_agregar_usuarios)
        val custom2= BDD(this, "miBD", null, 1)

        btn_log.setOnClickListener {


            var l=Intent(this,Login::class.java)
            startActivity(l)
        }
        btn_registrarUser.setOnClickListener {

            var r = txt_runU.text.toString()
            var n = txt_nombreU.text.toString()
            var pas = txt_passU.text.toString()
            if (r == "" || n == "" || pas == "") {
                Toast.makeText(this, "campos vacios, reintente", Toast.LENGTH_SHORT).show()

            } else {
                val custom = BDD(this, "miBD", null, 1)
                custom.InsertarUsuario(Usuario(0, r, n, r, pas, 1, 2))
                val queque = Volley.newRequestQueue(this)
                var url = "http://librosmcl.somee.com/servicios.svc/AgregarUsuarios"
                val jsonObject = JSONObject()
                jsonObject.put("nombre", n)
                jsonObject.put("activo", "1")
                jsonObject.put("run", r)
                jsonObject.put("user", r)
                jsonObject.put("pass", pas)
                jsonObject.put("tipo", "2")

                val jsonObjectRequest = JsonObjectRequest(url, jsonObject,
                    Response.Listener { response ->
                        Toast.makeText(this, "Exito al agregar Usuario", Toast.LENGTH_SHORT).show()
                        Log.i("Response is", url)
                    }, Response.ErrorListener { error ->
                        error.printStackTrace()

                    }
                )
                queque.add(jsonObjectRequest)
                var log = Intent(this, Login::class.java)
                startActivity(log)
            }
        }
    }
}
