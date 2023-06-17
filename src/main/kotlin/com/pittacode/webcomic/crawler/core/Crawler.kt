package com.pittacode.webcomic.crawler.core

import com.pittacode.webcomic.crawler.core.model.ComicImage
import com.pittacode.webcomic.crawler.core.model.PageUrl

fun startCrawling(
    startingLink: String,
    findComicImages: FindComicImages,
    findNextPage: FindNextPage,
    comicImageDownloader: ComicImageDownloader,
    crawlingStopCondition: (comicImages: List<ComicImage>, nextPage: PageUrl?) -> Boolean = { _, _ -> false }
): List<ComicImage> {

    val findAllPages = FindAllPagesDefault(
        findComicImages = findComicImages,
        findNextPage = findNextPage,
        comicImageDownloader = comicImageDownloader,
        isStopCondition = crawlingStopCondition
    )

    return findAllPages.startingFrom(PageUrl(startingLink))
}