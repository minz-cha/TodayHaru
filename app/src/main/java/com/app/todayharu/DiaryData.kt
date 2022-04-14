package com.app.todayharu

import android.os.Parcel
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class DiaryData(
    val date: String,
    val content: String) : Parcelable