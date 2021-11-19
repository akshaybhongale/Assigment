package com.example.assignment.models

import android.os.Parcel
import android.os.Parcelable
import com.example.assignment.api.*
import com.google.gson.annotations.SerializedName
import java.util.*

/**
 * This class is used for parsing character REST API Response
 */
data class Results(
    @SerializedName(ID)
    val id: Int,
    @SerializedName(NAME)
    var name: String?,
    @SerializedName(STATUS)
    var status: String?,
    @SerializedName(SPECIES)
    var species: String?,
    @SerializedName(TYPE)
    var type: String?,
    @SerializedName(GENDER)
    var gender: String?,
    @SerializedName(ORIGIN)
    val origin: Origin?,
    @SerializedName(LOCATION)
    val location: Location?,
    @SerializedName(IMAGE)
    var image: String?,
    @SerializedName(EPISODE)
    val episode: ArrayList<String>?,
    @SerializedName(URL)
    var url: String?,
    @SerializedName(CREATED)
    var created: String?
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readParcelable(Origin::class.java.classLoader),
        parcel.readParcelable(Location::class.java.classLoader),
        parcel.readString(),
        parcel.createStringArrayList(),
        parcel.readString(),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(name)
        parcel.writeString(status)
        parcel.writeString(species)
        parcel.writeString(type)
        parcel.writeString(gender)
        parcel.writeParcelable(origin, flags)
        parcel.writeParcelable(location, flags)
        parcel.writeString(image)
        parcel.writeStringList(episode)
        parcel.writeString(url)
        parcel.writeString(created)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Results> {
        override fun createFromParcel(parcel: Parcel): Results {
            return Results(parcel)
        }

        override fun newArray(size: Int): Array<Results?> {
            return arrayOfNulls(size)
        }
    }
}
