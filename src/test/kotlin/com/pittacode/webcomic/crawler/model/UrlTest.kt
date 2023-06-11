package com.pittacode.webcomic.crawler.model

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class UrlTest {
    @Test
    fun name() {
        println(ImgUrl("https://comicaurora.com/aurora/0-1-1/"))
        println(ImgUrl("https://comicaurora.com/aurora/0-1-1/").url)
        println(ImgUrl("https://comicaurora.com/aurora/0-1-1/").urlString)
        println(ImgUrl("https://comicaurora.com/aurora/0-1-1/").lastPathSegment)
    }
}