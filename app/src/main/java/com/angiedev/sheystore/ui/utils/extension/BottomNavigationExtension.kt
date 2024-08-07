package com.angiedev.sheystore.ui.utils.extension

import android.content.Intent
import android.util.SparseArray
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.R
import com.google.android.material.bottomnavigation.BottomNavigationView

/**
 * Manages the various graphs needed for a [BottomNavigationView].
 *
 * This sample is a workaround until the Navigation Component supports multiple back stacks.
 *
 * Your navGraphIds must have the same ids as your menuItem ids
 */
fun BottomNavigationView.setupWithNavController(
    fragmentManager: FragmentManager,
    navGraphIds: List<Int>,
    backButtonBehaviour: BackButtonBehaviour,
    containerId: Int,
    firstItemId: Int,
    intent: Intent,
    firstSelectedItemId: Int
): LiveData<Int> {

    val menuListItem = listOf("Login", "Inicio", "Compras", "Carrito", "Perfil", "Home Seller", "Productos", "Home Admin", "Gestión de Usuarios")
    val menuFragmentTag = mutableListOf<String>()
    // Map of tags
    // Result. Mutable live data with the selected controlled
    val selectedNavController = MutableLiveData<NavController>()
    val selectedItemIdObserver = MutableLiveData<Int>()

    // First create a NavHostFragment for each NavGraph ID
    navGraphIds.forEachIndexed { index, navGraphId ->
        val fragmentTag = getFragmentTag(menuListItem[index])

        // Find or create the Navigation host fragment
        val navHostFragment = obtainNavHostFragment(
            fragmentManager,
            fragmentTag,
            navGraphId,
            containerId
        )

        // Obtain its id
        val graphId = navHostFragment.navController.graph.id

        menuFragmentTag.add(fragmentTag)

        // Save to the map

        // Attach or detach nav host fragment depending on whether it's the selected item.
        val menuItemSelected = menu.findItem(firstSelectedItemId).title
        if (menuItemSelected == menuListItem[index]) {
            // Update livedata with the selected graph
            selectedItemIdObserver.postValue(firstSelectedItemId)
            selectedNavController.value = navHostFragment.navController
            attachNavHostFragment(fragmentManager, navHostFragment, graphId == firstItemId)
        } else {
            detachNavHostFragment(fragmentManager, navHostFragment)
        }
    }

    selectedItemId = firstSelectedItemId
    val menuItemSelected = menu.findItem(firstSelectedItemId).title.toString()
    val firstItemSelected = menu.getItem(1).title.toString()

    // Now connect selecting an item with swapping Fragments
    var selectedItemTag = menuFragmentTag.first { it.contains(menuItemSelected) }
    val firstFragmentTag = menuFragmentTag.first { it.contains(firstItemSelected) }
    var isOnFirstFragment = selectedItemTag == firstFragmentTag

    // When a navigation item is selected
    setOnItemSelectedListener { item ->
        // Don't do anything if the state is state has already been saved.
        if (fragmentManager.isStateSaved) {
            false
        } else {
            val newlySelectedItemTag = menuFragmentTag.first { it.contains(item.title.toString()) }
            if (selectedItemTag != newlySelectedItemTag) {
                // Pop everything above the first fragment (the "fixed start destination")
                fragmentManager.popBackStack(
                    firstFragmentTag,
                    FragmentManager.POP_BACK_STACK_INCLUSIVE
                )
                val selectedFragment = fragmentManager.findFragmentByTag(newlySelectedItemTag)
                        as NavHostFragment

                if (backButtonBehaviour == BackButtonBehaviour.SHOW_STARTING_FRAGMENT) { // Back button goes to first
                    // Exclude the first fragment tag because it's always in the back stack.
                    if (firstFragmentTag != newlySelectedItemTag) {
                        // Commit a transaction that cleans the back stack and adds the first fragment
                        // to it, creating the fixed started destination.
                        commitTransactionWithFixedStart(
                            fragmentManager,
                            selectedFragment,
                            firstFragmentTag
                        )
                    }
                } else { // Back button goes to previous 'main' graph destination
                    commitTransaction(fragmentManager, selectedFragment, selectedItemTag)
                }
                selectedItemTag = newlySelectedItemTag
                isOnFirstFragment = selectedItemTag == firstFragmentTag
                selectedItemIdObserver.postValue(item.itemId)
                selectedNavController.value = selectedFragment.navController
                true
            } else {
                false
            }
        }
    }

    // Optional: on item reselected, pop back stack to the destination of the graph
    //setupItemReselected(fragmentManager)

    // Optional: handle deep links
    setupDeepLinks(navGraphIds, fragmentManager, containerId, intent)

    // Finally, ensure that we update our BottomNavigationView when the back stack changes
    fragmentManager.addOnBackStackChangedListener {
        if (!isOnFirstFragment && !fragmentManager.isOnBackStack(firstFragmentTag)) {
            this.selectedItemId = firstSelectedItemId
        }

        // Reset the graph if the currentDestination is not valid (happens when the back
        // stack is popped after using the back button).
        selectedNavController.value?.let { controller ->
            if (controller.currentDestination == null) {
                controller.navigate(controller.graph.id)
                selectedItemIdObserver.postValue(firstSelectedItemId)
            }
        }
    }
    return selectedItemIdObserver
}

