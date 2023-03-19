package ru.linew.spotifyApp.ui

import android.widget.Toast
import androidx.fragment.app.Fragment

fun Fragment.showErrorToast(msg: String) {
    Toast.makeText(requireContext(), msg, Toast.LENGTH_SHORT).show()
}
fun Fragment.showMessageToast(msg: String) {
    Toast.makeText(requireContext(), msg, Toast.LENGTH_SHORT).show()}