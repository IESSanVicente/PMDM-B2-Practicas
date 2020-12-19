package es.javiercarrasco.myimcv2

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import es.javiercarrasco.myimcv2.data.MyIMC
import es.javiercarrasco.myimcv2.databinding.ActivityMainBinding
import es.javiercarrasco.myimcv2.utils.MyFunctions

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnCalcular.setOnClickListener {
            binding.tvIMC.text = ""
            binding.tvIMCText.text = ""

            if ((binding.etPeso.text.isNotEmpty()) && (binding.etAltura.text.isNotEmpty())) {
                binding.tvIMC.text = getString(R.string.textZero)

                if ((binding.etPeso.text.toString()
                        .toDouble() > 0.00) && (binding.etAltura.text.toString().toDouble() > 0.00)
                ) {
                    val datosIMC = MyIMC()

                    datosIMC.fecha = MyFunctions().getFecha()

                    datosIMC.imc = MyFunctions().obtenerIMC(
                        binding.etPeso.text.toString().toDouble(),
                        binding.etAltura.text.toString().toDouble()
                    )

                    if (binding.rbHombre.isChecked) {
                        datosIMC.sexo = binding.rbHombre.text.toString()
                        datosIMC.estado = MyFunctions().detalleIMC(
                            this,
                            datosIMC.imc!!,
                            binding.rbHombre.text.toString()
                        )
                    } else {
                        datosIMC.sexo = binding.rbMujer.text.toString()
                        datosIMC.estado = MyFunctions().detalleIMC(
                            this,
                            datosIMC.imc!!,
                            binding.rbMujer.text.toString()
                        )
                    }

                    binding.tvIMC.text = String.format("%.2f", datosIMC.imc!!)
                    binding.tvIMCText.text = datosIMC.estado

                    MyFunctions().writeFile(this, datosIMC)
                } else {
                    Toast.makeText(
                        this,
                        R.string.msgErrorZeros,
                        Toast.LENGTH_LONG
                    ).show()
                }
            } else {
                Toast.makeText(
                    this,
                    R.string.msgErrorInputs,
                    Toast.LENGTH_LONG
                ).show()
            }
        }

    }

    override fun onStart() {
        super.onStart()

        binding.btnHistorico.setOnClickListener {
            val intentHistorico = Intent(this, HistoricoActivity::class.java)
            startActivity(intentHistorico)
        }
    }
}