private fun commitTransaction(
    fragmentManager: FragmentManager,
    selectedFragment: NavHostFragment,
    selectedItemTag: String
) {
    fragmentManager.beginTransaction()
        .setCustomAnimations(
            R.anim.nav_default_enter_anim,
            R.anim.nav_default_exit_anim,
            R.anim.nav_default_pop_enter_anim,
            R.anim.nav_default_pop_exit_anim
        )
        .attach(selectedFragment)
        .setPrimaryNavigationFragment(selectedFragment)
        .detach(fragmentManager.findFragmentByTag(selectedItemTag)!!)
        .setReorderingAllowed(true)
        .commit()
}

private fun commitTransactionWithFixedStart(
    fragmentManager: FragmentManager,
    selectedFragment: NavHostFragment,
    firstFragmentTag: String
) {
    fragmentManager.beginTransaction()
        .setCustomAnimations(
            R.anim.nav_default_enter_anim,
            R.anim.nav_default_exit_anim,
            R.anim.nav_default_pop_enter_anim,
            R.anim.nav_default_pop_exit_anim
        )
        .attach(selectedFragment)
        .setPrimaryNavigationFragment(selectedFragment)
        .detach(fragmentManager.findFragmentByTag(firstFragmentTag)!!)
        .addToBackStack(firstFragmentTag)
        .setReorderingAllowed(true)
        .commit()
}

private fun BottomNavigationView.setupDeepLinks(
    navGraphIds: List<Int>,
    fragmentManager: FragmentManager,
    containerId: Int,
    intent: Intent
) {
    val menuListItem = listOf("Login", "Inicio", "Compras", "Carrito", "Perfil", "Home Seller", "Productos", "Home Admin", "Gestión de Usuarios")
    navGraphIds.forEachIndexed { index, navGraphId ->
        val fragmentTag = getFragmentTag(menuListItem[index])

        // Find or create the Navigation host fragment
        val navHostFragment = obtainNavHostFragment(
            fragmentManager,
            fragmentTag,
            navGraphId,
            containerId
        )
        // Handle Intent
        if (navHostFragment.navController.handleDeepLink(intent)
            && selectedItemId != navHostFragment.navController.graph.id
        ) {
            this.selectedItemId = navHostFragment.navController.graph.id
        }
    }
}

private fun BottomNavigationView.setupItemReselected(
    fragmentManager: FragmentManager
) {
    setOnItemReselectedListener { item ->
        val newlySelectedItemTag = menu.findItem(item.itemId).title.toString()
        val selectedFragment = fragmentManager.findFragmentByTag(newlySelectedItemTag)
                as NavHostFragment
        val navController = selectedFragment.navController
        // Pop the back stack to the start destination of the current navController graph
        navController.popBackStack(
            navController.graph.startDestinationId, false
        )
    }
}

private fun detachNavHostFragment(
    fragmentManager: FragmentManager,
    navHostFragment: NavHostFragment
) {
    fragmentManager.beginTransaction()
        .detach(navHostFragment)
        .commitNow()
}

private fun attachNavHostFragment(
    fragmentManager: FragmentManager,
    navHostFragment: NavHostFragment,
    isPrimaryNavFragment: Boolean
) {
    fragmentManager.beginTransaction()
        .attach(navHostFragment)
        .apply {
            if (isPrimaryNavFragment) {
                setPrimaryNavigationFragment(navHostFragment)
            }
        }
        .commitNow()
}

private fun obtainNavHostFragment(
    fragmentManager: FragmentManager,
    fragmentTag: String,
    navGraphId: Int,
    containerId: Int
): NavHostFragment {
    // If the Nav Host fragment exists, return it
    val existingFragment = fragmentManager.findFragmentByTag(fragmentTag) as NavHostFragment?
    existingFragment?.let { return it }

    // Otherwise, create it and return it.
    val navHostFragment = NavHostFragment.create(navGraphId)
    fragmentManager.beginTransaction()
        .add(containerId, navHostFragment, fragmentTag)
        .commitNow()
    return navHostFragment
}

private fun FragmentManager.isOnBackStack(backStackName: String): Boolean {
    val backStackCount = backStackEntryCount
    for (index in 0 until backStackCount) {
        if (getBackStackEntryAt(index).name == backStackName) {
            return true
        }
    }
    return false
}

private fun getFragmentTag(name: String) = "bottomNavigation#$name"

enum class BackButtonBehaviour {
    SHOW_STARTING_FRAGMENT,
    POP_HOST_FRAGMENT
}