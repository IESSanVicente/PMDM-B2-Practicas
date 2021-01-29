package es.javiercarrasco.myimcv4b.utils

import android.content.Context
import android.view.View
import com.google.android.material.snackbar.Snackbar
import es.javiercarrasco.myimcv4b.R
import java.util.*
import kotlin.math.pow

class MyFunctions {

    fun obtenerIMC(peso: Double, altura: Double): Double {
        // Se pasa la altura de centímetros a metros.
        return peso / (altura / 100).pow(2.00)
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