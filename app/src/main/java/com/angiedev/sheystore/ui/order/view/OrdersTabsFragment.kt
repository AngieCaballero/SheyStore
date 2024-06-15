package com.angiedev.sheystore.ui.order.view

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.angiedev.sheystore.databinding.FragmentOrdersTabsBinding
import com.angiedev.sheystore.ui.base.BaseFragment
import com.angiedev.sheystore.ui.order.adapter.ViewPagerTabsAdapter
import com.google.android.material.tabs.TabLayoutMediator

class OrdersTabsFragment : BaseFragment<FragmentOrdersTabsBinding>() {

    override var isBottomNavVisible = View.GONE

    private val tabsScreen = listOf(
        Pair("Activos", Fragment()),
        Pair("Completados", Fragment())
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