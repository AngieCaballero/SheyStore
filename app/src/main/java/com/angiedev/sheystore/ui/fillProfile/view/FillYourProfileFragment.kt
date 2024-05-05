package com.angiedev.sheystore.ui.fillProfile.view

import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.text.InputType
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.viewModels
import com.angiedev.sheystore.R
import com.angiedev.sheystore.databinding.FragmentFillYourProfileBinding
import com.angiedev.sheystore.ui.base.BaseFragment
import com.angiedev.sheystore.ui.fillProfile.viewmodel.FillYourProfileViewModel
import com.angiedev.sheystore.ui.utils.TextFieldValidator
import com.angiedev.sheystore.ui.utils.checkPermission
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FillYourProfileFragment : BaseFragment<FragmentFillYourProfileBinding>() {

    private val fillYourProfileViewModel: FillYourProfileViewModel by viewModels()
    private var firebaseStorageReference: StorageReference? = null

    override var isBottomNavVisible = View.GONE

    private lateinit var validator: TextFieldValidator

    private val roleList = listOf(
        Pair("Vendedor", "1"),
        Pair("Comprador", "2")
    )
    private val genderList = listOf("Hombre", "Mujer")
    private var genderSelected = ""
    private var roleSelected = ""
    private var downloadImageUrl = ""


    private val requestReadImagesPermission =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) {
                goToPickerImage()
            }
        }

    private val choiceImageLauncher =
        registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
            if (uri != null) {
                saveImageInStorage(uri)
            }
        }

    private fun saveImageInStorage(uri: Uri) {
        binding.fillProfileImage.setImageURI(uri)
        val childReference =
            firebaseStorageReference?.child("user-photos/${System.currentTimeMillis()}.jpg")

        childReference?.putFile(uri)
            ?.addOnSuccessListener { snapShot ->
                snapShot.storage.downloadUrl.addOnCompleteListener { downloadUri ->
                    downloadImageUrl = downloadUri.result.toString()
                    Log.i("OkHttp", downloadImageUrl)
                    Toast.makeText(requireContext(), "Foto de perfil guardada", Toast.LENGTH_SHORT).show()
                }
            }?.addOnFailureListener { exception ->
                Toast.makeText(requireContext(), "Ha ocurrido un error: ${exception.message}", Toast.LENGTH_SHORT).show()
            }
    }

    private fun goToPickerImage() {
        choiceImageLauncher.launch("image/*")
    }

    override fun getViewBinding() = FragmentFillYourProfileBinding.inflate(layoutInflater)

    override fun createView(view: View, savedInstanceState: Bundle?) {
        super.createView(view, savedInstanceState)
        validator = TextFieldValidator()
        setTextFieldRules()
        firebaseStorageReference = FirebaseStorage.getInstance().reference
        setupAutoCompleteFields()
    }

    private fun setTextFieldRules() {
        with(binding) {
            val rules = listOf(
                Pair(TextFieldValidator.ValidationRule("Ingrese su nombre completo") {
                    it.length > 3
                }, fillYourProfileTextInputLayoutFullName),
                Pair(TextFieldValidator.ValidationRule("Ingrese un nombre de usuario válido") {
                    it.length > 3
                }, fillYourProfileTextInputLayoutUsername),
                Pair(TextFieldValidator.ValidationRule("Ingrese un número de teléfono válido") {
                    it.length >= 8
                }, fillYourProfileTextInputLayoutPhone),
                Pair(TextFieldValidator.ValidationRule("favor, seleccione su sexo") {
                    it.isNotEmpty()
                }, fillYourProfileTextInputLayoutGender),
                Pair(TextFieldValidator.ValidationRule("Ingrese un tipo de usuario") {
                    it.isNotEmpty()
                }, fillYourProfileTextInputLayoutRole)
            )
            validator.setRules(rules)
            fillYourProfileTextInputEditEmail.setText(fillYourProfileViewModel.getEmail())
            fillYourProfileTextInputEditEmail.inputType = InputType.TYPE_NULL
        }
    }

    private fun setupAutoCompleteFields() {
        val roleAdapter =
            ArrayAdapter(requireContext(), R.layout.item_auto_complete, roleList.map { it.first })
        val genderAdapter = ArrayAdapter(requireContext(), R.layout.item_auto_complete, genderList)
        binding.fillYourProfileAutoCompleteRole.setAdapter(roleAdapter)
        binding.fillYourProfileAutoCompleteGender.setAdapter(genderAdapter)
    }

    override fun setListeners() {
        super.setListeners()
        with(binding) {
            fillYourProfileAutoCompleteGender.onItemClickListener =
                AdapterView.OnItemClickListener { parent, _, position, _ ->
                    genderSelected = parent.getItemAtPosition(position).toString()
                    fillYourProfileAutoCompleteGender.error = null
                }

            fillYourProfileAutoCompleteRole.onItemClickListener =
                AdapterView.OnItemClickListener { parent, _, position, _ ->
                    roleSelected = parent.getItemAtPosition(position).toString()
                    fillYourProfileAutoCompleteRole.error = null
                }

            fillProfileImage.setOnClickListener {
                checkReadImagePermission()
            }

            fillYourProfileContinueButton.setOnClickListener {
               if (validator.checkIsValid()) {
                   // Make request to save user data

               }
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