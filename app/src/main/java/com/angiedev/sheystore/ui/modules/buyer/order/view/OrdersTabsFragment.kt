package com.angiedev.sheystore.ui.modules.buyer.order.view

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import com.angiedev.sheystore.databinding.FragmentOrdersTabsBinding
import com.angiedev.sheystore.ui.base.BaseFragment
import com.angiedev.sheystore.ui.modules.buyer.order.adapter.ViewPagerTabsAdapter
import com.google.android.material.tabs.TabLayoutMediator

class OrdersTabsFragment : BaseFragment<FragmentOrdersTabsBinding>() {

    override var isBottomNavVisible = View.GONE

    private val tabsScreen = listOf(
        Pair("Activos", OrdersFragment.newInstance(1)),
        Pair("Completados", OrdersFragment.newInstance(2))
    )

    override fun getViewBinding() = FragmentOrdersTabsBinding.inflate(layoutInflater)

    override fun createView(view: View, savedInstanceState: Bundle?) {
        super.createView(view, savedInstanceState)
        setupViewPager()
        setupTabs()
    }

    private fun setupTabs() {
        TabLayoutMediator(binding.ordersTabs, binding.ordersTabsViewPager) { tab, position ->
            tab.text = tabsScreen[position].first
        }.attach()
    }

    private fun setupViewPager() {
        binding.ordersTabsViewPager.adapter = ViewPagerTabsAdapter(
            screens = tabsScreen.map { it.second },
            fragmentManager = childFragmentManager,
            lifecycle = lifecycle
        )
        binding.ordersTabsViewPager.isSaveEnabled = false
    }

    override fun setListeners() {
        super.setListeners()
        with(binding) {
            ordersTabsToolbar.setNavigationOnClickListener {
                findNavController().popBackStack()
            }
        }
    }
}