package com.darekbx.notebookcheckreader.repository

import com.darekbx.notebookcheckreader.model.RssItem
import com.darekbx.notebookcheckreader.repository.local.RssItemDto

fun RssItem.toDto() = RssItemDto(
    title = this.title,
    link = this.link,
    description = this.description,
    category = this.category,
    pubDate = this.pubDate,
    enclosure = this.enclosure,
    isRead = false
)

fun RssItemDto.toModel() = RssItem(
    title = this.title,
    link = this.link,
    description = this.description,
    category = this.category,
    pubDate = this.pubDate,
    enclosure = this.enclosure
).apply {
    isRead = this.isRead
}