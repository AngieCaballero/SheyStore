package com.angiedev.sheystore.ui.paymentMethods.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.angiedev.sheystore.R
import com.angiedev.sheystore.databinding.ItemPaymentBinding
import com.angiedev.sheystore.domain.entities.paymentMethod.PaymentMethodEntity
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy

class PaymentMethodsAdapter : RecyclerView.Adapter<PaymentMethodsAdapter.PaymentMethodsViewHolder>() {

    private val paymentMethodsList = mutableListOf<PaymentMethodEntity>()

    inner class PaymentMethodsViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val binding = ItemPaymentBinding.bind(view)

        fun render(paymentMethodEntity: PaymentMethodEntity) {
            with(binding) {
                itemPaymentCardNumber.text = paymentMethodEntity.cardNumber

                Glide.with(root.context)
                    .load("")
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .error(R.drawable.ic_mastercard)
                    .into(itemPaymentCardIcon)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = PaymentMethodsViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.item_payment, parent, false)
    )

    override fun getItemCount() = paymentMethodsList.size

    override fun onBindViewHolder(holder: PaymentMethodsViewHolder, position: Int) {
        holder.render(paymentMethodsList[position])
    }

    @SuppressLint("NotifyDataSetChanged")
    fun submitList(data: List<PaymentMethodEntity>) {
        paymentMethodsList.clear()
        paymentMethodsList.addAll(data)
        notifyDataSetChanged()
    }
}