package com.angiedev.sheystore.ui.main.view

import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.ui.NavigationUI
import com.angiedev.sheystore.R
import com.angiedev.sheystore.databinding.ActivityMainBinding
import com.angiedev.sheystore.ui.extension.BackButtonBehaviour
import com.angiedev.sheystore.ui.extension.setupWithNavController
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity: AppCompatActivity() {

    companion object {
        private const val BOTTOM_NAV_SELECTED_ITEM_ID_KEY = "bottomNavSelectedItemIdKey"
    }

    private var _binding: ActivityMainBinding? = null
    private var navController: NavController? = null
    private val binding get() = _binding!!
    private var bottomNavSelectedItemId = R.id.nav_login
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
        savedInstanceState?.let {
            bottomNavSelectedItemId = savedInstanceState.getInt(BOTTOM_NAV_SELECTED_ITEM_ID_KEY)
        }
        setupNavigationView()
    }

    private fun setupNavigationView() {
        val controller = binding.bottomNavigation.setupWithNavController(
            navGraphIds = navGraphId,
            fragmentManager = supportFragmentManager,
            containerId = R.id.main_fragment_container_view,
            backButtonBehaviour = BackButtonBehaviour.POP_HOST_FRAGMENT,
            firstItemId = R.id.nav_login,
            intent = intent
        )

        binding.bottomNavigation.selectedItemId = bottomNavSelectedItemId

        navController = controller.value

        controller.observe(this) { navController ->
            NavigationUI.setupWithNavController(binding.bottomNavigation, navController)
            bottomNavSelectedItemId = navController.graph.id
        }
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