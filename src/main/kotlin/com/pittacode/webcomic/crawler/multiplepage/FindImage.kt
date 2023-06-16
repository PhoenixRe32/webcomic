package com.pittacode.webcomic.crawler.multiplepage

import com.pittacode.webcomic.crawler.model.ComicImage
import com.pittacode.webcomic.crawler.model.PageUrl
import mu.KotlinLogging

private val logger = KotlinLogging.logger {}

interface FindComicImages {
    fun on(page: PageUrl): List<ComicImage>
}

fun List<ComicImage>.log() {
    when {
        isEmpty() -> logger.warn { "Found no images" }
        size > 1 -> logger.warn { "Found more than 1 image when looking for a div" }
    }
    logger.info { "Comic image links found: ${map { it.imgUrl.url }}" }
}