package com.pittacode.webcomic.crawler.aurora

import com.pittacode.webcomic.crawler.core.DefaultComicImageDownloader
import com.pittacode.webcomic.crawler.core.startCrawling

fun main() {
    val comicImagesFound = startCrawling(
        startingLink = "https://comicaurora.com/aurora/0-1-1/",
        findComicImages = FindAuroraComicImages(),
        findNextPage = FindNextAuroraPage,
        comicImageDownloader = DefaultComicImageDownloader("Aurora")
    )
    // this actually could in theory exit before the last few images download...
}