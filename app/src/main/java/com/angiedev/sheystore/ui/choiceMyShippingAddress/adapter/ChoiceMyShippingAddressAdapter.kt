package com.angiedev.sheystore.ui.choiceMyShippingAddress.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.angiedev.sheystore.R
import com.angiedev.sheystore.data.entities.ShippingAddressEntity
import com.angiedev.sheystore.databinding.ItemShippingAddressBinding
import com.angiedev.sheystore.ui.utils.extension.dpToPx
import com.angiedev.sheystore.ui.utils.extension.setInvisible
import com.angiedev.sheystore.ui.utils.extension.setVisible

class ChoiceMyShippingAddressAdapter(
    private val choiceMyShippingAddressListener: ChoiceMyShippingAddressListener
) : RecyclerView.Adapter<ChoiceMyShippingAddressAdapter.ChoiceMyShippingAddressViewHolder> () {

    private val shippingAddressList = mutableListOf<ShippingAddressEntity>()
    inner class ChoiceMyShippingAddressViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = ItemShippingAddressBinding.bind(view)

        fun render(shippingAddress: ShippingAddressEntity) {
            with(binding) {
                itemShippingAddressName.text = shippingAddress.name
                itemShippingAddressDetails.text = shippingAddress.details

                itemShippingAddressEndIcon.setInvisible()
                itemShippingAddressRadioButton.setVisible()

                itemShippingAddressRadioButton.isChecked = shippingAddress.default

                root.setOnClickListener {
                    if (!shippingAddress.default) {
                        isAnyChecked(adapterPosition)
                        shippingAddressList[adapterPosition].default = !shippingAddressList[adapterPosition].default
                        notifyItemChanged(adapterPosition)
                        choiceMyShippingAddressListener.onCheckedItemListener(shippingAddress)
                    }
                }
            }
            updateMargins()
        }

        private fun isAnyChecked(position: Int) {
            val temp = shippingAddressList.indexOfFirst { it.default }
            if (temp >= 0 && temp != position) {
                shippingAddressList[temp].default = false
                notifyItemChanged(temp, 12)
            }
        }

        fun unCheckedAddress() {
            binding.itemShippingAddressRadioButton.isChecked = false
        }

        private fun updateMargins() {
            (binding.root.layoutParams as ViewGroup.MarginLayoutParams).apply {
                topMargin = 10.dpToPx(binding.root.context).toInt()
            }
        }
    }

    fun getDataSource() = shippingAddressList

    @SuppressLint("NotifyDataSetChanged")
    fun submitList(newList: List<ShippingAddressEntity>) {
        shippingAddressList.clear()
        shippingAddressList.addAll(newList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ) = ChoiceMyShippingAddressViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.item_shipping_address, parent, false)
    )

    override fun getItemCount() = shippingAddressList.size

    override fun onBindViewHolder(holder: ChoiceMyShippingAddressViewHolder, position: Int) {
        holder.render(shippingAddressList[position])
    }
}