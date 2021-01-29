package es.javiercarrasco.myimcv4.adapters

import android.content.Context
import android.database.Cursor
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import es.javiercarrasco.myimcv4.MainActivity
import es.javiercarrasco.myimcv4.R
import es.javiercarrasco.myimcv4.data.MyIMC
import es.javiercarrasco.myimcv4.databinding.ItemImcBinding
import es.javiercarrasco.myimcv4.utils.MyFunctions

class MyRecyclerAdapter : RecyclerView.Adapter<MyRecyclerAdapter.ViewHolder>() {

    private lateinit var context: Context
    private lateinit var imcsCursor: Cursor

    fun MyRecyclerAdapter(context: Context, imcs: Cursor) {
        this.context = context
        this.imcsCursor = imcs
        Log.d("RV", "constructor")
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Log.d("RV", "onBindViewHolder")
        // Se coloca el cursor en la siguietne posición a leer.
        imcsCursor.moveToPosition(position)
        holder.bind()
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
        return if (imcsCursor != null)
            imcsCursor.count
        else 0
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = ItemImcBinding.bind(view)

        fun bind() {
            Log.d("RV", "en el bind")

            binding.tvID.text = imcsCursor.getString(0)
            binding.tvMes.text =
                context.resources.getStringArray(R.array.meses)[imcsCursor.getString(1)!!.split("-")[1].toInt() - 1]
            binding.tvDia.text = imcsCursor.getString(1)!!.split("-")[0]
            binding.tvAnyo.text = imcsCursor.getString(1)!!.split("-")[2]
            binding.tvSexo.text = imcsCursor.getString(2)
            binding.tvIMChistorico.text = String.format("%.2f", imcsCursor.getString(3)!!.toDouble())
            binding.tvEstado.text = imcsCursor.getString(6)
            binding.tvPesoItemList.text = context.getString(
                R.string.labelPesoItem,
                String.format("%.1f", imcsCursor.getString(4)!!.toDouble())
            )
            binding.tvAlturaItemList.text = context.getString(
                R.string.labelAlturaItem,
                String.format("%.1f", imcsCursor.getString(5)!!.toDouble())
            )

            itemView.setOnClickListener {
                MyFunctions().showSimpleSnackBar(
                    binding.root,
                    "${binding.tvEstado.text} (${binding.tvIMChistorico.text})"
                )
            }

            // Pulsación larga para eliminar.
            itemView.setOnLongClickListener {
                MyFunctions().deleteItemRV(context, it, adapterPosition)

                // Este evento necesita devolver un boolean para notificar si
                // realmente se ha producido el evento completamente.
                return@setOnLongClickListener true
            }
        }
    }
}