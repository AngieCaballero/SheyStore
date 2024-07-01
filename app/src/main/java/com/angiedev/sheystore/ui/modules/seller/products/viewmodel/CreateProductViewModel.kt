package com.angiedev.sheystore.ui.modules.seller.products.viewmodel

import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.angiedev.sheystore.data.model.remote.response.ApiResponse
import com.angiedev.sheystore.data.repository.product.IProductRepository
import com.angiedev.sheystore.domain.entities.category.CategoryEntity
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CreateProductViewModel @Inject constructor(
    private val productRepository: IProductRepository
): ViewModel() {

    private val _productSaved: MutableLiveData<ApiResponse<Boolean>> = MutableLiveData()
    val productSaved get() = _productSaved

    private val _photosSaved: MutableLiveData<Boolean> = MutableLiveData()
    val photosSaved get() = _photosSaved

    private val firebaseStorageReference: StorageReference = FirebaseStorage.getInstance().reference

    private fun saveProduct() {

    }

    fun savePhotoInFirebaseStorage(uri: Uri) {
        val childReference = firebaseStorageReference.child(
            "user-photos/${System.currentTimeMillis()}.jpg"
        )

        childReference.putFile(uri)
            .addOnSuccessListener { snapShot ->
                snapShot.storage.downloadUrl.addOnCompleteListener { downloadUri ->
                    val downloadImageUrl = downloadUri.result.toString()
                    _photosSaved.postValue(true)
                }
            }.addOnFailureListener { exception ->
                _photosSaved.postValue(false)
                Log.i("FirebaseStorage", "Ha ocurrido un error al cargar la imagen: ${exception.message}")
            }
    }
}