package es.javiercarrasco.myimc

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import es.javiercarrasco.myimc.databinding.ActivityMainBinding

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
                    val imc: Double
                    if (binding.rbHombre.isChecked) {
                        imc = MyFunctions().obtenerIMC(
                            binding.etPeso.text.toString().toDouble(),
                            binding.etAltura.text.toString().toDouble(),
                            binding.rbHombre.text.toString()
                        )
                        binding.tvIMCText.text =
                            MyFunctions().detalleIMC(this, imc, binding.rbHombre.text.toString())
                    } else {
                        imc = MyFunctions().obtenerIMC(
                            binding.etPeso.text.toString().toDouble(),
                            binding.etAltura.text.toString().toDouble(),
                            binding.rbMujer.text.toString()
                        )
                        binding.tvIMCText.text =
                            MyFunctions().detalleIMC(this, imc, binding.rbMujer.text.toString())
                    }
                    binding.tvIMC.text = String.format("%.2f", imc)
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
}