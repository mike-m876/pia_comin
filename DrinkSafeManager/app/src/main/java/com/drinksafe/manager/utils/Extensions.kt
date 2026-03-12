package com.drinksafe.manager.utils

import android.content.Context
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar

/**
 * Extensiones de utilidad para simplificar código repetitivo en la app.
 */

/** Muestra o esconde una View con animación de fade */
fun View.mostrar() {
    visibility = View.VISIBLE
}

fun View.ocultar() {
    visibility = View.GONE
}

fun View.invisible() {
    visibility = View.INVISIBLE
}

/** Animación de entrada desde abajo */
fun View.animarDesdeAbajo(context: Context) {
    val anim = AnimationUtils.loadAnimation(context, android.R.anim.slide_in_left)
    startAnimation(anim)
}

/** Muestra un Snackbar de éxito (verde) */
fun View.snackbarExito(mensaje: String) {
    Snackbar.make(this, mensaje, Snackbar.LENGTH_LONG)
        .setBackgroundTint(context.getColor(com.drinksafe.manager.R.color.verde_exito))
        .setTextColor(context.getColor(android.R.color.white))
        .show()
}

/** Muestra un Snackbar de error (rojo) */
fun View.snackbarError(mensaje: String) {
    Snackbar.make(this, mensaje, Snackbar.LENGTH_LONG)
        .setBackgroundTint(context.getColor(com.drinksafe.manager.R.color.error_color))
        .setTextColor(context.getColor(android.R.color.white))
        .show()
}

/** Toast corto */
fun Context.toastCorto(mensaje: String) {
    Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show()
}
