package com.pittacode.webcomic.crawler.weegie

import com.pittacode.webcomic.crawler.core.multiplepage.DefaultComicImageDownloader
import com.pittacode.webcomic.crawler.core.startCrawling
import com.pittacode.webcomic.crawler.weegie.FindNextWeegiePage
import com.pittacode.webcomic.crawler.weegie.FindWeegieComicImages
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking

fun main() {
    val comicImagesFound = startCrawling(
        startingLink = "https://www.kevinbolk.com/comic/suckstobeweegie/",
        findComicImages = FindWeegieComicImages(),
        findNextPage = FindNextWeegiePage,
        comicImageDownloader = DefaultComicImageDownloader("Sucks to be Weegie")
    )
    // this actually could in theory exit before the last few images download...
    runBlocking {
        delay(120000L)
    }
}