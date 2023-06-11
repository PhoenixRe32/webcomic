package com.pittacode.webcomic.crawler.multiplepage

import com.pittacode.webcomic.crawler.model.PageUrl

interface FindAllPages {
    fun startingFrom(startingPage: PageUrl): List<PageUrl>
}

open class FindAllPagesDefault(
    private val findNextPage: FindNextPage
) : FindAllPages {

    override fun startingFrom(startingPage: PageUrl): List<PageUrl> {
        return findPagesStartingFrom(startingPage)
    }

    private fun findPagesStartingFrom(currentPage: PageUrl): List<PageUrl> {
        val nextPage = findNextPage.of(currentPage)
        return when {
            nextPage == null -> listOf(currentPage) // default assumption is that no next page manifests as null (TODO use sealed types)
            nextPage.isNotTheNextPage(currentPage) -> listOf(currentPage)
            nextPage.value().contains("1-1-2") -> listOf(currentPage)
            else -> listOf(currentPage) + findPagesStartingFrom(nextPage)
        }
    }

    // Default assumption is that if it's the end there will be no next page but I
    // also noticed pages that take you to the same page so just put as default this
    protected fun PageUrl.isNotTheNextPage(currentPage: PageUrl): Boolean {
        return currentPage == this
    }
}