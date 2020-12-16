package es.javiercarrasco.myimc

import android.content.Context

class MyFunctions {
    fun dividir(a: Double, b: Int) = a / b
    fun cuadrado(alt: Double): Double = alt * alt

    fun obtenerIMC(peso: Double, altura: Double, sexo: String): Double {
        // Se pasan los centímetros a metros.
        val altPorDos = dividir(altura, 100)

        return peso / cuadrado((altPorDos))
    }

    /**
     * Composición corporal Índice de masa corporal (IMC)
     * Peso inferior al normal < 18.5
     * Normal >= 18.5 && <= 24.9(H) || <= 23.9(M)
     * Peso superior al normal >= 25.0 && <=29.9(H) || >=24 && <=28.9(M)
     * Obesidad Más de > 30.0(H) || >29.0(M)
     */
    fun detalleIMC(context:Context, imc: Double, sexo: String): String {
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