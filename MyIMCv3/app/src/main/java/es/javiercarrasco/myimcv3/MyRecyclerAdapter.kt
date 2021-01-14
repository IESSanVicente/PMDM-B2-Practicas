package es.javiercarrasco.myimcv3

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import es.javiercarrasco.myimcv3.data.MyIMC
import es.javiercarrasco.myimcv3.databinding.ItemImcBinding
import es.javiercarrasco.myimcv3.utils.MyFunctions

class MyRecyclerAdapter : RecyclerView.Adapter<MyRecyclerAdapter.ViewHolder>() {

    private var imcsList: MutableList<MyIMC> = ArrayList()
    private lateinit var context: Context

    fun MyRecyclerAdapter(context: Context, imcs: MutableList<MyIMC>) {
        this.context = context
        this.imcsList = imcs
        Log.d("RV", "constructor")
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Log.d("RV", "onBindViewHolder")
        val item = imcsList.get(position)
        holder.bind(item)
    }

    // Devuelve el ViewHolder ya configurado.
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        Log.d("RV", "onCreateViewHolder")
        return ViewHolder(
            ItemImcBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ).root,
        )
    }

    // Devuelve el tamaño de la lista.
    override fun getItemCount(): Int {
        return imcsList.size
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = ItemImcBinding.bind(view)

        fun bind(imc: MyIMC) {
            Log.d("RV", "en el bind")

            binding.tvMes.text =
                context.resources.getStringArray(R.array.meses)[imc.fecha!!.split("-")[1].toInt() - 1]
            binding.tvDia.text = imc.fecha!!.split("-")[0]
            binding.tvAnyo.text = imc.fecha!!.split("-")[2]
            binding.tvSexo.text = imc.sexo
            binding.tvIMChistorico.text = String.format("%.2f", imc.imc)
            binding.tvEstado.text = imc.estado
            binding.tvPesoItemList.text =
                context.getString(R.string.labelPesoItem, String.format("%.1f", imc.peso))
            binding.tvAlturaItemList.text =
                context.getString(R.string.labelAlturaItem, String.format("%.1f", imc.altura))

            itemView.setOnClickListener {
                MyFunctions().showSimpleSnackBar(
                    binding.root,
                    "${binding.tvEstado.text} (${binding.tvIMChistorico.text})"
                )
            }
        }
    }
}