package com.angiedev.sheystore.ui.main.view

import android.os.Bundle
import androidx.activity.OnBackPressedCallback
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment
import com.angiedev.sheystore.R
import com.angiedev.sheystore.data.model.remote.response.ApiResponse
import com.angiedev.sheystore.databinding.ActivityMainBinding
import com.angiedev.sheystore.ui.login.viewmodel.LoginViewModel
import com.angiedev.sheystore.ui.main.viewmodel.MainViewModel
import com.angiedev.sheystore.ui.utils.extension.BackButtonBehaviour
import com.angiedev.sheystore.ui.utils.extension.setupWithNavController
import dagger.hilt.android.AndroidEntryPoint
import java.util.Date

@AndroidEntryPoint
class MainActivity: AppCompatActivity() {

    companion object {
        private const val BOTTOM_NAV_SELECTED_ITEM_ID_KEY = "bottomNavSelectedItemIdKey"
    }

    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!
    private lateinit var navController: NavController
    private val mainViewModel: MainViewModel by viewModels()
    private val loginViewModel: LoginViewModel by viewModels()

    private var bottomNavSelectedItemId = R.id.nav_home
    private val navGraphId = listOf(
        R.navigation.nav_login,
        R.navigation.nav_home,
        R.navigation.nav_shopping,
        R.navigation.nav_cart,
        R.navigation.nav_my_profile
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        handleOnBackPressed()
        setObservers()
        savedInstanceState?.let {
            bottomNavSelectedItemId = savedInstanceState.getInt(BOTTOM_NAV_SELECTED_ITEM_ID_KEY)
        }
        setupNavigationView()
        loginViewModel.isAuthored(Date().time)
    }

    private fun setObservers() {
        loginViewModel.isAuthored.observe(this) { isAuthored ->
            if (!isAuthored) navigateToLoginModule()
        }
    }

    private fun handleOnBackPressed() {
        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (!navController.popBackStack()) {
                    finish()
                }
            }
        })
    }

    private fun setupNavigationView() {
        binding.bottomNavigation.itemIconTintList = null
        val controller = binding.bottomNavigation.setupWithNavController(
            navGraphIds = navGraphId,
            fragmentManager = supportFragmentManager,
            containerId = R.id.main_fragment_container_view,
            backButtonBehaviour = BackButtonBehaviour.POP_HOST_FRAGMENT,
            firstItemId = R.id.nav_home,
            intent = intent
        )

        binding.bottomNavigation.selectedItemId = bottomNavSelectedItemId

        controller.observe(this) { selectedItemId ->
            bottomNavSelectedItemId = selectedItemId
            navController = Navigation.findNavController(this@MainActivity, R.id.main_fragment_container_view)
        }
    }

    private fun navigateToLoginModule() {
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.main_fragment_container_view) as NavHostFragment
        val inflater = navHostFragment.navController.navInflater
        val graph = inflater.inflate(R.navigation.nav_login)
        navController.graph = graph
    }

    fun isBottomNavVisible(visibility: Int){
        binding.bottomNavigation.visibility = visibility
    }

    // Needed to maintain correct state over rotations
    override fun onSaveInstanceState(outState: Bundle) {
        outState.putInt(BOTTOM_NAV_SELECTED_ITEM_ID_KEY, bottomNavSelectedItemId)
        super.onSaveInstanceState(outState)
    }

}