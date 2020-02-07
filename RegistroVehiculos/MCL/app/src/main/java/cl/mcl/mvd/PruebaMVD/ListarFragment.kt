package cl.mcl.mvd.PruebaMVD

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import cl.mcl.mvd.PruebaMVD.Clases.CustomAdapter
import cl.mcl.mvd.PruebaMVD.Clases.ListaCliente
import cl.mcl.mvd.PruebaMVD.R


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [ListarFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [ListarFragment.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class ListarFragment : Fragment() {

    var miContexto: Context? = activity
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val v = inflater.inflate(R.layout.fragment_listar, container, false)
        var adaptador = CustomAdapter(miContexto!!, ListaCliente.lista)
        var recycler: RecyclerView = v.findViewById(R.id.rvLista)
        recycler.layoutManager = LinearLayoutManager(miContexto!!, LinearLayout.VERTICAL,false)
        recycler.adapter = adaptador
        return v
    }

}
