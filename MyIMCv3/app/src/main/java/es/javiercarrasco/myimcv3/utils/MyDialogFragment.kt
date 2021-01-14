package es.javiercarrasco.myimcv3.utils

import android.app.Dialog
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import es.javiercarrasco.myimcv3.HistoricoFragment
import es.javiercarrasco.myimcv3.R
import es.javiercarrasco.myimcv3.data.MyIMC

class MyDialogFragment(imc: MyIMC) : DialogFragment() {

    private var dato: MyIMC = imc

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it)

            builder.setMessage(context!!.getString(R.string.msgDialog))
                .setPositiveButton(android.R.string.yes) { dialog, which ->
                    // En caso afirmativo se almacena el dato.
                    MyFunctions().writeFile(context!!, dato)

                    MyFunctions().showSimpleSnackBar(
                        it.findViewById(android.R.id.content),
                        context!!.getString(R.string.msgAceptDialog)
                    )
                }
                .setNegativeButton(android.R.string.cancel) { dialog, which ->
                    MyFunctions().showSimpleSnackBar(
                        it.findViewById(android.R.id.content),
                        context!!.getString(R.string.msgCancelDialog)
                    )
                }
            builder.create()
        } ?: throw IllegalStateException(context!!.getString(R.string.msgErrorDialog))
    }
}