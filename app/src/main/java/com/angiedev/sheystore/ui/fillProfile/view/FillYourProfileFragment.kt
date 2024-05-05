package com.angiedev.sheystore.ui.fillProfile.view

import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.angiedev.sheystore.R
import com.angiedev.sheystore.databinding.FragmentFillYourProfileBinding
import com.angiedev.sheystore.ui.base.BaseFragment
import com.angiedev.sheystore.ui.utils.checkPermission

class FillYourProfileFragment : BaseFragment<FragmentFillYourProfileBinding>() {

    override var isBottomNavVisible = View.GONE

    private val roleList = listOf(
        Pair("Vendedor", "1"),
        Pair("Comprador", "2")
    )
    private val genderList = listOf("Hombre", "Mujer")
    private var genderSelected = ""
    private var roleSelected = ""


    private val requestReadImagesPermission = registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
        if (isGranted) {
            goToPickerImage()
        }
    }

    private val choiceImageLauncher = registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        if (uri != null) {
            binding.fillProfileImage.setImageURI(uri)
        }
    }

    private fun goToPickerImage() {
        choiceImageLauncher.launch("image/*")
    }

    override fun getViewBinding() = FragmentFillYourProfileBinding.inflate(layoutInflater)

    override fun createView(view: View, savedInstanceState: Bundle?) {
        super.createView(view, savedInstanceState)
        setupAutoCompleteFields()
    }

    private fun setupAutoCompleteFields() {
        val roleAdapter = ArrayAdapter(requireContext(), R.layout.item_auto_complete, roleList.map { it.first })
        val genderAdapter = ArrayAdapter(requireContext(), R.layout.item_auto_complete, genderList)
        binding.fillYourProfileAutoCompleteRole.setAdapter(roleAdapter)
        binding.fillYourProfileAutoCompleteGender.setAdapter(genderAdapter)
    }

    override fun setListeners() {
        super.setListeners()
        with(binding) {
            fillYourProfileAutoCompleteGender.onItemClickListener = AdapterView.OnItemClickListener { parent, _, position, _ ->
                genderSelected = parent.getItemAtPosition(position).toString()
            }

            fillYourProfileAutoCompleteRole.onItemClickListener = AdapterView.OnItemClickListener { parent, _, position, _ ->
                roleSelected = parent.getItemAtPosition(position).toString()
            }

            fillProfileImage.setOnClickListener {
                checkReadImagePermission()
            }
        }
    }

    private fun checkReadImagePermission() {
        val permissionToRequest = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            android.Manifest.permission.READ_MEDIA_IMAGES
        } else {
            android.Manifest.permission.READ_EXTERNAL_STORAGE
        }
        if (checkPermission(requireContext(), permissionToRequest)) {
            goToPickerImage()
        } else {
            requestReadImagesPermission.launch(permissionToRequest)
        }
    }
}