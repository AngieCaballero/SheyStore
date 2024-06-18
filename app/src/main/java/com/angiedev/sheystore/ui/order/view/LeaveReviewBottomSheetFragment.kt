package com.angiedev.sheystore.ui.order.view

import android.graphics.drawable.GradientDrawable
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.angiedev.sheystore.R
import com.angiedev.sheystore.databinding.FragmentLeaveReviewBottomSheetBinding
import com.angiedev.sheystore.domain.entities.cart.CartItemEntity
import com.angiedev.sheystore.ui.base.BaseBottomSheetDialogFragment
import com.angiedev.sheystore.ui.utils.checkPermission
import com.angiedev.sheystore.ui.utils.extension.parcelable
import com.angiedev.sheystore.ui.utils.extension.parseColor
import com.angiedev.sheystore.ui.utils.extension.setInvisible
import com.angiedev.sheystore.ui.utils.extension.setVisible
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference

class LeaveReviewBottomSheetFragment : BaseBottomSheetDialogFragment<FragmentLeaveReviewBottomSheetBinding>() {

    private var orderReviewListener: OrderReviewListener? = null
    private var cartItem: CartItemEntity? = null
    private var firebaseStorageReference: StorageReference? = null
    private var downloadImageUrl = ""

    override fun getViewBinding() = FragmentLeaveReviewBottomSheetBinding.inflate(layoutInflater)

    override fun createView(view: View, savedInstanceState: Bundle?) {
        super.createView(view, savedInstanceState)
        firebaseStorageReference = FirebaseStorage.getInstance().reference
        setupUI()
    }

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

    private fun saveImageInStorage(uri: Uri) {
        val childReference = firebaseStorageReference?.child("review/${System.currentTimeMillis()}.jpg")

        childReference?.putFile(uri)
            ?.addOnSuccessListener { snapShot ->
                snapShot.storage.downloadUrl.addOnCompleteListener { downloadUri ->
                    downloadImageUrl = downloadUri.result.toString()
                    Log.i("OkHttp", downloadImageUrl)
                    Toast.makeText(requireContext(), "Imagen agregada", Toast.LENGTH_SHORT).show()
                }
            }?.addOnFailureListener { _ ->
                Toast.makeText(requireContext(), "Ha ocurrido un error al cargar la imagen", Toast.LENGTH_SHORT).show()
            }
    }

    private fun goToPickerImage() {
        choiceImageLauncher.launch("image/*")
    }

    private fun setupUI() {
        setupCartItem()
    }

    private fun setupCartItem() {
        with(binding.bottomSheetItemOrder) {
            itemCartRemoveIcon.setInvisible()
            itemCartName.text = cartItem?.product?.name
            itemCartTotalPrice.text = cartItem?.product?.price.toString()
            itemCartQuantityStepper.setInvisible()
            itemCartStatus.setVisible()
            itemCartStatus.text = resources.getString(R.string.completado)
            val gradientColor = GradientDrawable().apply {
                setColor(cartItem?.color.orEmpty().parseColor())
                setStroke(1, root.context.getColor(R.color.white))
                cornerRadii = floatArrayOf(90f, 90f, 90f, 90f, 90f, 90f, 90f, 90f)
            }
            itemCartColor.background = gradientColor
            com.bumptech.glide.Glide.with(root.context)
                .load(cartItem?.product?.image.orEmpty())
                .diskCacheStrategy(com.bumptech.glide.load.engine.DiskCacheStrategy.ALL)
                .error(R.drawable.product_hint)
                .into(itemCartImage)
        }

    }

    fun setOrderReviewListener(listener: OrderReviewListener) {
        orderReviewListener = listener
    }
    override fun setArguments(args: Bundle?) {
        super.setArguments(args)
        cartItem = args?.parcelable(CART_ITEM)
    }

    override fun setListeners() {
        super.setListeners()
        with(binding) {
            bottomSheetLeaveReviewCancel.setOnClickListener {
                dismiss()
            }
            bottomSheetLeaveReviewSubmit.setOnClickListener {
                orderReviewListener?.onSubmit(
                    binding.bottomSheetLeaveReviewRatingBar.rating,
                    binding.bottomSheetAddLeaveReviewEditLayout.text.toString(),
                    downloadImageUrl
                )
                dismiss()
            }
            bottomSheetLeaveReviewInputLayout.setEndIconOnClickListener {
                checkReadImagePermission()
            }
        }
    }

    companion object {
        const val TAG = "LeaveReviewBottomSheetFragment"
        const val CART_ITEM = "cart_item"
        fun newInstance(
            bundle: Bundle,
        ) = LeaveReviewBottomSheetFragment().apply {
            arguments = bundle
        }
    }
}