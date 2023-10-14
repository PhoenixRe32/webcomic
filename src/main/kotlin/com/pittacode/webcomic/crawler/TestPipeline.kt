package com.pittacode.webcomic.crawler

import com.pittacode.webcomic.crawler.aurora.FindAuroraComicImages
import com.pittacode.webcomic.crawler.aurora.FindNextAuroraPage
import com.pittacode.webcomic.crawler.core.DefaultComicImageDownloader
import com.pittacode.webcomic.crawler.core.model.ComicImage
import com.pittacode.webcomic.crawler.core.model.PageUrl
import com.pittacode.webcomic.crawler.core.startCrawling
import com.pittacode.webcomic.crawler.heroreturns.ChapterNameSupplier
import com.pittacode.webcomic.crawler.heroreturns.FindNextTheHeroReturnsPage
import com.pittacode.webcomic.crawler.heroreturns.FindTheHeroReturnsComicImages
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking

suspend fun main() {
    // Aurora
//    val startingLink = "https://comicaurora.com/aurora/1-6-25"
//    val findComicImages = FindAuroraComicImages()
//    val findNextPage = FindNextAuroraPage// Aurora

    // Hero
    val startingLink = "https://reaperscans.com/comics/2706-the-hero-returns/chapters/73769407-chapter-4"
    val findComicImages = FindTheHeroReturnsComicImages()
    val findNextPage = FindNextTheHeroReturnsPage

    // K6BD
//    val startingLink = "https://killsixbilliondemons.com/comic/breaker-of-infinities-1-37-to-1-38"
//    val findComicImages = FindK6bdComicImages()
//    val findNextPage = FindNextK6bdPage

    val chapters: (pageUrl:PageUrl) -> String = ChapterNameSupplier()::getChapterName
    val tmp: () -> String = {"tmp"}
    startCrawling(
        startingLink = startingLink,
        findComicImages = findComicImages,
        findNextPage = findNextPage,
        comicImageDownloader = DefaultComicImageDownloader(chapters),
        crawlingStopCondition = { lci: List<ComicImage>, pu: PageUrl? -> pu?.urlString?.contains("chapter-5") ?: true }
    )

    runBlocking {
        delay(15000L)
    }
}

