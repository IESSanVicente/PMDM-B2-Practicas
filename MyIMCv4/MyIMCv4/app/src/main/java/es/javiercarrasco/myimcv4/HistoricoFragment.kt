package es.javiercarrasco.myimcv4

import android.database.Cursor
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import es.javiercarrasco.myimcv4.MainActivity.Companion.myAdapter
import es.javiercarrasco.myimcv4.databinding.FragmentHistoricoBinding
import es.javiercarrasco.myimcv4.utils.MyFunctions
import es.javiercarrasco.myimcv4.utils.MyIMCDBOpenHelper


class HistoricoFragment() : Fragment() {

    private lateinit var binding: FragmentHistoricoBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentHistoricoBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onStart() {
        super.onStart()

        cargarDatos()
    }

    override fun onResume() {
        super.onResume()


        cargarDatos()
    }

    private fun cargarDatos() {
        val datos = MyFunctions().getMyCursor()
        Log.d("CargarDatos", datos.count.toString())

        if (datos.count > 0) {
            binding.tvInfo.isVisible = false
            binding.rvIMCs.isVisible = true
            myAdapter.MyRecyclerAdapter(context!!, datos)
            binding.rvIMCs.setHasFixedSize(true)
            binding.rvIMCs.layoutManager = LinearLayoutManager(context!!)
            binding.rvIMCs.adapter = myAdapter
        } else {
            binding.tvInfo.isVisible = true
            binding.rvIMCs.isVisible = false
        }
    }
}