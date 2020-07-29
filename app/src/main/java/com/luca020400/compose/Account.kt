package com.luca020400.compose

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Account(
    val name: String,
    val email: String
) : Parcelable