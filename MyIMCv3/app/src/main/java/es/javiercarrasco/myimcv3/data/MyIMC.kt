package es.javiercarrasco.myimcv3.data

class MyIMC {
    var fecha: String? = null
    var sexo: String? = null
    var imc: Double? = null
        set(value) {
            field = if (value!! > 0.00) value else 0.00
        }

    var peso: Double? = null
        set(value) {
            field = if (value!! > 0.00) value else 0.00
        }

    var altura: Double? = null
        set(value) {
            field = if (value!! > 0.00) value else 0.00
        }

    var estado: String? = null
}
