package com.example.assignment.models

import android.os.Parcel
import android.os.Parcelable
import com.example.assignment.api.*
import com.google.gson.annotations.SerializedName
import java.util.*
/**
 * This class is used for parsing location REST API Response
 */
data class Location(
    @SerializedName(ID)
    val id: Int,
    @SerializedName(NAME)
    var name: String?,
    @SerializedName(TYPE)
    var type: String?,
    @SerializedName(DIMENSION)
    var dimension: String?,
    @SerializedName(URL)
    var url: String?,
    @SerializedName(CREATED)
    var created: String?,
    @SerializedName(RESIDENTS)
    val residents: ArrayList<String>?
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.createStringArrayList()

    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(name)
        parcel.writeString(type)
        parcel.writeString(dimension)
        parcel.writeString(url)
        parcel.writeString(created)
        parcel.writeStringList(residents)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Location> {
        override fun createFromParcel(parcel: Parcel): Location {
            return Location(parcel)
        }

        override fun newArray(size: Int): Array<Location?> {
            return arrayOfNulls(size)
        }
    }
}
