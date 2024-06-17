package com.angiedev.sheystore.ui.order.view

import com.angiedev.sheystore.databinding.FragmentLeaveReviewBottomSheetBinding
import com.angiedev.sheystore.ui.base.BaseBottomSheetDialogFragment

class LeaveReviewBottomSheetFragment : BaseBottomSheetDialogFragment<FragmentLeaveReviewBottomSheetBinding>() {

    override fun getViewBinding() = FragmentLeaveReviewBottomSheetBinding.inflate(layoutInflater)

    companion object {
        const val TAG = "LeaveReviewBottomSheetFragment"
        fun newInstance() = LeaveReviewBottomSheetFragment()
    }
}