package com.pittacode.webcomic.crawler.multiplepage

import com.pittacode.webcomic.crawler.model.ComicImage
import com.pittacode.webcomic.crawler.model.PageUrl

interface FindAllPages {
    fun startingFrom(startingPage: PageUrl): List<ComicImage>
}

open class FindAllPagesDefault(
    private val findComicImage: FindComicImage,
    private val findNextPage: FindNextPage
) : FindAllPages {

    override fun startingFrom(startingPage: PageUrl): List<ComicImage> {
        return findPagesStartingFrom(startingPage)
    }

    private fun findPagesStartingFrom(currentPage: PageUrl): List<ComicImage> {
        val comicImage = findComicImage.on(currentPage)
        val nextPage = findNextPage.of(currentPage)
        return when {
            nextPage == null -> listOf(comicImage) // default assumption is that no next page manifests as null (TODO use sealed types)
            nextPage.isNotTheNextPage(currentPage) -> listOf(comicImage)
            else -> listOf(comicImage) + findPagesStartingFrom(nextPage)
        }
    }

    // Default assumption is that if it's the end there will be no next page but I
    // also noticed pages that take you to the same page so just put as default this
    protected fun PageUrl.isNotTheNextPage(currentPage: PageUrl): Boolean {
        return currentPage == this
    }
}