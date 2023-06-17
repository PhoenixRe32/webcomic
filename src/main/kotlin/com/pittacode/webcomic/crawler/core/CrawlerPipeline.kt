package com.pittacode.webcomic.crawler.core

import com.pittacode.webcomic.crawler.core.model.ComicImage
import com.pittacode.webcomic.crawler.core.model.PageUrl
import com.pittacode.webcomic.crawler.core.multiplepage.ComicImageDownloader
import com.pittacode.webcomic.crawler.core.multiplepage.FindAllPagesDefault
import com.pittacode.webcomic.crawler.core.multiplepage.FindComicImages
import com.pittacode.webcomic.crawler.core.multiplepage.FindNextPage

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