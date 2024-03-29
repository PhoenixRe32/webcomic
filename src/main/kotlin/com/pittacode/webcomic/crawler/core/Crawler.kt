package com.pittacode.webcomic.crawler.core

import com.pittacode.webcomic.crawler.core.model.ComicImage
import com.pittacode.webcomic.crawler.core.model.PageUrl
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking

interface Crawler {
    fun startingFrom(startingPage: PageUrl): List<ComicImage>
}

open class CrawlerDefault(
    private val findComicImages: FindComicImages,
    private val findNextPage: FindNextPage,
    private val comicImageDownloader: ComicImageDownloader,
    private val isStopCondition: (comicImages: List<ComicImage>, nextPage: PageUrl?) -> Boolean = { _, _ -> false }
) : Crawler {

    override fun startingFrom(startingPage: PageUrl): List<ComicImage> {
        return findPagesStartingFrom(startingPage)
    }

    private fun findPagesStartingFrom(currentPage: PageUrl): List<ComicImage> {
        // TODO SOOOOOO Ugly, only for chapter based webcomics
        runBlocking {
            delay(5000L)
        }
        val comicImages = findComicImages.on(currentPage)
        comicImageDownloader.downloadAndSave(comicImages, currentPage)

        val nextPage = findNextPage.of(currentPage)
        return when {
            nextPage == null -> comicImages // default assumption is that no next page manifests as null (TODO use sealed types)
            nextPage.isNotTheNextPage(currentPage) -> comicImages
            isStopCondition(comicImages, nextPage) -> comicImages
            else -> comicImages + findPagesStartingFrom(nextPage)
        }
    }

    // Default assumption is that if it's the end there will be no next page but I
    // also noticed pages that take you to the same page so just put as default this
    protected open fun PageUrl.isNotTheNextPage(currentPage: PageUrl): Boolean {
        return currentPage == this
    }
}