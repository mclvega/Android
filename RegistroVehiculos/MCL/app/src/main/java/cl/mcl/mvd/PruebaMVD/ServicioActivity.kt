package cl.mcl.mvd.PruebaMVD

import android.app.DatePickerDialog
import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import cl.mcl.mvd.PruebaMVD.Clases.ListaCliente
import cl.mcl.mvd.PruebaMVD.Clases.Vehiculos
import kotlinx.android.synthetic.main.activity_servicio.*
import kotlinx.android.synthetic.main.activity_servicio.view.*
import kotlinx.android.synthetic.main.fragment_agregar.*
import kotlinx.android.synthetic.main.servicios_layout.view.*
import java.util.*

class ServicioActivity : AppCompatActivity() {
    var miContexto: Context? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_servicio)
        //   val c = Calendar.getInstance()

        var numero = intent.getStringExtra("position")
        var ru = intent.getStringExtra("run")
        var nombre = intent.getStringExtra("nombre")
        var dire = intent.getStringExtra("direccion")
        var fecha = intent.getStringExtra("fecha")
        var genero = intent.getStringExtra("genero")
        var telefono = intent.getStringExtra("telefono")

        txv_runC.text = ru.toString()
        txv_direccionC.text = dire.toString()
        txv_nombreC.text = nombre.toString()
        txv_fechaC.text = fecha.toString()
        txv_generoC.text = genero.toString()
        txv_telefonoC.text = telefono.toString()
        adaptarRecycler(numero.toInt())


        /* btnHora.setOnClickListener{
            val hora = c.get(Calendar.HOUR_OF_DAY)
            val minuto = c.get(Calendar.MINUTE)
            val timeSetListener = TimePickerDialog.OnTimeSetListener{
                timePicker: TimePicker?, hourOfDay: Int, minute: Int ->
                c.set(Calendar.HOUR_OF_DAY,hourOfDay)
                c.set(Calendar.MINUTE,minute)
                lblHora.text = SimpleDateFormat("HH:mm").format(c.time)
            }
            TimePickerDialog(this,
                    timeSetListener,hora,minuto,true).show()

        }
        */



        rg_tipo.setOnCheckedChangeListener { group, checkedId ->
            when (checkedId) {
                R.id.rb_auto -> {
                    txtPatente.visibility = View.VISIBLE
                    txtMarca.visibility = View.VISIBLE
                    txtModelo.visibility = View.VISIBLE
                    txtAnio.visibility = View.VISIBLE
                    txtCantPuertas.visibility = View.VISIBLE
                    txtKilometros.visibility = View.VISIBLE
                    txtCantCarga.visibility = View.GONE
                    lbl_camarote.visibility = View.GONE
                    cbCamarote.visibility = View.GONE
                }

                R.id.rb_camion -> {
                    txtPatente.visibility = View.VISIBLE
                    txtMarca.visibility = View.VISIBLE
                    txtModelo.visibility = View.VISIBLE
                    txtAnio.visibility = View.VISIBLE
                    txtCantCarga.visibility = View.VISIBLE
                    lbl_camarote.visibility = View.VISIBLE
                    cbCamarote.visibility = View.VISIBLE
                    txtCantPuertas.visibility = View.GONE
                    txtKilometros.visibility = View.GONE

                }
            }
        }

        var elementos2: ArrayList<String> = ArrayList()
        elementos2.add("si")
        elementos2.add("no")
        cbCamarote.adapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, elementos2)

        btnRegistrarServicio.setOnClickListener {

            if (!rb_auto.isChecked && !rb_camion.isChecked) {
                Toast.makeText(miContexto, "Seleccione un tipo de cliente.", Toast.LENGTH_LONG).show()
            }
            if (rb_auto.isChecked) {
                var patente: String = txtPatente.text.toString()
                var marca: String = txtMarca.text.toString()
                var anio: String = txtAnio.text.toString()
                var modelo: String = txtModelo.text.toString()
                var cantP: String = txtCantPuertas.text.toString()
                var kilome: String = txtKilometros.text.toString()
                if (patente.isEmpty()) {
                    Toast.makeText(this, "Agregue una patente.", Toast.LENGTH_LONG).show()
                } else if (marca.isEmpty()) {
                    Toast.makeText(this, "Agregue una marca.", Toast.LENGTH_LONG).show()
                } else if (modelo.isEmpty()) {
                    Toast.makeText(this, "Agregue un modelo.", Toast.LENGTH_LONG).show()
                } else if (anio.isEmpty()) {
                    Toast.makeText(this, "Agregue un año de fabricacion.", Toast.LENGTH_LONG).show()
                } else if (telefono.isEmpty()) {
                    Toast.makeText(this, "Agregue un telefono.", Toast.LENGTH_LONG).show()
                } else if (cantP.isEmpty()) {
                    Toast.makeText(this, "Agregue cantidad de puertas.", Toast.LENGTH_LONG).show()
                } else if (kilome.isEmpty()) {
                    Toast.makeText(this, "Agregue el kilometraje.", Toast.LENGTH_LONG).show()
                } else {
                    ListaCliente.lista[numero.toInt()].lista.add(Vehiculos("Automovil", patente, marca, modelo, anio.toInt(), cantP.toInt(), kilome.toInt(), 0, false))

                    adaptarRecycler(numero.toInt())
                    Toast.makeText(this, "Agregado Correctamente", Toast.LENGTH_LONG).show()
                }


            }else if(rb_camion.isChecked){
                var patente: String = txtPatente.text.toString()
                var marca: String = txtMarca.text.toString()
                var anio: String = txtAnio.text.toString()
                var modelo: String = txtModelo.text.toString()
                  var cantC: String = txtCantCarga.text.toString()

                if (patente.isEmpty()) {
                    Toast.makeText(this, "Agregue una patente.", Toast.LENGTH_LONG).show()
                } else if (marca.isEmpty()) {
                    Toast.makeText(this, "Agregue una marca.", Toast.LENGTH_LONG).show()
                } else if (modelo.isEmpty()) {
                    Toast.makeText(this, "Agregue un modelo.", Toast.LENGTH_LONG).show()
                } else if (anio.isEmpty()) {
                    Toast.makeText(this, "Agregue un año de fabricacion.", Toast.LENGTH_LONG).show()
                } else if (telefono.isEmpty()) {
                    Toast.makeText(this, "Agregue un telefono.", Toast.LENGTH_LONG).show()
                } else if (cantC.isEmpty()) {
                    Toast.makeText(this, "Agregue cantidad de carga.", Toast.LENGTH_LONG).show()
                }  else {
                    if(cbCamarote.selectedItem.toString()=="si"){
                        ListaCliente.lista[numero.toInt()].lista.add(Vehiculos("Camion", patente, marca, modelo, anio.toInt(),
                                0, 0, cantC.toInt(), true))
                    }else if (cbCamarote.selectedItem.toString()=="no"){
                        ListaCliente.lista[numero.toInt()].lista.add(Vehiculos("Camion", patente, marca, modelo, anio.toInt(),
                                0, 0, cantC.toInt(), false))

                    }


                    adaptarRecycler(numero.toInt())
                    Toast.makeText(this, "Agregado Correctamente", Toast.LENGTH_LONG).show()
                }
            }

        }
    }
        fun adaptarRecycler(numero: Int) {
            var adaptador: CustomAdapterServicio = CustomAdapterServicio(this, ListaCliente.lista[numero.toInt()].lista)
            rvServicios.layoutManager = LinearLayoutManager(this, LinearLayout.VERTICAL, false)
            rvServicios.adapter = adaptador
        }


        class CustomAdapterServicio(val miContexto: Context, val miLista: ArrayList<Vehiculos>) : RecyclerView.Adapter<CustomAdapterServicio.CustomViewHolder>() {
            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
                val v: View = LayoutInflater.from(miContexto).inflate(R.layout.servicios_layout, parent, false)
                return CustomViewHolder(v)
            }

            override fun getItemCount(): Int {
                return miLista.size
            }

            override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
                holder.bindData(miLista[position])
            }

            class CustomViewHolder(val miView: View) : RecyclerView.ViewHolder(miView) {
                fun bindData(vh: Vehiculos) {
                    miView.txtTipoV.text = "Tipo Vehiculo: ${vh.tipo}"
                    miView.txtPatenteV.text = "Patente: ${vh.patente}"
                    miView.txtMarcaV.text = "Marca: ${vh.marca}"
                    miView.txtAnioV.text = "Año de Fabricacion: ${vh.anio_fabricacion}"
                    miView.txtModeloV.text = "Modelo: ${vh.modelo}"
                    if (vh.tipo.toString() == "Automovil") {
                        miView.txt1.text = "Cantidad de Puertas: ${vh.cantidad_puertas}"
                        miView.txt2.text = "Kilometraje: ${vh.cantidad_kilometros}"


                    } else {
                        miView.txt1.text = "Cantidad de Carga: ${vh.cantidad_carga}"
                        if (vh.camarote == true) {
                            miView.txt2.text = "Si tiene Camarote"
                        } else {
                            miView.txt2.text = "No tiene Camarote"
                        }
                    }
                }
            }
        }
    }
