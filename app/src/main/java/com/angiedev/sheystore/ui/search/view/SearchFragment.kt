package com.angiedev.sheystore.ui.search.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.MenuItemCompat
import com.angiedev.sheystore.R
import com.angiedev.sheystore.databinding.FragmentSearchBinding
import com.angiedev.sheystore.ui.base.BaseFragment
import com.google.android.material.search.SearchView

class SearchFragment : BaseFragment<FragmentSearchBinding>() {

    override var isBottomNavVisible = View.GONE
    override fun getViewBinding() = FragmentSearchBinding.inflate(layoutInflater)

    override fun createView(view: View, savedInstanceState: Bundle?) {
        super.createView(view, savedInstanceState)
        binding.fragmentSearchView.show()
    }

    override fun setListeners() {
        super.setListeners()
        binding.fragmentSearchView.addTransitionListener { _, _, transitionState2 ->
            if (transitionState2 == SearchView.TransitionState.SHOWING) {
                Toast.makeText(requireContext(), "Showing", Toast.LENGTH_SHORT).show()
            }
        }

        binding.fragmentSearchView.setOnMenuItemClickListener {
            return@setOnMenuItemClickListener true
        }


        binding.fragmentSearchView.inflateMenu(R.menu.menu_search_bar)
    }
}