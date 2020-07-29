package com.luca020400.compose

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Contact(
    var firstName: String = "",
    var lastName: String = "",
    var number: String = "",
    var email: String = "",
) : Parcelable