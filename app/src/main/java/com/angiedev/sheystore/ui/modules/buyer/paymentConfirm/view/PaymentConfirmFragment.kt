package com.angiedev.sheystore.ui.modules.buyer.paymentConfirm.view

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.angiedev.sheystore.data.model.remote.request.order.OrderType
import com.angiedev.sheystore.data.model.remote.response.ApiResponse
import com.angiedev.sheystore.databinding.FragmentPaymentConfirmBinding
import com.angiedev.sheystore.ui.base.BaseFragment
import com.angiedev.sheystore.ui.main.viewmodel.MainViewModel
import com.angiedev.sheystore.ui.modules.buyer.paymentConfirm.viewmodel.PaymentConfirmViewModel
import com.angiedev.sheystore.ui.user.viewmodel.UserDataViewModel
import com.angiedev.sheystore.ui.utils.constant.PreferencesKeys
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PaymentConfirmFragment : BaseFragment<FragmentPaymentConfirmBinding>() {

    override var isBottomNavVisible = View.GONE
    private val mainViewModel: MainViewModel by activityViewModels()
    private val paymentConfirmViewModel: PaymentConfirmViewModel by viewModels()
    private val userDataViewModel: UserDataViewModel by viewModels()
    private val args: PaymentConfirmFragmentArgs by navArgs()

    override fun getViewBinding() = FragmentPaymentConfirmBinding.inflate(layoutInflater)

    override fun createView(view: View, savedInstanceState: Bundle?) {
        super.createView(view, savedInstanceState)
    }

    override fun setListeners() {
        super.setListeners()
        with(binding) {
            paymentConfirmToolbar.setNavigationOnClickListener {
                findNavController().popBackStack()
            }

            paymentConfirmContinueButton.setOnClickListener {
                if (args.cvc == paymentConfirmCvc.text.toString()){
                    paymentConfirmViewModel.createOrder(
                        userId = userDataViewModel.readValue(PreferencesKeys.USER_ID) ?: 0,
                        cartId = mainViewModel.cartId,
                        status = OrderType.IN_PROGRESS
                    )
                } else {
                    Toast.makeText(requireContext(), "CVC incorrecto", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun setObservers() {
        super.setObservers()
        paymentConfirmViewModel.paymentConfirm.observe(viewLifecycleOwner) {
            when(it) {
                is ApiResponse.Error -> Toast.makeText(requireContext(), "Ha ocurrido un error", Toast.LENGTH_SHORT).show()
                ApiResponse.Loading -> TODO()
                is ApiResponse.Success -> {
                    showConfirmPaymentDialog()
                }
            }
        }
    }

    private fun showConfirmPaymentDialog() {
        PaymentConfirmDialog.newInstance().also {
            it.setPaymentConfirmListener(object : PaymentConfirmListener {
                override fun gotToShopping() {

                }

                override fun goToOrder() {
                    findNavController().navigate(PaymentConfirmFragmentDirections.actionPaymentConfirmFragmentToOrdersTabsFragment())
                }

            })
        }.show(childFragmentManager, PaymentConfirmDialog.TAG)
    }
}