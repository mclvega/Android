package mcl.vega.appusuario

import android.app.AlertDialog
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.graphics.BitmapFactory
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kotlinx.android.synthetic.main.activity_lista_libros.*
import kotlinx.android.synthetic.main.activity_lista_prestamos.*
import mcl.vega.appusuario.Clases.*
import org.json.JSONArray
import org.json.JSONException
import java.text.SimpleDateFormat
import java.time.Instant
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*
import java.util.regex.Matcher
import java.util.regex.Pattern
import kotlin.collections.ArrayList

class ListaPrestamos : AppCompatActivity(), Json.Obtener.OnTaskCompleted {

    lateinit var nm:NotificationManager
    lateinit var nc:NotificationChannel
    lateinit var builder: Notification.Builder
    var ci="mcl.vega.appadm"
    var desc="notificacion prueba"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lista_prestamos)

        var run=intent.getStringExtra("runusu")
        nm=getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        var tarea=Json.Obtener("",this)
        tarea.execute("http://librosmcl.somee.com/servicios.svc/Listarprestamosr/$run")

        btn_updp.setOnClickListener {
            var tarea=Json.Obtener("",this)
            tarea.execute("http://librosmcl.somee.com/servicios.svc/Listarprestamosr/$run")
        }

    }

    override fun onTaskCompleted(response: String) {
        if (!response.equals("Error")) {
            try {

                var lista = ArrayList<Prestamos>()
                var jsonData = JSONArray(response)
                for (i in 0..jsonData.length() - 1) {

                    var prestamo = jsonData.getJSONObject(i)
                    var codigo = prestamo.getInt("codigo")
                    var usuario = prestamo.getInt("usuario")
                    var libro = prestamo.getInt("libro")
                    var nombreL = prestamo.getString("nombre_libro")
                    var nomb = prestamo.getString("nombre")
                    var run = prestamo.getString("run")
                    var fecha_dev=prestamo.getString("fecha_devolucion")
                    var fecha_pre=prestamo.getString("fecha_prestamo")
                    lista.add(Prestamos(codigo,usuario,libro,fecha_dev,fecha_pre, run, nomb,nombreL))

                    var pat: Pattern = Pattern.compile("/Date\\((\\d+)([+-]\\d{4})\\)/")
                    var m: Matcher =pat.matcher((fecha_dev))

                    if(m.matches()){
                        var i: Instant = Instant.ofEpochMilli(m.group(1).toLong())
                        val form=DateTimeFormatter.ofPattern("dd-MM-yyyy")
                        var d=Date.from(i)
                        val sdf=SimpleDateFormat("dd-MM-yyyy")
                        val current=sdf.format(Date())
                        var f=SimpleDateFormat("dd-MM-yyyy").format(d).toString()
                        if("20-11-2019"==current){

                            nc=NotificationChannel(ci,desc,NotificationManager.IMPORTANCE_HIGH)
                            nc.enableLights(true)
                            nc.lightColor= Color.GREEN
                            nc.enableVibration(true)
                            nm.createNotificationChannel(nc)
                            builder=Notification.Builder(this,ci)
                                .setContentTitle("Libro pendiente")
                                .setContentText("el usuario $nomb tiene fecha de devolucion $f del libro $nombreL")
                                .setSmallIcon(R.mipmap.ic_launcher_round)
                                .setLargeIcon(BitmapFactory.decodeResource(this.resources,R.mipmap.ic_launcher))

                            nm.notify(123,builder.build())
                        }
                    }


                }

                var adap = CustomAdapterPrestamos(this, android.R.layout.simple_list_item_1, lista)
                lv_prestamos.adapter = adap
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
