package com.angiedev.sheystore.ui.utils

import com.google.android.material.textfield.TextInputLayout

class TextFieldValidator {

    private val setRules = mutableListOf<Pair<ValidationRule, TextInputLayout>>()
    fun checkIsValid(): Boolean {
        var isValid = true
        setRules.forEach {
            val textInputEditText = it.second.editText?.text.toString()
            if (!it.first.validate(textInputEditText)) {
                it.second.error = it.first.errorMessage
                isValid = false
            } else {
                it.second.error = null
            }
        }
        return isValid
    }

    fun setRules(rules: List<Pair<ValidationRule, TextInputLayout>>) {
        setRules.clear()
        setRules.addAll(rules)
    }

    data class ValidationRule(val errorMessage: String, val validator: (String) -> Boolean) {
        fun validate(input: String) = validator(input)
    }
}