package es.javiercarrasco.myimcv2.data

class MyIMC {
    var fecha: String? = null
    var sexo: String? = null
    var imc: Double? = null
        set(value) {
            field = if (value!! > 0.00) value else 0.00
        }

    var estado: String? = null
}
