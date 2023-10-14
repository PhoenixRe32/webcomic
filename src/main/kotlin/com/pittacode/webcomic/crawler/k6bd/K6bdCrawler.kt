package com.pittacode.webcomic.crawler.k6bd

import com.pittacode.webcomic.crawler.core.DefaultComicImageDownloader
import com.pittacode.webcomic.crawler.core.startCrawling
import com.pittacode.webcomic.crawler.heroreturns.FindTheHeroReturnsComicImages
import com.pittacode.webcomic.crawler.heroreturns.FindNextTheHeroReturnsPage

fun main() {
    val comicImagesFound = startCrawling(
        startingLink = "https://killsixbilliondemons.com/comic/kill-six-billion-demons-chapter-1/",
        findComicImages = FindTheHeroReturnsComicImages(),
        findNextPage = FindNextTheHeroReturnsPage,
        comicImageDownloader = DefaultComicImageDownloader { "K6BD" }
    )
    // this actually could in theory exit before the last few images download...
}