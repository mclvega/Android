package mcl.vega.appusuario

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_lista_libros_favoritos.*
import mcl.vega.appusuario.Clases.CustomAdapterLibrosFav

class ListaLibrosFavoritos : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lista_libros_favoritos)
        var run:String?=intent.getStringExtra("run")
        val custom=BDD(this,"miBD",null,1)
        val lista=custom.listarLibrosFav(run!!)
        val adap=CustomAdapterLibrosFav(this,android.R.layout.simple_list_item_1,lista)
        lv_fav.adapter=adap
    }
}
