package com.biodata.apps

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class User(
    val nama: String? = "",
    val gender: String? = "",
    val tempat: String? = "",
    val tanggal: String? = "",
    val alamat: String? = ""
) : Parcelable