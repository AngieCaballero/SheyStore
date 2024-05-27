package com.angiedev.sheystore.ui.addNewAddress.view

import android.Manifest
import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.coroutineScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.angiedev.sheystore.R
import com.angiedev.sheystore.databinding.FragmentAddNewAddressBinding
import com.angiedev.sheystore.ui.base.BaseFragment
import com.angiedev.sheystore.ui.utils.checkPermission
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MapStyleOptions
import com.google.maps.android.ktx.awaitMap
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class AddNewAddressFragment : BaseFragment<FragmentAddNewAddressBinding>() {

    override var isBottomNavVisible = View.GONE

    private var focusedLocationProvider: FusedLocationProviderClient? = null
    private var supportMapFragment: SupportMapFragment? = null
    private var googleMap: GoogleMap? = null

    private val requestReadImagesPermission =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) {
                setupGoogleMap()
            }
        }
    override fun getViewBinding() = FragmentAddNewAddressBinding.inflate(layoutInflater)


    override fun createView(view: View, savedInstanceState: Bundle?) {
        super.createView(view, savedInstanceState)
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

    override fun setListeners() {
        super.setListeners()
        with(binding) {
            fragmentAddNewAddressToolbar.setNavigationOnClickListener {
                findNavController().popBackStack()
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