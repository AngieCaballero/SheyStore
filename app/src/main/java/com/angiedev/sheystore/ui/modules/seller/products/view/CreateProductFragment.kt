package com.angiedev.sheystore.ui.modules.seller.products.view

import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.abhishek.colorpicker.ColorPickerDialog
import com.angiedev.sheystore.data.model.remote.response.ApiResponse
import com.angiedev.sheystore.data.model.remote.response.dto.category.CategoryDTO
import com.angiedev.sheystore.databinding.FragmentCreateProductBinding
import com.angiedev.sheystore.domain.entities.category.CategoryEntity
import com.angiedev.sheystore.domain.entities.product.ProductEntity
import com.angiedev.sheystore.ui.base.BaseFragment
import com.angiedev.sheystore.ui.modules.seller.products.adapter.AddColorAdapter
import com.angiedev.sheystore.ui.modules.seller.products.adapter.AddImagesAdapter
import com.angiedev.sheystore.ui.modules.seller.products.viewmodel.CreateProductViewModel
import com.angiedev.sheystore.ui.user.viewmodel.UserDataViewModel
import com.angiedev.sheystore.ui.utils.Category
import com.angiedev.sheystore.ui.utils.checkPermission
import com.angiedev.sheystore.ui.utils.constant.PreferencesKeys
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CreateProductFragment: BaseFragment<FragmentCreateProductBinding>() {
    private val viewModel: CreateProductViewModel by viewModels()
    private val userViewModel: UserDataViewModel by viewModels()
    private val colorPicker = ColorPickerDialog()
    private val colorAdapter: AddColorAdapter = AddColorAdapter { colorPressed -> /* TODO */ }
    private val photoAdapter: AddImagesAdapter = AddImagesAdapter { photoDeleted -> /* TODO */ }
    private var userId = 0

    private val requestReadImagesPermission =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) {
                goToPickerImage()
            }
        }

    private val pickMedia =
        registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
            if (uri != null) {
                //val flag = Intent.FLAG_GRANT_READ_URI_PERMISSION
                //context?.contentResolver?.takePersistableUriPermission(uri, flag)
                photoAdapter.addPhoto(uri.toString())
                saveImageInStorage(uri)
            }
        }

    override fun getViewBinding() = FragmentCreateProductBinding.inflate(layoutInflater)

    override fun createView (view: View, savedInstanceState: Bundle?) {
        super.createView(view, savedInstanceState)
        userId = userViewModel.readValue(PreferencesKeys.USER_ID) ?: 0
        setupAdapters()
        binding.createProductCategoryEditText.setAdapter(
            ArrayAdapter(
                requireContext(),
                android.R.layout.simple_spinner_dropdown_item,
                Category.entries.map { it.name }
            )
        )
    }

    private fun setupAdapters() {
        binding.createProductColorRecycler.adapter = colorAdapter
        binding.createProductPhotosRecycler.adapter = photoAdapter
    }

    override fun setListeners() {
        super.setListeners()

        binding.createProductAddPhotoButton.setOnClickListener {
            checkReadImagePermission()
        }

        colorPicker.setOnOkCancelListener { isOk, color ->
            if (isOk) {
                val hex = String.format("#%06X", 0xFFFFFF and color)
                colorAdapter.addColor(hex)
            }
        }

        binding.createProductAddColorButton.setOnClickListener {
            if (!colorPicker.isAdded) {
                colorPicker.show(requireActivity().supportFragmentManager)
            }
        }

        binding.createProductButton.setOnClickListener {
            viewModel.saveProduct(userId, getProduct())
        }

        binding.createProductToolbar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }
    }

    override fun setObservers() {
        super.setObservers()
        viewModel.productSaved.observe(viewLifecycleOwner) {
            when (it) {
                is ApiResponse.Error -> {

                }

                ApiResponse.Loading -> {
                    // TODO
                }

                is ApiResponse.Success -> {
                    findNavController().popBackStack()
                }
            }
        }
    }

    private fun saveImageInStorage(uri: Uri) {
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

    private fun getProduct(): ProductEntity {
        return ProductEntity(
            id = 0,
            name = binding.createProductTitleEditText.text.toString(),
            image = viewModel.photoSaved.value ?: "",
            price = binding.createProductPriceEditText.text.toString().toDoubleOrNull() ?: 0.0,
            discount = binding.createProductDiscountEditText.text.toString().toDoubleOrNull() ?: 0.0,
            description = binding.createProductDescriptionEditText.text.toString(),
            category = CategoryEntity(id = Category.getIdByName(binding.createProductCategoryEditText.text.toString()) ?: 1, name = "", image = ""),
            presentationImages = listOf(),
            colors = colorAdapter.colors,
            rate = "0",
            quantity = binding.createProductQuantityEditText.text.toString().toIntOrNull() ?: 1
        )
    }

}