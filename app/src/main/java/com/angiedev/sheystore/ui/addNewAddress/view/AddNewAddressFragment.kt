package com.angiedev.sheystore.ui.addNewAddress.view

import android.Manifest
import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.coroutineScope
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.angiedev.sheystore.R
import com.angiedev.sheystore.data.model.remote.response.ApiResponse
import com.angiedev.sheystore.databinding.FragmentAddNewAddressBinding
import com.angiedev.sheystore.ui.addNewAddress.viewmodel.AddNewAddressViewModel
import com.angiedev.sheystore.ui.base.BaseFragment
import com.angiedev.sheystore.ui.user.viewmodel.UserDataViewModel
import com.angiedev.sheystore.ui.utils.checkPermission
import com.angiedev.sheystore.ui.utils.constant.PreferencesKeys
import com.angiedev.sheystore.ui.utils.helper.hideKeyboard
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MapStyleOptions
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.maps.android.ktx.awaitMap
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


@AndroidEntryPoint
class AddNewAddressFragment : BaseFragment<FragmentAddNewAddressBinding>() {

    override var isBottomNavVisible = View.GONE

    private val userDataViewModel: UserDataViewModel by viewModels()
    private val addNewAddressViewModel: AddNewAddressViewModel by viewModels()
    private var focusedLocationProvider: FusedLocationProviderClient? = null
    private var supportMapFragment: SupportMapFragment? = null
    private var googleMap: GoogleMap? = null
    private var bottomSheetBehavior: BottomSheetBehavior<*>? = null

    private val requestReadImagesPermission =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) {
                setupGoogleMap()
            }
        }
    override fun getViewBinding() = FragmentAddNewAddressBinding.inflate(layoutInflater)


    override fun createView(view: View, savedInstanceState: Bundle?) {
        super.createView(view, savedInstanceState)
        bottomSheetBehavior = BottomSheetBehavior.from(binding.fragmentAddNewBottomSheet.root)
        getGoogleMap()
    }

    private fun checkLocationPermission() {
        if (checkPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION)) {
            setupGoogleMap()
            getCurrentLocation()
        } else {
            requestReadImagesPermission.launch(Manifest.permission.ACCESS_FINE_LOCATION)
        }
    }


    private fun getCurrentLocation() {
        focusedLocationProvider = LocationServices.getFusedLocationProviderClient(requireContext())

        focusedLocationProvider?.lastLocation?.addOnSuccessListener {
            if (it != null) {
                val latitude = it.latitude
                val longitude = it.longitude
                googleMap?.moveCamera(CameraUpdateFactory.newLatLngZoom(LatLng(latitude, longitude), 20f))
            }
        }?.addOnFailureListener {
            Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
        }
    }

    private fun setupGoogleMap() {
        googleMap?.isMyLocationEnabled = true
        try {
            val success = googleMap?.setMapStyle(
                MapStyleOptions.loadRawResourceStyle(requireContext(), R.raw.map_style_json)
            )
            if (success == false) {
                Toast.makeText(requireContext(), "Style parsing failed.", Toast.LENGTH_SHORT).show()
            }
        } catch (e: Exception) {
            Toast.makeText(requireContext(), e.message, Toast.LENGTH_SHORT).show()
        }
    }

    override fun setObservers() {
        super.setObservers()
        addNewAddressViewModel.shippingAddressCrated.observe(viewLifecycleOwner) {
            when(it) {
                is ApiResponse.Error -> Toast.makeText(requireContext(), "Ha ocurrido un error", Toast.LENGTH_SHORT).show()
                ApiResponse.Loading -> TODO()
                is ApiResponse.Success -> {
                    Toast.makeText(requireContext(), "Dirección agregada", Toast.LENGTH_SHORT).show()
                    hideKeyboard()
                    lifecycleScope.launch {
                        delay(500)
                        bottomSheetBehavior?.state = BottomSheetBehavior.STATE_COLLAPSED
                    }
                }
            }
        }
    }

    override fun setListeners() {
        super.setListeners()
        with(binding) {
            fragmentAddNewAddressToolbar.setNavigationOnClickListener {
                findNavController().popBackStack()
            }

            bottomSheetBehavior?.addBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback(){
                override fun onStateChanged(bottomSheet: View, newState: Int) {
                    // Make some changes when the bottom sheet changes its state
                }

                override fun onSlide(bottomSheet: View, slideOffset: Float) {
                    // Make some changes when the bottom sheet is being dragged
                }
            })

            fragmentAddNewBottomSheet.bottomSheetAddNewAddressButton.setOnClickListener {
                with(fragmentAddNewBottomSheet) {
                    val nameText = bottomSheetAddNewNameAddressEditLayout.text
                    val detailsText = bottomSheetAddNewDetailAddressEditLayout.text
                    when {
                        nameText.isNullOrBlank() -> {
                            bottomSheetAddNewNameAddressInputLayout.error = "El nombre es requerido"
                        }
                        detailsText.isNullOrBlank() -> {
                            bottomSheetAddNewDetailsAddressInputLayout.error = "Los detalles son requeridos"
                        }
                        else -> {
                            bottomSheetAddNewDetailsAddressInputLayout.error = null
                            bottomSheetAddNewNameAddressInputLayout.error = null
                            addNewAddressViewModel.createShippingAddress(
                                userId = userDataViewModel.readValue(PreferencesKeys.USER_ID) ?: 0,
                                name = nameText.toString(),
                                details = detailsText.toString(),
                                default = bottomSheetAddNewMarkAsDefault.isChecked
                            )
                        }
                    }
                }
            }
        }
    }

    private fun getGoogleMap() {
        supportMapFragment = childFragmentManager.findFragmentById(R.id.fragment_add_new_address_maps) as SupportMapFragment
        lifecycle.coroutineScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.CREATED) {
                googleMap = supportMapFragment?.awaitMap()
                checkLocationPermission()
            }
        }
    }

}