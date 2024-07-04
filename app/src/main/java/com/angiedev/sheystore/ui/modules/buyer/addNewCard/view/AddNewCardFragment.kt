package com.angiedev.sheystore.ui.modules.buyer.addNewCard.view

import android.os.Bundle
import android.text.InputType
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.angiedev.sheystore.data.model.remote.response.ApiResponse
import com.angiedev.sheystore.databinding.FragmentAddNewCardBinding
import com.angiedev.sheystore.ui.modules.buyer.addNewCard.viewmodel.AddNewCardViewModel
import com.angiedev.sheystore.ui.base.BaseFragment
import com.angiedev.sheystore.ui.user.viewmodel.UserDataViewModel
import com.angiedev.sheystore.ui.utils.TextFieldValidator
import com.angiedev.sheystore.ui.utils.constant.PreferencesKeys
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddNewCardFragment : BaseFragment<FragmentAddNewCardBinding>() {

    override var isBottomNavVisible = View.GONE
    private val userDataViewModel: UserDataViewModel by viewModels()
    private val addNewCardViewModel: AddNewCardViewModel by viewModels()
    private lateinit var validator: TextFieldValidator

    override fun getViewBinding() = FragmentAddNewCardBinding.inflate(layoutInflater)

    override fun createView(view: View, savedInstanceState: Bundle?) {
        super.createView(view, savedInstanceState)
        validator = TextFieldValidator()
        setTextFieldRules()
    }

    override fun setListeners() {
        super.setListeners()
        with(binding) {
            addNewCardButtonAdd.setOnClickListener {
                if (validator.checkIsValid()) {
                    addNewCardViewModel.createPaymentMethod(
                        userDataViewModel.readValue(PreferencesKeys.USER_ID) ?: 0,
                        addNewCardNameEditLayout.text.toString(),
                        addNewCardNumberEditLayout.text.toString(),
                        addNewCardExpiredDateEditLayout.text.toString(),
                        addNewCardCvvEditLayout.text.toString().toInt()
                    )
                }
            }

            addNewCardToolbar.setNavigationOnClickListener {
                findNavController().popBackStack()
            }
        }
    }

    override fun setObservers() {
        super.setObservers()
        addNewCardViewModel.paymentMethodCreated.observe(viewLifecycleOwner) {
            when (it) {
                is ApiResponse.Error -> Toast.makeText(requireContext(), "Ha ocurrido un error", Toast.LENGTH_SHORT).show()
                ApiResponse.Loading -> { /* TODO */ }
                is ApiResponse.Success -> {
                    Toast.makeText(requireContext(), "Tarjeta agregada", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun setTextFieldRules() {
        with(binding) {
            val rules = listOf(
                Pair(TextFieldValidator.ValidationRule("Ingrese un nombre válido") {
                    it.length > 3
                }, addNewCardNameInputLayout),
                Pair(TextFieldValidator.ValidationRule("Ingrese un número de tarjeta válido") {
                    it.length < 20
                }, addNewCardNumberInputLayout),
                Pair(TextFieldValidator.ValidationRule("No válido") {
                    it.length == 5
                }, addNewCardExpiredDateInputLayout),
                Pair(TextFieldValidator.ValidationRule("CVC no válido") {
                    (it.length == 3)
                }, addNewCardCvvInputLayout)
            )
            validator.setRules(rules)
        }
    }

}