package com.axxess.myapplication.network

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
    val cover: String? = "",
    val coverWidth: Int = 0,
    val id: String? = "",
    val coverHeight: Int = 0,
    val title: String? = "")