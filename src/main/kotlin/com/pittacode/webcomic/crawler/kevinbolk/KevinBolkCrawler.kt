package com.pittacode.webcomic.crawler.kevinbolk

import com.pittacode.webcomic.crawler.core.DefaultComicImageDownloader
import com.pittacode.webcomic.crawler.core.startCrawling
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking

suspend fun main() {
    coroutineScope {
        val comicImagesFound = startCrawling(
            startingLink = "https://www.kevinbolk.com/comic/ensign-sue-must-die/",
            findComicImages = FindKevinBokComicImages(),
            findNextPage = FindNextKevinBolkPage,
            comicImageDownloader = DefaultComicImageDownloader("Ensign [01] Ensign Sue Must Die")
        )
    }

    coroutineScope {
        val comicImagesFound = startCrawling(
            startingLink = "https://www.kevinbolk.com/comic/ensigntwo/",
            findComicImages = FindKevinBokComicImages(),
            findNextPage = FindNextKevinBolkPage,
            comicImageDownloader = DefaultComicImageDownloader("Ensign [02] The Wrath of Sue")
        )
    }

    coroutineScope {
        val comicImagesFound = startCrawling(
            startingLink = "https://www.kevinbolk.com/comic/ensign-crisis/",
            findComicImages = FindKevinBokComicImages(),
            findNextPage = FindNextKevinBolkPage,
            comicImageDownloader = DefaultComicImageDownloader("Ensign [03] Crisis of Infinite Sues")
        )
    }

    coroutineScope {
        val comicImagesFound = startCrawling(
            startingLink = "https://www.kevinbolk.com/comic/suckstobeweegie/",
            findComicImages = FindKevinBokComicImages(),
            findNextPage = FindNextKevinBolkPage,
            comicImageDownloader = DefaultComicImageDownloader("Suck to be Weegie")
        )
    }

    // this actually could in theory exit before the last few images download...
    runBlocking {
        delay(120000L)
    }
}