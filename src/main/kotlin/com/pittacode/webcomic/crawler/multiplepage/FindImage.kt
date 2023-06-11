package com.pittacode.webcomic.crawler.multiplepage

import com.pittacode.webcomic.crawler.model.ComicImage
import com.pittacode.webcomic.crawler.model.ImgUrl
import com.pittacode.webcomic.crawler.model.PageUrl
import it.skrape.core.htmlDocument
import it.skrape.fetcher.HttpFetcher
import it.skrape.fetcher.response
import it.skrape.fetcher.skrape
import it.skrape.selects.and
import it.skrape.selects.attribute
import it.skrape.selects.html5.a
import it.skrape.selects.html5.img
import mu.KotlinLogging

interface FindComicImage {
    fun on(page: PageUrl): ComicImage
}


object FindAuroraComicImage : FindComicImage {
    private val logger = KotlinLogging.logger {}

    override fun on(page: PageUrl): ComicImage {
        val result = skrape(HttpFetcher) {
            request {
                url = page.urlString
            }

            response {
                htmlDocument {
                    relaxed = true
                    a {
                        withClass = "next-webcomic-link" and "webcomic-link"
                        findAll {
                            img {
                                withAttributeKey = "srcset"
                                findAll {
                                    map {
                                        ComicImage(
                                            pageUrl = page,
                                            imgUrl = ImgUrl(value = attribute("src")),
                                            title = attribute("title")
                                        )
                                    }
                                }.toSet()
                            }
                        }
                    }
                }
            }
        }
        logger.info { "Comic image links found: ${result.map(ComicImage::imgUrl)}" }
        return result.first()
    }
}