package com.example.assignment.models

import android.os.Parcel
import android.os.Parcelable
import com.example.assignment.api.COUNT
import com.example.assignment.api.NEXT
import com.example.assignment.api.PAGES
import com.example.assignment.api.PREV
import com.google.gson.annotations.SerializedName
/**
 * This class is used for parsing character REST API Response
 */
data class Info(
    @SerializedName(COUNT)
    val count: Int,
    @SerializedName(PAGES)
    val pageNumber: Int,
    @SerializedName(NEXT)
    var next: String?,
    @SerializedName(PREV)
    var prev: String?
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readInt(),
        parcel.readString(),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(count)
        parcel.writeInt(pageNumber)
        parcel.writeString(next)
        parcel.writeString(prev)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Info> {
        override fun createFromParcel(parcel: Parcel): Info {
            return Info(parcel)
        }

        override fun newArray(size: Int): Array<Info?> {
            return arrayOfNulls(size)
        }
    }
}
