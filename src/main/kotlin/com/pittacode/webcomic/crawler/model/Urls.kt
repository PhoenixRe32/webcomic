package com.pittacode.webcomic.crawler.model

import java.net.URI
import java.net.URL

sealed class Url(private val value: String) {
    init {
        require(value.startsWith("http")) { "Not a valid URL [$value]" }
    }

    val urlString
        get() = value
    val url: URL
        get() = with(URL(value)) {
            return URI(protocol, userInfo, host, port, path, query, ref).toURL() // escapes characters this way
        }
    val lastPathSegment: String
        get() =
            if (value.last() == '/') value.dropLast(1).substringAfterLast("/")
            else value.substringAfterLast("/")
}

data class PageUrl(private val value: String) : Url(value)
data class ImgUrl(private val value: String) : Url(value)