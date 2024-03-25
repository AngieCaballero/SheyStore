package com.angiedev.sheystore.ui.main

import android.app.Activity
import android.os.Bundle
import android.os.PersistableBundle
import com.angiedev.sheystore.databinding.ActivityMainBinding

class MainActivity: Activity() {

    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

}