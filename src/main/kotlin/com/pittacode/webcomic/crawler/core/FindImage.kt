package com.pittacode.webcomic.crawler.core

import com.pittacode.webcomic.crawler.core.model.ComicImage
import com.pittacode.webcomic.crawler.core.model.PageUrl
import it.skrape.core.htmlDocument
import it.skrape.fetcher.HttpFetcher
import it.skrape.fetcher.response
import it.skrape.fetcher.skrape
import it.skrape.selects.Doc
import it.skrape.selects.DocElement
import mu.KotlinLogging
import java.util.concurrent.atomic.AtomicInteger

private val logger = KotlinLogging.logger {}

interface FindComicImages {
    fun on(page: PageUrl): List<ComicImage>
}

// base class where the name of the page works nicely as indexing or numbering pages
abstract class FindComicImagesStrategy : FindComicImages {
    override fun on(page: PageUrl): List<ComicImage> {
        val result = skrape(HttpFetcher) {
            request {
                url = page.urlString
            }

            response {
                htmlDocument {
                    relaxed = true
                    comicImageElementsSelector()
                }
            }
        }

        return result
            .map { element -> element.asComicImage(page) }
            .also { log(page, it) }
    }

    protected abstract fun Doc.comicImageElementsSelector(): List<DocElement>

    protected abstract fun DocElement.asComicImage(page: PageUrl): ComicImage
}

// base class where the name of the page doesn't work or is not consistent so we need to create
// our own numbering of pages
abstract class FindComicImagesStrategyWithIndexing(startingIndex: Int = 0) : FindComicImagesStrategy() {
    protected val index = AtomicInteger(startingIndex)
}

fun log(page:PageUrl, comicImages: List<ComicImage>) {
    when {
        comicImages.isEmpty() -> logger.warn { "Found no images at ${page.urlString}" }
        comicImages.size > 1 -> logger.warn { "Found more than 1 image" }
    }
    val comicImagesLinks = comicImages
        .map { it.imgUrl.url }
        .joinToString("\n", "\n", "\n")
    if (comicImagesLinks.isNotEmpty())
        logger.info { "Comic image links found at ${page.urlString}: $comicImagesLinks" }
}