package com.example.hangeunmarket.ui.dialog

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.hangeunmarket.R

class LoadingDialogFragment : DialogFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT)) // 배경을 투명하게 설정
        // 커스텀 다이얼로그 인플레이트
        return inflater.inflate(R.layout.loading_dialog, container, false)
    }

    // 화면을 꽉 채우도록
    override fun onStart() {
        super.onStart()
        dialog?.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        dialog.setCancelable(false) // 뒤로 가기 버튼 등으로 취소 불가능하도록
        dialog.setCanceledOnTouchOutside(false) // 다이얼로그 외부 터치로 취소 불가능하게 설정
        return dialog
    }
}
