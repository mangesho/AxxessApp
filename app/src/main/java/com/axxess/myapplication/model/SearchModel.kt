package com.axxess.myapplication.model

import android.os.Parcel
import android.os.Parcelable
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.axxess.myapplication.R
import com.bumptech.glide.Glide


/**
 * This is an plain Kotlin data classes that represent the things in our app. These are the
 * objects that should be displayed on screen, or manipulated by the app.
 *
 * @see database for objects that are mapped to the database
 * @see network for objects that parse or prepare network calls
 */

/**
 * SearchModel represent a list of Image item which displayed in list
 */
data class SearchModel(
    val id: String? = "",
    val cover: String? = "",
    val title: String? = "") : Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    )


    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeString(cover)
        parcel.writeString(title)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<SearchModel> {
        override fun createFromParcel(parcel: Parcel): SearchModel {
            return SearchModel(parcel)
        }

        override fun newArray(size: Int): Array<SearchModel?> {
            return arrayOfNulls(size)
        }


       // This will load the image using Glide in imageView of recycler items
        @BindingAdapter("searchImage")
        @JvmStatic
        fun loadSearchImage(view: ImageView, coverImage: String) {
            Glide.with(view.context)
                .load(coverImage)
                .centerCrop()
                .placeholder(R.drawable.ic_launcher_background)
                .into(view)

        }

    }
}