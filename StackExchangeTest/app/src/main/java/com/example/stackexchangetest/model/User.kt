package com.example.stackexchangetest.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue

@Parcelize
data class User(
    val user_id: Long,

    @SerializedName("display_name")
    val displayName: String,

    val reputation: Int,

    @SerializedName("profile_image")
    val profileImage: String?,

    @SerializedName("user_type")
    val userType: String?,

    val link: String?,

    @SerializedName("creation_date")
    val creationDate: Long,

    @SerializedName("badge_counts")
    val badgeCounts: @RawValue BadgeCounts?,
) : Parcelable

@Parcelize
data class BadgeCounts(
    val bronze: Int,
    val silver: Int,
    val gold: Int,
) : Parcelable