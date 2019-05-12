package com.project.mobile_university.data.presentation

import android.os.Parcel
import android.os.Parcelable

data class Student(
    val email: String?,
    val firstName: String?,
    val lastName: String?,
    val groupId: Long,
    val subgroupId: Long
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readLong(),
        parcel.readLong()
    )

    override fun toString() = "$firstName $lastName"

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(email)
        parcel.writeString(firstName)
        parcel.writeString(lastName)
        parcel.writeLong(groupId)
        parcel.writeLong(subgroupId)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Student> {
        override fun createFromParcel(parcel: Parcel): Student {
            return Student(parcel)
        }

        override fun newArray(size: Int): Array<Student?> {
            return arrayOfNulls(size)
        }
    }
}