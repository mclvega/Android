package mcl.vega.appadm

import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.database.SQLException
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.activity_login.*
import mcl.vega.appadm.Clases.Libro
import mcl.vega.appadm.Clases.Session
import mcl.vega.appadm.Clases.Usuario
import okhttp3.*
import java.io.IOException

class Login : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)



        var session:Session

        session=Session(applicationContext)
        if(session.isLoggedIn()){
            var i:Intent= Intent(applicationContext,ListaLibros::class.java)
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(i)
            finish()
        }

        btn_login.setOnClickListener {
            var user=txt_user.text.toString()
            var pass=txt_password.text.toString()

            if(user=="" || pass==""){
                Toast.makeText(this,"usuario o password vacia, reintente",Toast.LENGTH_SHORT).show()
            }else if(user==""&&pass==""){
                Toast.makeText(this,"si no tienes usuario registrate",Toast.LENGTH_SHORT).show()
            }
            else{

                val url="http://librosmcl.somee.com/servicios.svc/BuscarUsuarios/$user"
                val request= Request.Builder().url(url).build()
                if(request!=null){
                    val cliente= OkHttpClient()
                    cliente.newCall(request).enqueue(object : Callback {
                        override fun onFailure(call: Call?, e: IOException?) {


                        }

                        override fun onResponse(call: Call?, response: Response?) {
                            val respuesta=response?.body()?.string()
                            if(respuesta!=null){
                                val gson= GsonBuilder().create()
                                val indicador=gson.fromJson(respuesta,Usuario::class.java)

                                if(indicador!=null){
                                    if(user=="" || pass==""){
                                        ingre()
                                    }else{
                                        if(indicador.user==user && indicador.pass==pass && indicador.tipo.toString()=="2"){

                                            var u:Usuario=(Usuario(indicador.codigo,indicador.run,indicador.nombre,indicador.user,indicador.pass,
                                                indicador.activo,indicador.tipo))
                                            list(u)
                                        }else{
                                            ingre()
                                        }
                                    }
                                }else{
                                    ingre()}
                            }else{
                                ingre()}

                        }

                    })
                }else{
                    Toast.makeText(this,"ingrese las credenciales correctas",Toast.LENGTH_SHORT).show()

                }



            }


            /* val custom=BDD(this,"miBD",null,1)
             var us: Usuario?=custom.Login(user,pass)

             if(custom.Login(user,pass)!=null){

                 session.createLoginSession(user,pass)
                 val login= Intent(this,ListaLibros::class.java)
                 login.putExtra("nombre",us!!.nombre)
                 val db = BDD(this, "miBD", null, 1)
                 var usuario=db.BuscarUsuario(us!!.run)
                 login.putExtra("codigo",usuario!!.codigo.toString())
                 login.putExtra("nombreusu",us!!.nombre)
                 login.putExtra("runusu",us!!.run)
                 startActivity(login)
             }
             else if(user=="" || pass=="") {
                 Toast.makeText(this,"ingrese las credenciales correctas",Toast.LENGTH_SHORT).show()

             }else{


                 Toast.makeText(this,"Registrese si no tiene un usuario",Toast.LENGTH_SHORT).show()
             }*/
        }

        btn_regis.setOnClickListener {
            var regis=Intent(this,AgregarUsuarios::class.java)
            startActivity(regis)
        }
    }
    fun list(Usu:Usuario){
        var session:Session
        session=Session(applicationContext)
        session.createLoginSession(Usu.user,Usu.pass)
        val login= Intent(this,ListaLibros::class.java)

        login.putExtra("nombre",Usu.nombre)
        login.putExtra("codigo",Usu.codigo.toString())
        login.putExtra("nombreusu",Usu.nombre)
        login.putExtra("runusu",Usu.run)
        startActivity(login)
    }
    fun ingre(){
        Toast.makeText(this,"ingrese las credenciales correctas",Toast.LENGTH_SHORT).show()
    }


}
