package es.javiercarrasco.myimcv4.utils

import android.content.Context
import android.database.Cursor
import android.util.Log
import android.view.View
import androidx.appcompat.app.AlertDialog
import com.google.android.material.snackbar.Snackbar
import es.javiercarrasco.myimcv4.MainActivity
import es.javiercarrasco.myimcv4.R
import es.javiercarrasco.myimcv4.databinding.ItemImcBinding
import java.util.*
import kotlin.math.pow

class MyFunctions {

    fun obtenerIMC(peso: Double, altura: Double): Double {
        // Se pasa la altura de centímetros a metros.
        return peso / (altura / 100).pow(2.00)
    }

    /**
     * Devuelve un cursor con el listado completo de los datos.
     */
    fun getMyCursor(): Cursor {
        val db = MainActivity.imcDBHelper.readableDatabase

        val cursor = db.rawQuery(
            "SELECT * FROM ${MyIMCDBOpenHelper.TABLA_IMC};",
            null
        )

        return cursor
    }

    /**
     * Método encargado de eliminar el item del RV y su correspondiente registro
     * de la base de datos.
     */
    fun deleteItemRV(context: Context, view: View, position: Int) {
        val builder = AlertDialog.Builder(context)

        val bindView = ItemImcBinding.bind(view)
        // Se crea el AlertDialog.
        builder.apply {
            // Se asigna un título.
            setTitle(R.string.titleDelete)
            // Se asgina el cuerpo del mensaje.
            setMessage(
                context.getString(
                    R.string.msgDelete, bindView.tvIMChistorico.text
                    //String.format("%.2f", bindView.tvIMChistorico.text.toString().toDouble())
                    //bind.fecha!!.replace("-", "/")
                )
            )
            // Se define el comportamiento de los botones
            setPositiveButton(android.R.string.ok) { _, _ ->
                // Se elimina el registro de la BD.
                val result = MainActivity.imcDBHelper.delIMC(bindView.tvID.text.toString().toInt())

                // Si realmente se ha producido una eliminación en la base de datos
                // result contendrá un valor mayor que 0, en tal caso se actualiza
                // el adapter del RV.
                if (result > 0) {
                    Log.d(
                        "RV",
                        "DELETE: Accept ID: ${
                            bindView.tvID.text.toString().toInt()
                        } POS: ${position}"
                    )

                    // Actualiza el cursor asignado al adapter.
                    MainActivity.myAdapter.MyRecyclerAdapter(context, getMyCursor())

                    // Se notifica la eliminación.
                    MainActivity.myAdapter.notifyItemRemoved(position)
                    MainActivity.myAdapter.notifyDataSetChanged()

                    return@setPositiveButton
                }
            }
            setNegativeButton(android.R.string.no) { _, _ ->
                Log.d("RV", "DELETE: Cancel")
                return@setNegativeButton
            }
        }.show()
    }

    /**
     * Composición corporal Índice de masa corporal (IMC)
     * Peso inferior al normal < 18.5
     * Normal >= 18.5 && <= 24.9(H) || <= 23.9(M)
     * Peso superior al normal >= 25.0 && <=29.9(H) || >=24 && <=28.9(M)
     * Obesidad Más de > 30.0(H) || >29.0(M)
     */
    fun detalleIMC(context: Context, imc: Double, sexo: String): String {
        if (sexo.equals(context.getString(R.string.radioButtonHombre))) {
            when {
                imc < 18.5 -> return "Peso inferior al normal"
                imc in 18.5..24.9 -> return "Normal"
                imc in 25.0..29.9 -> return "Sobrepeso"
                imc > 30.0 -> return "Obesidad"
                else -> return "No hay datos suficientes"
            }
        } else {
            when {
                imc < 18.5 -> return "Peso inferior al normal"
                imc in 18.5..23.9 -> return "Normal"
                imc in 24.0..28.9 -> return "Sobrepeso"
                imc > 29.0 -> return "Obesidad"
                else -> return "No hay datos suficientes"
            }
        }
    }

    fun getFecha(): String {
        val hoy = Calendar.getInstance()
        // ENERO - 0, FEBRERO - 1, ..., DICIEMBRE - 11
        return "${hoy.get(Calendar.DAY_OF_MONTH)}" +
                "-${hoy.get(Calendar.MONTH) + 1}" +
                "-${hoy.get(Calendar.YEAR)}"
    }

    fun showSimpleSnackBar(view: View, msg: String) {
        Snackbar.make(
            view,
            msg,
            Snackbar.LENGTH_LONG
        ).show()
    }
}