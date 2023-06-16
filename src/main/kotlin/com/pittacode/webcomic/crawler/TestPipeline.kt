package com.pittacode.webcomic.crawler

import com.pittacode.webcomic.crawler.model.PageUrl
import com.pittacode.webcomic.crawler.aurora.FindAuroraComicImages

fun main() {
    FindAuroraComicImages.on(PageUrl("https://comicaurora.com/aurora/1-6-26/"))
}