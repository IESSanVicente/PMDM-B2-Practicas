package es.javiercarrasco.myimc

import android.content.Context

class MyFunctions {

    fun obtenerIMC(peso: Double, altura: Double): Double {
        // Se pasa la altura de centímetros a metros.
        return peso / Math.pow((altura / 100), 2.00)
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
}