package com.pittacode.webcomic.crawler.multiplepage

import com.pittacode.webcomic.crawler.model.ComicImage
import com.pittacode.webcomic.crawler.model.PageUrl

interface FindAllPages {
    fun startingFrom(startingPage: PageUrl): List<ComicImage>
}

open class FindAllPagesDefault(
    private val findComicImages: FindComicImages,
    private val findNextPage: FindNextPage,
    private val comicImageDownloader: ComicImageDownloader
) : FindAllPages {

    override fun startingFrom(startingPage: PageUrl): List<ComicImage> {
        return findPagesStartingFrom(startingPage)
    }

    private fun findPagesStartingFrom(currentPage: PageUrl): List<ComicImage> {
        val comicImages = findComicImages.on(currentPage)
        comicImageDownloader.downloadAndSave(comicImages)
        val nextPage = findNextPage.of(currentPage)
        return when {
            nextPage == null -> comicImages // default assumption is that no next page manifests as null (TODO use sealed types)
            nextPage.isNotTheNextPage(currentPage) -> comicImages
//            nextPage.urlString.contains("ksbd-2-0") -> comicImages
            else -> comicImages + findPagesStartingFrom(nextPage)
        }
    }

    // Default assumption is that if it's the end there will be no next page but I
    // also noticed pages that take you to the same page so just put as default this
    protected fun PageUrl.isNotTheNextPage(currentPage: PageUrl): Boolean {
        return currentPage == this
    }
}