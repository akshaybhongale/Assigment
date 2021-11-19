package com.example.assignment.models

import android.os.Parcel
import android.os.Parcelable
import com.example.assignment.api.NAME
import com.example.assignment.api.URL
import com.google.gson.annotations.SerializedName
/**
 * This class is used for parsing character REST API Response
 */
data class Origin(
    @SerializedName(NAME)
    var name: String?,
    @SerializedName(URL)
    var url: String?
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(name)
        parcel.writeString(url)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Origin> {
        override fun createFromParcel(parcel: Parcel): Origin {
            return Origin(parcel)
        }

        override fun newArray(size: Int): Array<Origin?> {
            return arrayOfNulls(size)
        }
    }
}