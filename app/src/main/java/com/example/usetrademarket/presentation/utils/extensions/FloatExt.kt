package com.example.usetrademarket.presentation.utils.extensions

import android.content.res.Resources

// Float 형태 타입의 dp 값을 픽셀로 변환
fun Float.fromDpToPx(): Int {
    return (this * Resources.getSystem().displayMetrics.density).toInt()
}