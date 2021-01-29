package es.javiercarrasco.myimcv4.utils

import android.app.Dialog
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import es.javiercarrasco.myimcv4.MainActivity
import es.javiercarrasco.myimcv4.R
import es.javiercarrasco.myimcv4.data.MyIMC

class MyDialogFragment(imc: MyIMC) : DialogFragment() {

    private var dato: MyIMC = imc

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it)

            builder.setMessage(context!!.getString(R.string.msgDialog))
                .setPositiveButton(android.R.string.yes) { _, _ ->
                    // En caso afirmativo se almacena el dato.
                    MainActivity.imcDBHelper.addIMC(dato)

                    MyFunctions().showSimpleSnackBar(
                        it.findViewById(android.R.id.content),
                        context!!.getString(R.string.msgAceptDialog)
                    )
                }
                .setNegativeButton(android.R.string.cancel) { _, _ ->
                    MyFunctions().showSimpleSnackBar(
                        it.findViewById(android.R.id.content),
                        context!!.getString(R.string.msgCancelDialog)
                    )
                }
            builder.create()
        } ?: throw IllegalStateException(context!!.getString(R.string.msgErrorDialog))
    }
}