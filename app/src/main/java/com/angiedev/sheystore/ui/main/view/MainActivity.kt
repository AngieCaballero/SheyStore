package com.angiedev.sheystore.ui.main.view

import android.os.Bundle
import androidx.activity.OnBackPressedCallback
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.angiedev.sheystore.R
import com.angiedev.sheystore.databinding.ActivityMainBinding
import com.angiedev.sheystore.domain.entities.user.RoleType
import com.angiedev.sheystore.ui.modules.login.login.viewmodel.LoginViewModel
import com.angiedev.sheystore.ui.main.viewmodel.MainViewModel
import com.angiedev.sheystore.ui.user.viewmodel.UserDataViewModel
import com.angiedev.sheystore.ui.utils.constant.PreferencesKeys
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
    private lateinit var splashScreen: SplashScreen
    private lateinit var navController: NavController
    private val mainViewModel: MainViewModel by viewModels()
    private val loginViewModel: LoginViewModel by viewModels()
    private val userDataViewModel: UserDataViewModel by viewModels()

    private var bottomNavSelectedItemId = R.id.nav_home
    private val navGraphId = listOf(
        R.navigation.nav_login,
        R.navigation.nav_home,
        R.navigation.nav_shopping,
        R.navigation.nav_cart,
        R.navigation.nav_my_profile,
        R.navigation.nav_seller_home,
        R.navigation.nav_seller_product
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        splashScreen = installSplashScreen()
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        splashScreen.setKeepOnScreenCondition { true }
        handleOnBackPressed()
        setObservers()
        savedInstanceState?.let {
            bottomNavSelectedItemId = savedInstanceState.getInt(BOTTOM_NAV_SELECTED_ITEM_ID_KEY)
        }
        loginViewModel.isAuthored(Date().time)
    }

    private fun setObservers() {
        loginViewModel.isAuthored.observe(this) { isAuthored ->
            val module = if (!isAuthored) {
                Pair(R.id.item_login, R.id.nav_login)
            }
            else {
                val role = userDataViewModel.readValue(PreferencesKeys.ROLE)
                when (role) {
                    RoleType.Buyer.value -> {
                        setupBuyerBottomNav()
                        Pair(R.id.item_home, R.id.nav_home)
                    }
                    RoleType.Seller.value -> {
                        setupSellerBottomNav()
                        Pair(R.id.item_seller_home, R.id.nav_seller_home)
                    }
                    else -> {
                        setupAdminBottomNav()
                        Pair(R.id.item_admin_home, R.id.nav_admin_home)
                    }
                }
            }
            setupNavigationView(module.first, module.second)
            splashScreen.setKeepOnScreenCondition { false }
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

    fun selectBottomNav(itemId: Int) {
        binding.bottomNavigation.selectedItemId = itemId
    }

    fun setupSellerBottomNav() {
        binding.bottomNavigation.menu.apply {
            findItem(R.id.item_home).isVisible = false
            findItem(R.id.item_cart).isVisible = false
            findItem(R.id.item_shopping).isVisible = false
            findItem(R.id.item_admin_home).isVisible = false
            findItem(R.id.item_users_management).isVisible = false
            findItem(R.id.item_backup).isVisible = false
            findItem(R.id.item_my_profile).isVisible = true
            findItem(R.id.item_seller_home).isVisible = true
            findItem(R.id.item_seller_product).isVisible = true
        }
        binding.bottomNavigation.selectedItemId = R.id.item_seller_home
    }

    fun setupBuyerBottomNav() {
        binding.bottomNavigation.menu.apply {
            findItem(R.id.item_home).isVisible = true
            findItem(R.id.item_cart).isVisible = true
            findItem(R.id.item_shopping).isVisible = true
            findItem(R.id.item_my_profile).isVisible = true
            findItem(R.id.item_seller_home).isVisible = false
            findItem(R.id.item_seller_product).isVisible = false
            findItem(R.id.item_admin_home).isVisible = false
            findItem(R.id.item_users_management).isVisible = false
            findItem(R.id.item_backup).isVisible = false
        }
        binding.bottomNavigation.selectedItemId = R.id.item_home
    }

    fun setupAdminBottomNav() {
        binding.bottomNavigation.menu.apply {
            findItem(R.id.item_home).isVisible = false
            findItem(R.id.item_cart).isVisible = false
            findItem(R.id.item_shopping).isVisible = false
            findItem(R.id.item_seller_home).isVisible = false
            findItem(R.id.item_seller_product).isVisible = false
            findItem(R.id.item_admin_home).isVisible = true
            findItem(R.id.item_users_management).isVisible = true
            findItem(R.id.item_backup).isVisible = true
            findItem(R.id.item_my_profile).isVisible = true
        }
        binding.bottomNavigation.selectedItemId = R.id.item_admin_home
    }

    private fun setupNavigationView(firsItemName: Int, firstItemId: Int) {
        val controller = binding.bottomNavigation.setupWithNavController(
            navGraphIds = navGraphId,
            fragmentManager = supportFragmentManager,
            containerId = R.id.main_fragment_container_view,
            backButtonBehaviour = BackButtonBehaviour.POP_HOST_FRAGMENT,
            firstSelectedItemId = firsItemName,
            firstItemId = firstItemId,
            intent = intent
        )

        binding.bottomNavigation.selectedItemId = bottomNavSelectedItemId

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.main_fragment_container_view) as NavHostFragment
        navController = navHostFragment.navController
        controller.observe(this) { selectedItemId ->
            bottomNavSelectedItemId = selectedItemId
        }
    }

    fun isBottomNavVisible(visibility: Int){
        binding.bottomNavigation.visibility = visibility
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        mainViewModel.onRestoreInstanceState(savedInstanceState)
    }

    // Needed to maintain correct state over rotations
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(BOTTOM_NAV_SELECTED_ITEM_ID_KEY, bottomNavSelectedItemId)
        mainViewModel.onSaveInstanceState(outState)
    }

}