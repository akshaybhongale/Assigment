package com.example.assignment.models

import android.os.Parcel
import android.os.Parcelable
import com.example.assignment.api.INFO
import com.example.assignment.api.RESULTS
import com.google.gson.annotations.SerializedName

/**
 * This class is used for parsing character REST API Response
 */
data class CharacterList(
    @SerializedName(INFO)
    var info: Info?,
    @SerializedName(RESULTS)
    var results: ArrayList<Results>?
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readParcelable(Info::class.java.classLoader),
        parcel.createTypedArrayList(Results.CREATOR)
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeParcelable(info, flags)
        parcel.writeTypedList(results)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<CharacterList> {
        override fun createFromParcel(parcel: Parcel): CharacterList {
            return CharacterList(parcel)
        }

        override fun newArray(size: Int): Array<CharacterList?> {
            return arrayOfNulls(size)
        }
    }
}
