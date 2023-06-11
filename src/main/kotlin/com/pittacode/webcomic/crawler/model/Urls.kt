package com.pittacode.webcomic.crawler.model

sealed class Url(private val value: String) {
    init {
        require(value.startsWith("http")) { "Not a valid URL" }
    }
    fun value() = value
}

data class PageUrl(val value: String): Url(value)
data class ImgUrl(val value: String): Url(value)