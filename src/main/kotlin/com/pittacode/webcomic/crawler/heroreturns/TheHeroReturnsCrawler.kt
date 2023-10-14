package com.pittacode.webcomic.crawler.heroreturns

import com.pittacode.webcomic.crawler.core.DefaultComicImageDownloader
import com.pittacode.webcomic.crawler.core.model.PageUrl
import com.pittacode.webcomic.crawler.core.startCrawling
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking

fun main() {

    val storageDirectorySupplier: (pageUrl:PageUrl) -> String = ChapterNameSupplier()::getChapterName
    val comicImagesFound = startCrawling(
        startingLink = "https://reaperscans.com/comics/2706-the-hero-returns/chapters/42733311-chapter-11",
        findComicImages = FindTheHeroReturnsComicImages(),
        findNextPage = FindNextTheHeroReturnsPage,
        comicImageDownloader = DefaultComicImageDownloader(storageDirectorySupplier)
    )
    // this actually could in theory exit before the last few images download...

    runBlocking {
        delay(10000L)
    }
}

class ChapterNameSupplier {
    private var i = 1
    fun getChapterName(pageUrl: PageUrl): String {
        return "The Hero Returns Chapter " + pageUrl.lastPathSegment.substringAfterLast("-")
    }
}