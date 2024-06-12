package com.angiedev.sheystore.ui.choiceMyShippingAddress.view

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.angiedev.sheystore.data.model.remote.response.ApiResponse
import com.angiedev.sheystore.databinding.FragmentChoiceMyShippingAddressBinding
import com.angiedev.sheystore.domain.entities.shippingAddres.ShippingAddressEntity
import com.angiedev.sheystore.ui.base.BaseFragment
import com.angiedev.sheystore.ui.choiceMyShippingAddress.adapter.ChoiceMyShippingAddressAdapter
import com.angiedev.sheystore.ui.choiceMyShippingAddress.adapter.ChoiceMyShippingAddressListener
import com.angiedev.sheystore.ui.choiceMyShippingAddress.viewmodel.ChoiceMyShippingAddressViewModel
import com.angiedev.sheystore.ui.user.viewmodel.UserDataViewModel
import com.angiedev.sheystore.ui.utils.constant.PreferencesKeys
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ChoiceMyShippingAddressFragment : BaseFragment<FragmentChoiceMyShippingAddressBinding>(), ChoiceMyShippingAddressListener {

    private val choiceMyShippingAddressViewModel: ChoiceMyShippingAddressViewModel by viewModels()
    private val userDataViewModel: UserDataViewModel by viewModels()

    private var choiceMyShippingAddressAdapter: ChoiceMyShippingAddressAdapter? = null
    private var shippingAddressSelected: ShippingAddressEntity? = null
    override fun getViewBinding() = FragmentChoiceMyShippingAddressBinding.inflate(layoutInflater)

    override fun createView(view: View, savedInstanceState: Bundle?) {
        super.createView(view, savedInstanceState)
        setupAdapter()
        choiceMyShippingAddressViewModel.getShippingAddressList(
            userDataViewModel.readValue(PreferencesKeys.USER_ID) ?: 0
        )
    }

    override fun setListeners() {
        super.setListeners()
        with(binding) {
            fragmentChoiceMyShippingApply.setOnClickListener {
                choiceMyShippingAddressViewModel.updateShippingAddress(
                    userId = userDataViewModel.readValue(PreferencesKeys.USER_ID) ?: 0,
                    shippingAddressEntity = shippingAddressSelected
                )
            }

            fragmentChoiceMyShippingNewAddress.setOnClickListener {
                findNavController().navigate(ChoiceMyShippingAddressFragmentDirections.actionChoiceMyShippingAddressFragmentToAddNewAddressFragment())
            }

            fragmentChoiceMyShippingAddressToolbar.setNavigationOnClickListener {
                findNavController().popBackStack()
            }
        }
    }

    override fun setObservers() {
        super.setObservers()
        choiceMyShippingAddressViewModel.shippingAddressList.observe(viewLifecycleOwner) {
            when(it) {
                is ApiResponse.Error -> Toast.makeText(requireContext(), it.toString(), Toast.LENGTH_SHORT).show()
                ApiResponse.Loading -> TODO()
                is ApiResponse.Success -> {
                    choiceMyShippingAddressAdapter?.submitList(it.data)
                }
            }
        }
        choiceMyShippingAddressViewModel.shippingAddressUpdated.observe(viewLifecycleOwner) {
            when(it) {
                is ApiResponse.Error -> Toast.makeText(requireContext(), "Ha ocurrido un error", Toast.LENGTH_SHORT).show()
                ApiResponse.Loading -> TODO()
                is ApiResponse.Success -> {
                    Toast.makeText(requireContext(), "Direeción actualizada", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun setupAdapter() {
        choiceMyShippingAddressAdapter = ChoiceMyShippingAddressAdapter(this)
        binding.fragmentChoiceMyShippingAddressRv.adapter = choiceMyShippingAddressAdapter
    }

    override fun onCheckedItemListener(shippingAddressEntity: ShippingAddressEntity) {
        shippingAddressSelected = shippingAddressEntity
    }
}