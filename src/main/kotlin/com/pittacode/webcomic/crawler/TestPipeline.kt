package com.pittacode.webcomic.crawler

import com.pittacode.webcomic.crawler.aurora.FindAuroraComicImages
import com.pittacode.webcomic.crawler.aurora.FindNextAuroraPage
import com.pittacode.webcomic.crawler.core.model.ComicImage
import com.pittacode.webcomic.crawler.core.model.PageUrl
import com.pittacode.webcomic.crawler.core.multiplepage.DefaultComicImageDownloader
import com.pittacode.webcomic.crawler.core.startCrawling
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking

suspend fun main() {
    // Aurora
    val startingLink = "https://comicaurora.com/aurora/1-6-25"
    val findComicImages = FindAuroraComicImages()
    val findNextPage = FindNextAuroraPage

    // K6BD
//    val startingLink = "https://killsixbilliondemons.com/comic/breaker-of-infinities-1-37-to-1-38"
//    val findComicImages = FindK6bdComicImages()
//    val findNextPage = FindNextK6bdPage

    startCrawling(
        startingLink = startingLink,
        findComicImages = findComicImages,
        findNextPage = findNextPage,
        comicImageDownloader = DefaultComicImageDownloader("tmp"),
        crawlingStopCondition = { lci: List<ComicImage>, pu: PageUrl? -> pu?.urlString?.contains("27")?:true }
    )

    runBlocking {
        delay(15000L)
    }
}

