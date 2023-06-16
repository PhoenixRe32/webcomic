package com.pittacode.webcomic.crawler.k6bd

import com.pittacode.webcomic.crawler.model.ComicImage
import com.pittacode.webcomic.crawler.model.ImgUrl
import com.pittacode.webcomic.crawler.model.IndexedComicImage
import com.pittacode.webcomic.crawler.model.PageUrl
import com.pittacode.webcomic.crawler.multiplepage.FindComicImages
import com.pittacode.webcomic.crawler.multiplepage.log
import it.skrape.core.htmlDocument
import it.skrape.fetcher.HttpFetcher
import it.skrape.fetcher.response
import it.skrape.fetcher.skrape
import it.skrape.selects.html5.div
import it.skrape.selects.html5.img
import mu.KotlinLogging
import java.util.concurrent.atomic.AtomicInteger

internal object FindK6bdComicImages : FindComicImages {
    private val logger = KotlinLogging.logger {}
    private val index = AtomicInteger(601)

    override fun on(page: PageUrl): List<ComicImage> {
        val result = skrape(HttpFetcher) {
            request {
                url = page.urlString
            }

            response {
                htmlDocument {
                    relaxed = true
                    div {
                        withId = "comic"
                        findAll {
                            img {
                                findAll {
                                    filter { it.hasAttribute("src") }
                                        .map {
                                            IndexedComicImage(
                                                pageUrl = page,
                                                imgUrl = ImgUrl(value = it attribute "src"),
                                                title = it attribute "title",
                                                index = index.getAndIncrement()
                                            )
                                        }
                                }
                            }
                        }
                    }
                }
            }
        }

        return result.also { it.log() }
    }
}