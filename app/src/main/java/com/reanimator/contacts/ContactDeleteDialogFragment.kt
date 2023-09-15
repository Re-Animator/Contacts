package com.reanimator.contacts

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import kotlin.ClassCastException

private const val ALERT_TITLE = "Delete contact"
private const val ALERT_MESSAGE = "Do you want to delete this contact?"
private const val ALERT_POSITIVE = "Delete"
private const val ALERT_CANCEL = "Cancel"
private const val CLASS_CAST_EXCEPTION_MESSAGE = " must implement ConfirmationListener"

class ContactDeleteDialogFragment : DialogFragment() {
    interface ConfirmationListener {
        fun confirmButtonClicked()
        fun cancelButtonClicked()
    }

    private lateinit var listener: ConfirmationListener

    override fun onAttach(context: Context) {
        super.onAttach(context)
        try {
            listener = parentFragment as ConfirmationListener
        } catch (e: ClassCastException) {
            throw ClassCastException("$context $CLASS_CAST_EXCEPTION_MESSAGE")
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return AlertDialog.Builder(requireContext())
            .setTitle(ALERT_TITLE)
            .setMessage(ALERT_MESSAGE)
            .setCancelable(true)
            .setPositiveButton(ALERT_POSITIVE) { _, _ ->
                listener.confirmButtonClicked()
            }
            .setNegativeButton(ALERT_CANCEL) { _, _ ->
                listener.cancelButtonClicked()
            }
            .create()
    }
}
