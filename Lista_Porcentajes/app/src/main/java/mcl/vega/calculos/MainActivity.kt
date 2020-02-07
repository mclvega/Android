package mcl.vega.calculos

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var lista:ArrayList<Int> = ArrayList<Int>()
        var n=""
        var t=0
        var td=0
        var tv=0
        btn_clear.visibility=View.INVISIBLE
        btn_add.setOnClickListener {

            n=txt_numero.text.toString()
            if(n.toString()!=""){

                lista.add(n.toInt())
                t=t+n.toInt()
                td=(t*0.10).toInt()
                tv=(t*0.20).toInt()

                var adap:ArrayAdapter<Int> = ArrayAdapter(this,android.R.layout.simple_list_item_1,lista)
                lv_lista.adapter=adap
                lbl_total.text="Total :$ $t"
                lbl_10.text="10% :$ $td"
                lbl_20.text="20% :$ $tv"
                txt_numero.setText("")
                if(lista!=null){
                    btn_clear.visibility=View.VISIBLE
                }

            }
            else{
                Toast.makeText(this,"Ingrese un valor",Toast.LENGTH_SHORT).show()
            }

        }

        btn_clear.setOnClickListener {
            lista.clear()
            btn_clear.visibility=View.INVISIBLE
            n=""
            t=0
            td=0
            tv=0
            var adap:ArrayAdapter<Int> = ArrayAdapter(this,android.R.layout.simple_list_item_1,lista)
            lv_lista.adapter=adap
            lbl_total.text=""
            lbl_10.text=""
            txt_numero.setText("")
            lbl_20.text=""
        }
    }
}
