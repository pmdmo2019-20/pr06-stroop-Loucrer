package es.iessaladillo.pedrojoya.stroop.extensions

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager

fun View.hideSoftKeyboard(): Boolean {
    val imm = context.getSystemService(
        Context.INPUT_METHOD_SERVICE
    ) as InputMethodManager
    return imm.hideSoftInputFromWindow(windowToken, 0)
}

// Segun la condicion mostramos o no la vista.
fun View.invisibleUnless(condition: Boolean) {
    visibility = if (condition) View.VISIBLE else View.INVISIBLE
}