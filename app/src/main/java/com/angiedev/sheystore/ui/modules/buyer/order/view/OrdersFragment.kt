package com.angiedev.sheystore.ui.modules.buyer.order.view

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import com.angiedev.sheystore.data.model.remote.response.ApiResponse
import com.angiedev.sheystore.databinding.FragmentOrdersBinding
import com.angiedev.sheystore.domain.entities.cart.CartItemEntity
import com.angiedev.sheystore.ui.base.BaseFragment
import com.angiedev.sheystore.ui.modules.buyer.order.adapter.OrderItemListener
import com.angiedev.sheystore.ui.modules.buyer.order.adapter.OrdersAdapter
import com.angiedev.sheystore.ui.modules.buyer.order.viewmodel.OrderViewModel
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
                ApiResponse.Loading -> { /* TODO */ }
                is ApiResponse.Success -> {
                    ordersAdapter?.submitList(it.data.reversed())
                }
            }
        }

        viewModel.review.observe(viewLifecycleOwner) {
            when(it) {
                is ApiResponse.Error -> Toast.makeText(requireContext(), "Ha ocurrido un error", Toast.LENGTH_SHORT).show()
                ApiResponse.Loading -> { /* TODO */ }
                is ApiResponse.Success -> Toast.makeText(requireContext(), "Gracias por tu opinión", Toast.LENGTH_SHORT).show()
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
        LeaveReviewBottomSheetFragment.newInstance(
            bundleOf(LeaveReviewBottomSheetFragment.CART_ITEM to orderItem)
        ).also {
            it.setOrderReviewListener(object : OrderReviewListener {
                override fun onSubmit(rating: Float, comment: String, image: String) {
                    viewModel.sendReview(
                        userId = userDataViewModel.readValue(PreferencesKeys.USER_ID) ?: 0,
                        productId = orderItem.product.id,
                        rating = rating,
                        comment = comment,
                        image = image
                    )
                }

            })
        }.show(childFragmentManager, LeaveReviewBottomSheetFragment.TAG)
    }
}