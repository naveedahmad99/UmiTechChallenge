package com.urban.mobility.io.domain

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class RepositoryOwner(
        @Expose @SerializedName("id") val id: Long,
        @Expose @SerializedName("login") val username: String,
        @Expose @SerializedName("avatar_url") val avatar: String
) : Parcelable {
    constructor(parcel: Parcel) : this(
            parcel.readLong(),
            parcel.readString()!!,
            parcel.readString()!!
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeLong(id)
        parcel.writeString(username)
        parcel.writeString(avatar)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<RepositoryOwner> {
        override fun createFromParcel(parcel: Parcel): RepositoryOwner {
            return RepositoryOwner(parcel)
        }

        override fun newArray(size: Int): Array<RepositoryOwner?> {
            return arrayOfNulls(size)
        }
    }
}