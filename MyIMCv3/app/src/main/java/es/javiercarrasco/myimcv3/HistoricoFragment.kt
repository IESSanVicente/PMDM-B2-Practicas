package es.javiercarrasco.myimcv3

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import es.javiercarrasco.myimcv3.databinding.FragmentHistoricoBinding
import es.javiercarrasco.myimcv3.utils.MyFunctions

class HistoricoFragment : Fragment() {

    private lateinit var binding: FragmentHistoricoBinding
    private val myAdapter: MyRecyclerAdapter = MyRecyclerAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentHistoricoBinding.inflate(layoutInflater, container, false)

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
        binding.tvInfo.isVisible = true

        if (context!!.fileList().contains(getString(R.string.filename))) {
            binding.tvInfo.isVisible = false
            myAdapter.MyRecyclerAdapter(context!!, MyFunctions().readFile(context!!))
            binding.rvIMCs.setHasFixedSize(true)
            binding.rvIMCs.layoutManager = LinearLayoutManager(context!!)
            binding.rvIMCs.adapter = myAdapter
        }
    }
}