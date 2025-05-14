package com.darekbx.notebookcheckreader.model

data class RssItem(
    val title: String,
    val link: String,
    val description: String,
    val category: String,
    val pubDate: String,
    val enclosure: String
) {
    var isRead: Boolean = false
}
