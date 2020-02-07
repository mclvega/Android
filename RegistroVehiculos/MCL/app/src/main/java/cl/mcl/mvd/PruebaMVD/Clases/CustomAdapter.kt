package cl.mcl.mvd.PruebaMVD.Clases

import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import cl.mcl.mvd.PruebaMVD.R
import cl.mcl.mvd.PruebaMVD.ServicioActivity
import kotlinx.android.synthetic.main.lista_clientes_layout.view.*

class CustomAdapter(val miContexto: Context, val miLista: ArrayList<Cliente>)
    : RecyclerView.Adapter<CustomAdapter.CustomViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val v: View = LayoutInflater.from(miContexto).inflate(R.layout.lista_clientes_layout,parent,false)
        return CustomViewHolder(v)
    }

    override fun getItemCount(): Int {
        return ListaCliente.lista.size
    }




    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        holder.binData(ListaCliente.lista[position])

        holder.miView.setOnClickListener({
            var intento = Intent(miContexto, ServicioActivity::class.java)
            intento.putExtra("position",position.toString())
            intento.putExtra("run", ListaCliente.lista[position].run)
            intento.putExtra("nombre", ListaCliente.lista[position].nombre)
            intento.putExtra("direccion", ListaCliente.lista[position].direccion)
            intento.putExtra("fecha", ListaCliente.lista[position].fecha_nacimiento)
            intento.putExtra("telefono", ListaCliente.lista[position].telefono.toString())
            intento.putExtra("genero", ListaCliente.lista[position].genero)
            miContexto.startActivity(intento)
        })
    }

    class CustomViewHolder(val miView: View): RecyclerView.ViewHolder(miView)
    {
        fun binData(cliente: Cliente)
        {
            miView.txtRunLista.text = "Run: ${cliente.run}"
            miView.txtNombreLista.text = "Nombre: ${cliente.nombre}"
        }
    }
}