package com.axxess.myapplication.model


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
    val coverWidth: Int = 0,
    val coverHeight: Int = 0,
    val title: String? = "")