package com.pittacode.webcomic.crawler.aurora

import com.pittacode.webcomic.crawler.model.BaseComicImage
import com.pittacode.webcomic.crawler.model.ComicImage
import com.pittacode.webcomic.crawler.model.ImgUrl
import com.pittacode.webcomic.crawler.model.PageUrl
import com.pittacode.webcomic.crawler.multiplepage.FindComicImages
import com.pittacode.webcomic.crawler.multiplepage.log
import it.skrape.core.htmlDocument
import it.skrape.fetcher.HttpFetcher
import it.skrape.fetcher.response
import it.skrape.fetcher.skrape
import it.skrape.selects.html5.a
import it.skrape.selects.html5.div
import it.skrape.selects.html5.img
import mu.KotlinLogging

internal object FindAuroraComicImages : FindComicImages {
    private val logger = KotlinLogging.logger {}

    override fun on(page: PageUrl): List<ComicImage> {
        val result = skrape(HttpFetcher) {
            request {
                url = page.urlString
            }

            response {
                htmlDocument {
                    relaxed = true
                    div {
                        withClass = "webcomic-media"
                        findAll {
                            a {
                                withClass = "next-webcomic-link"
                                findAll {
                                    img {
                                        findAll {
                                            map {
                                                BaseComicImage(
                                                    pageUrl = page,
                                                    imgUrl = ImgUrl(value = it attribute "src"),
                                                    title = it attribute "title"
                                                )
                                            }
                                        }
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