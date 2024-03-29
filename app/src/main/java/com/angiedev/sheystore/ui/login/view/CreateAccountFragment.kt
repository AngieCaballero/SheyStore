package com.angiedev.sheystore.ui.login.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.angiedev.sheystore.R
import com.angiedev.sheystore.databinding.FragmentCreateAccountBinding
import com.angiedev.sheystore.ui.base.BaseFragment

class CreateAccountFragment : BaseFragment<FragmentCreateAccountBinding>() {

    override var isBottomNavVisible = View.GONE

    override fun getViewBinding() = FragmentCreateAccountBinding.inflate(layoutInflater)

}