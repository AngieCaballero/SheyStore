package com.angiedev.sheystore.ui.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.angiedev.sheystore.ui.main.view.MainActivity

abstract class BaseFragment<B : ViewBinding> : Fragment() {

    private var _binding: B? = null
    val binding get() = _binding!!

    protected open var isBottomNavVisible = View.VISIBLE

    protected abstract fun getViewBinding(): B

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        init()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setListeners()
        setObservers()
        createView(view, savedInstanceState)
    }

    protected open fun createView(view: View, savedInstanceState: Bundle?) { }

    protected open fun setListeners() {}

    protected open fun setObservers() {}

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        bottomNavigationVisibility()
        return binding.root
    }

    protected fun bottomNavigationVisibility(bottomNavigationVisibility: Int = isBottomNavVisible) {
        // get the reference of the parent activity and call the setBottomNavigationVisibility method.
        (activity as MainActivity).isBottomNavVisible(bottomNavigationVisibility)
    }

    private fun init() {
        _binding = getViewBinding()
    }
}