package com.dicoding.hairstyler.ui.customview

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.util.Patterns
import androidx.appcompat.widget.AppCompatEditText

class EmailEditText @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : AppCompatEditText(context, attrs) {

    init {
        addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
                validateEmail(s)
            }
        })
    }

    private fun validateEmail(email: Editable?) {
        if (email.isNullOrEmpty()) {
            error = null
            return
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            error = "Invalid email format"
        } else {
            error = null
        }
    }
}