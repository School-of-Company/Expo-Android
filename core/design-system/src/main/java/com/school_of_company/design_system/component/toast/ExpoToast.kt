package com.school_of_company.design_system.component.toast

import android.content.Context
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.DrawableRes
import com.school_of_company.design_system.component.toast.toast_icon.ToastIcon
import com.school_of_company.design_system.R

object ExpoToast {
    /**
     * Expo 프로젝트에서 사용하는 커스텀 Toast입니다.
     * @param context 토스트 표시에 사용될 [Context]
     * @param message 토스트에 표시될 메시지
     * @param duration 토스트 표시 시간
     * @param drawable 토스트에 텍스트와 함께 표시될 drawable의 resource id
     * @return 위 파라미터들로 만들어진 [Toast] 객체
     *
     * 사용법
     * ```kotlin
     * ExpoToast.create(
     *      context = context,
     *      message = context.getString("안드로이드!!") -> R.string.___,
     *      drawable = ToastIcon.Success
     * ).show()
     * ```
     */
    fun create(
        context: Context,
        toastMessage: String,
        duration: Int = Toast.LENGTH_SHORT,
        @DrawableRes drawable: Int = ToastIcon.Success
    ) : Toast {
        // 커스텀뷰 생성
        val layoutInflater = LayoutInflater.from(context)
        val view = layoutInflater.inflate(
            R.layout.expo_toast, // 커스텀 토스트 레이아웃 XML
            LinearLayout(context).findViewById(R.id.ll_expo_toast) // 루트 레이아웃
        )

        // 메세지 설정
        val messageTextView = view.findViewById<TextView>(R.id.tv_toast_message)
        messageTextView.text = toastMessage

        // 아이콘 이미지 설정
        val iconImageView = view.findViewById<ImageView>(R.id.img_toast_icon)
        iconImageView.setImageResource(drawable)

        // 토스트 객체 생성 및 설정
        return Toast(context).apply {
            this.duration = duration
            this.view = view
        }
    }
}