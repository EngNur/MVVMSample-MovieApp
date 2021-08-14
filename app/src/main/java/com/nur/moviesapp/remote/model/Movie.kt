package com.nur.moviesapp.remote.model

import android.os.Parcel
import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Movie(
        @ColumnInfo(name = "movie_title")
    val Title: String?,
        @ColumnInfo(name = "movie_year")
    val Year: String?,
        @ColumnInfo(name = "movie_id")
    val imdbID:String?,
        @ColumnInfo(name = "movie_type")
    val Type:String?,
        @ColumnInfo(name = "movie_poster")
    val Poster:String?
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    ) {}


    @PrimaryKey(autoGenerate = true)
    var uuid: Int = 0

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(Title)
        parcel.writeString(Year)
        parcel.writeString(imdbID)
        parcel.writeString(Type)
        parcel.writeString(Poster)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Movie> {
        override fun createFromParcel(parcel: Parcel): Movie {
            return Movie(parcel)
        }

        override fun newArray(size: Int): Array<Movie?> {
            return arrayOfNulls(size)
        }
    }
}
