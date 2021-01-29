package es.javiercarrasco.myimcv4b

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import es.javiercarrasco.myimcv4b.data.MyIMC
import es.javiercarrasco.myimcv4b.databinding.FragmentImcBinding
import es.javiercarrasco.myimcv4b.utils.MyDialogFragment
import es.javiercarrasco.myimcv4b.utils.MyFunctions

class ImcFragment:Fragment() {

    private lateinit var binding: FragmentImcBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentImcBinding.inflate(layoutInflater, container,false)

        return binding.root
    }

    override fun onStart() {
        super.onStart()

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

                    datosIMC.peso = binding.etPeso.text.toString().toDouble()
                    datosIMC.altura = binding.etAltura.text.toString().toDouble()

                    datosIMC.imc = MyFunctions().obtenerIMC(
                        datosIMC.peso!!,
                        datosIMC.altura!!
                    )

                    if (binding.rbHombre.isChecked) {
                        datosIMC.sexo = binding.rbHombre.text.toString()
                        datosIMC.estado = MyFunctions().detalleIMC(
                            it.context,
                            datosIMC.imc!!,
                            binding.rbHombre.text.toString()
                        )
                    } else {
                        datosIMC.sexo = binding.rbMujer.text.toString()
                        datosIMC.estado = MyFunctions().detalleIMC(
                            it.context,
                            datosIMC.imc!!,
                            binding.rbMujer.text.toString()
                        )
                    }

                    binding.tvIMC.text = String.format("%.2f", datosIMC.imc!!)
                    binding.tvIMCText.text = datosIMC.estado

                    val myDialogFragment = MyDialogFragment(datosIMC)
                    myDialogFragment.show(fragmentManager!!, "SaveFile")

                } else {
                    MyFunctions().showSimpleSnackBar(
                        binding.root,
                        getString(R.string.msgErrorZeros)
                    )
                }
            } else {
                MyFunctions().showSimpleSnackBar(
                    binding.root,
                    getString(R.string.msgErrorInputs)
                )
            }
        }
    }
}