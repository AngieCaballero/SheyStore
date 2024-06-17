package com.angiedev.sheystore.ui.order.view

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.angiedev.sheystore.data.model.remote.response.ApiResponse
import com.angiedev.sheystore.databinding.FragmentOrdersBinding
import com.angiedev.sheystore.domain.entities.cart.CartItemEntity
import com.angiedev.sheystore.ui.base.BaseFragment
import com.angiedev.sheystore.ui.order.adapter.OrderItemListener
import com.angiedev.sheystore.ui.order.adapter.OrdersAdapter
import com.angiedev.sheystore.ui.order.viewmodel.OrderViewModel
import com.angiedev.sheystore.ui.user.viewmodel.UserDataViewModel
import com.angiedev.sheystore.ui.utils.constant.PreferencesKeys
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OrdersFragment : BaseFragment<FragmentOrdersBinding>(), OrderItemListener {

    override var isBottomNavVisible = View.GONE
    private val viewModel: OrderViewModel by viewModels()
    private val userDataViewModel: UserDataViewModel by viewModels()
    private var statusId = 1
    private var ordersAdapter: OrdersAdapter? = null

    override fun getViewBinding() = FragmentOrdersBinding.inflate(layoutInflater)

    override fun setArguments(args: Bundle?) {
        super.setArguments(args)
        statusId = args?.getInt(STATUS_ID) ?: 1
    }

    override fun createView(view: View, savedInstanceState: Bundle?) {
        super.createView(view, savedInstanceState)
        setupAdapter()
        viewModel.getOrders(
            userDataViewModel.readValue(PreferencesKeys.USER_ID) ?: 0,
            statusId
        )
    }

    private fun setupAdapter() {
        ordersAdapter = OrdersAdapter(this)
        binding.fragmentOrdersRecyclerView.adapter = ordersAdapter
    }

    override fun setObservers() {
        super.setObservers()
        viewModel.order.observe(viewLifecycleOwner) {
            when(it) {
                is ApiResponse.Error -> Toast.makeText(requireContext(), "Ha ocurrido un error", Toast.LENGTH_SHORT).show()
                ApiResponse.Loading -> TODO()
                is ApiResponse.Success -> {
                    ordersAdapter?.submitList(it.data.reversed())
                }
            }
        }
    }

    companion object {
        const val STATUS_ID = "status_id"

        fun newInstance(statusId: Int) = OrdersFragment().apply {
            arguments = Bundle().apply {
                putInt(STATUS_ID, statusId)
            }
        }
    }

    override fun onItemLeaveReview(orderItem: CartItemEntity) {
        LeaveReviewBottomSheetFragment.newInstance().show(childFragmentManager, LeaveReviewBottomSheetFragment.TAG)
    }
}