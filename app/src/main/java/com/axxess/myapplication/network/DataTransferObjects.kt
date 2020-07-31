package com.axxess.myapplication.network

import androidx.room.PrimaryKey
import com.axxess.myapplication.model.SearchModel
import com.google.gson.annotations.SerializedName


/**
 * DataTransferObjects go in this file. These are responsible for parsing responses from the server
 * or formatting objects to send to the server.
 *
 */

/**
 * VideoHolder holds a list of Images.
 *
 * This is to parse first level of our network result which looks like
 *
 * {
 *   "data": [],
 *   "success" : Boolean,
 *   "status" : Int,
 *  "currentPageNum" : Int
 * }
 */
data class NetworkImageContainer(val data: List<NetworkImages>, val success: Boolean,
                                            val status: Int, val currentPageNum: Int)


data class NetworkImages(
    val id: String? = "",
    val title: String? = "",
    val description : String? = "",
    var images :  List<ImageList> = emptyList()
)

data class ImageList(
    var link : String
)


/**
 * Convert Network results to database objects
 */
fun List<NetworkImages>.asDataModel(): List<SearchModel> {
    return map {
        SearchModel(
            id = it.id,
            cover = if(it.images.isNullOrEmpty()) null else it.images[0].link,
            title = it.title
        )
    }
}
