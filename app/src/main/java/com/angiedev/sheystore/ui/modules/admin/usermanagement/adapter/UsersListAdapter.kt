package com.angiedev.sheystore.ui.modules.admin.usermanagement.adapter

import android.content.Context
import android.graphics.drawable.GradientDrawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.angiedev.sheystore.R
import com.angiedev.sheystore.databinding.ItemUserCellBinding
import com.angiedev.sheystore.domain.entities.user.UserEntity

class UserListAdapter(
    private val onClickAction: (Int) -> Unit
) : ListAdapter<UserEntity, UserListAdapter.UserViewHolder>(UserDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemUserCellBinding.inflate(layoutInflater, parent, false)
        return UserViewHolder(parent.context, binding)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val user = getItem(position)
        holder.bind(user)
    }

    inner class UserViewHolder(
        private val context: Context,
        private val binding: ItemUserCellBinding
    ) :
        RecyclerView.ViewHolder(binding.root) {

        private var _isSelected = false
        private val isSelected get() = _isSelected

        fun bind(user: UserEntity) = with(binding) {
            val names = user.fullName.split(" ")

            if (names.size > 1) {
                tvName.text = names[0]
                tvLastName.text = names[1]
            } else {
                tvName.text = user.fullName
            }

            tvUsername.text = "@" + user.username
            tvPhone.text = user.phone
            tvRole.text = user.role

            llUserItem.setOnClickListener {

                onClickAction(user.id)
            }
        }

        fun toggleSelection(selected: Boolean) {
            if (selected) setBorder(STROKE_WIDTH)
            else  setBorder(ZERO)

            _isSelected = selected
        }

        private fun setBorder(strokeWidth: Int) {
            val background = GradientDrawable()
            background.shape = GradientDrawable.RECTANGLE
            background.setStroke(strokeWidth, ContextCompat.getColor(context, R.color.teal_700))
            background.cornerRadius = CORNER_RADIUS
            background.setColor(ContextCompat.getColor(context, android.R.color.white))
            binding.llUserItem.background = background
        }
    }

    companion object {

        private const val ZERO = 0
        private const val STROKE_WIDTH = 4
        private const val CORNER_RADIUS = 14f
    }
}

class UserDiffCallback : DiffUtil.ItemCallback<UserEntity>() {
    override fun areItemsTheSame(oldItem: UserEntity, newItem: UserEntity): Boolean {
        return oldItem.username == newItem.username
    }

    override fun areContentsTheSame(oldItem: UserEntity, newItem: UserEntity): Boolean {
        return oldItem == newItem
    }
}

