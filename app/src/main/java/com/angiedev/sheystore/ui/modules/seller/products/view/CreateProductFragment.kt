package com.angiedev.sheystore.ui.modules.seller.products.view

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.viewModels
import com.abhishek.colorpicker.ColorPickerDialog
import com.angiedev.sheystore.databinding.FragmentCreateProductBinding
import com.angiedev.sheystore.ui.base.BaseFragment
import com.angiedev.sheystore.ui.modules.seller.products.adapter.AddColorAdapter
import com.angiedev.sheystore.ui.modules.seller.products.adapter.AddImagesAdapter
import com.angiedev.sheystore.ui.modules.seller.products.viewmodel.CreateProductViewModel
import com.angiedev.sheystore.ui.utils.checkPermission
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CreateProductFragment: BaseFragment<FragmentCreateProductBinding>() {
    private val viewModel: CreateProductViewModel by viewModels()
    private val colorPicker = ColorPickerDialog()
    private var colorAdapter: AddColorAdapter? = null
    private var photoAdapter: AddImagesAdapter? = null

    private val requestReadImagesPermission =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) {
                goToPickerImage()
            }
        }

    private val pickMedia =
        registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
            if (uri != null) {
                saveImageInStorage(uri)
            }
        }

    override fun getViewBinding() = FragmentCreateProductBinding.inflate(layoutInflater)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupAdapters()
    }

    private fun setupAdapters() {
        colorAdapter = AddColorAdapter(listOf("#000000", "#FFFFFF")) { colorPressed ->

        }
        binding.createProductColorRecycler.adapter = colorAdapter
        photoAdapter = AddImagesAdapter(listOf("", "")) { photoDeleted ->

        }
        binding.createProductPhotosRecycler.adapter = photoAdapter
    }

    override fun setListeners() {
        super.setListeners()

        binding.createProductAddPhotoButton.setOnClickListener {
            checkReadImagePermission()
        }

        colorPicker.setOnOkCancelListener { isOk, color ->
            if (isOk) {
                Log.i("ColorPicker", "Color Selected: $color")
            }
        }

        binding.createProductAddColorButton.setOnClickListener {
            colorPicker.show(requireActivity().supportFragmentManager)
        }
    }

    private fun saveImageInStorage(uri: Uri) {
        val flag = Intent.FLAG_GRANT_READ_URI_PERMISSION
        context?.contentResolver?.takePersistableUriPermission(uri, flag)
        viewModel.savePhotoInFirebaseStorage(uri)
    }

    private fun goToPickerImage() {
        pickMedia.launch("image/*")
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