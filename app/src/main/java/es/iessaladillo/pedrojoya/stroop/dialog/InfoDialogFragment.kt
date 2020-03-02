package es.iessaladillo.pedrojoya.stroop.dialog


import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import es.iessaladillo.pedrojoya.stroop.R


class InfoDialogFragment : DialogFragment() {

    private val message: String? by lazy {
        arguments!!.getString(getString(R.string.ARG_MESSAGE))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        isCancelable = false
    }


    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            // Usar el builder para construir el mensaje emergente que aparece
            val builder = AlertDialog.Builder(it)
                .setTitle(R.string.help_title)
                .setMessage(message)
                .setPositiveButton(R.string.help_accept) { _, _ -> }//Bton aceptar no hace nada


            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }
}
