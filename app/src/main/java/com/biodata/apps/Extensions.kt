package com.biodata.apps

import android.app.DatePickerDialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.text.Editable
import android.view.View
import androidx.core.text.buildSpannedString
import androidx.core.text.color
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import java.text.SimpleDateFormat
import java.util.*


fun TextInputLayout.markRequiredInRed() {
    hint = buildSpannedString {
        append(hint)
        color(Color.RED) { append(" *") }
    }
}

fun TextInputLayout.inputError(data: String, message: String?): Boolean {
    return if (data.isEmpty()) {
        this.error = message
        false
    } else {
        this.error = null
        true
    }
}

fun View.snackBar(message: String?) {
    Snackbar.make(this, message!!, Snackbar.LENGTH_SHORT).show()
}

fun String.toEditable(): Editable = Editable.Factory.getInstance().newEditable(this)

fun TextInputEditText.openCalender(
    context: Context?
) {
    val myCalendar = Calendar.getInstance()
    val date = DatePickerDialog.OnDateSetListener { _, year, monthOfYear, dayOfMonth ->
        myCalendar[Calendar.YEAR] = year
        myCalendar[Calendar.MONTH] = monthOfYear
        myCalendar[Calendar.DAY_OF_MONTH] = dayOfMonth
        val sdf = SimpleDateFormat("dd MMM yyyy", Locale.getDefault())
        this.setText(sdf.format(myCalendar.time))
    }
    this.onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
        if (hasFocus) {
            val dialog = DatePickerDialog(
                context!!,
                R.style.DatePickerTheme,
                date,
                myCalendar[Calendar.YEAR], myCalendar[Calendar.MONTH],
                myCalendar[Calendar.DAY_OF_MONTH]
            )
            dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialog.show()
        }
    }
    this.setOnClickListener {
        val dialog = DatePickerDialog(
            context!!,
            R.style.DatePickerTheme,
            date,
            myCalendar[Calendar.YEAR], myCalendar[Calendar.MONTH],
            myCalendar[Calendar.DAY_OF_MONTH]
        )
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.show()
    }
}
