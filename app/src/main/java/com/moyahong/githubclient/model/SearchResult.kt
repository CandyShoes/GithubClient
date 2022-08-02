package com.moyahong.githubclient.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import android.os.Parcel
import java.util.ArrayList

class SearchResult<M> : Parcelable {
    @SerializedName("total_count")
    var totalCount: String? = null

    @SerializedName("incomplete_results")
    var isIncompleteResults = false
    var items: ArrayList<M>? = null
    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeString(totalCount)
        dest.writeByte(if (isIncompleteResults) 1.toByte() else 0.toByte())
        dest.writeList(items)
    }

    constructor() {}
    protected constructor(`in`: Parcel) {
        totalCount = `in`.readString()
        isIncompleteResults = `in`.readByte().toInt() != 0
        items = ArrayList()
        `in`.readList(items, SearchResult::class.java.classLoader)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<SearchResult<*>> =
            object : Parcelable.Creator<SearchResult<*>> {
                override fun createFromParcel(source: Parcel): SearchResult<*> {
                    return SearchResult<Any?>(source)
                }

                override fun newArray(size: Int): Array<SearchResult<*>?> {
                    return arrayOfNulls(size)
                }
            }
    }
}