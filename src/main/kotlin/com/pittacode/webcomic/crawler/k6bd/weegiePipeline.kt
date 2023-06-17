package com.pittacode.webcomic.crawler.k6bd

import com.pittacode.webcomic.crawler.core.multiplepage.DefaultComicImageDownloader
import com.pittacode.webcomic.crawler.core.startCrawling
import com.pittacode.webcomic.crawler.weegie.FindNextWeegiePage
import com.pittacode.webcomic.crawler.weegie.FindWeegieComicImages

fun main() {
    val comicImagesFound = startCrawling(
        startingLink = "https://killsixbilliondemons.com/comic/kill-six-billion-demons-chapter-1/",
        findComicImages = FindWeegieComicImages(),
        findNextPage = FindNextWeegiePage,
        comicImageDownloader = DefaultComicImageDownloader("K6BD")
    )
    // this actually could in theory exit before the last few images download...
}