package es.javiercarrasco.myimcv4b

import android.database.Cursor
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import es.javiercarrasco.myimcv4b.MainActivity.Companion.myAdapter
import es.javiercarrasco.myimcv4b.databinding.FragmentHistoricoBinding
import es.javiercarrasco.myimcv4b.utils.MyIMCDBOpenHelper


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
        val datos = getMyCursor()
        Log.d("CargarDatos", datos.count.toString())

        if (datos.count > 0) {
            setItemTouchHelper()
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

    fun getMyCursor(): Cursor {
        val db = MainActivity.imcDBHelper.readableDatabase

        val cursor = db.rawQuery(
            "SELECT * FROM ${MyIMCDBOpenHelper.TABLA_IMC};",
            null
        )

        return cursor
    }

    // Activa el deslizado de los elementos de la lista.
    private fun setItemTouchHelper() {
        val background = ColorDrawable(Color.rgb(255, 150, 150))
        val icon = resources.getDrawable(
            android.R.drawable.ic_menu_delete,
            null
        )

        val itemTouchCallbak = object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            override fun onChildDraw(
                c: Canvas,
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                dX: Float,
                dY: Float,
                actionState: Int,
                isCurrentlyActive: Boolean
            ) {
                super.onChildDraw(
                    c,
                    recyclerView,
                    viewHolder,
                    dX,
                    dY,
                    actionState,
                    isCurrentlyActive
                )

                val itemView = viewHolder.itemView
                // Se utiliza para indicar el valor de solapamiento del fondo
                // con el item del RV.
                val backgroundCornerOffset = 20

                val iconMargin = (itemView.height - icon.intrinsicHeight) / 2
                val iconTop = itemView.top + (itemView.height - icon.intrinsicHeight) / 2
                val iconBottom = iconTop + icon.intrinsicHeight

                if (dX < 0) { // Swipe hacia la izquierda
                    Log.d("Swiped", "onChildDraw")

                    val iconLeft = itemView.right - iconMargin - icon.intrinsicWidth
                    val iconRight = itemView.right - iconMargin
                    icon.setBounds(iconLeft, iconTop, iconRight, iconBottom)

                    background.setBounds(
                        itemView.right + dX.toInt() - backgroundCornerOffset,
                        itemView.top, itemView.right, itemView.bottom
                    )
                } else {
                    background.setBounds(0, 0, 0, 0)
                }

                background.draw(c)
                icon.draw(c)
            }

            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                // Se obtiene el _id del item a eliminar.
                val ident = viewHolder.itemView.findViewById(R.id.tvID) as TextView

                Log.d(
                    "RV",
                    "DELETE: Accept ID: ${ident.text} POS: ${viewHolder.adapterPosition} SIZE: ${binding.rvIMCs.adapter!!.itemCount}"
                )
                Log.d("Swiped", "Desplazado ${ident.text}")

                MainActivity.imcDBHelper.delIMC(ident.text.toString().toInt())

                // Se recarga nuevamente el Adapter.
                myAdapter.MyRecyclerAdapter(context!!, getMyCursor())
                
                // Se notica el cambio en el adapter.
                binding.rvIMCs.adapter!!.notifyDataSetChanged()
            }
        }

        val itemTouchHelper = ItemTouchHelper(itemTouchCallbak)
        itemTouchHelper.attachToRecyclerView(binding.rvIMCs)
    }
}