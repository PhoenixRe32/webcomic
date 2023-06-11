package com.pittacode.webcomic.crawler.multiplepage

import com.pittacode.webcomic.crawler.model.PageUrl
import it.skrape.core.htmlDocument
import it.skrape.fetcher.HttpFetcher
import it.skrape.fetcher.response
import it.skrape.fetcher.skrape
import it.skrape.selects.and
import it.skrape.selects.eachHref
import it.skrape.selects.html5.a
import mu.KotlinLogging

interface FindNextPage {
    fun of(currentPage: PageUrl): PageUrl?
}

object FindNextAuroraPage : FindNextPage {
    private val logger = KotlinLogging.logger {}

    override fun of(currentPage: PageUrl): PageUrl? {
        val result = skrape(HttpFetcher) {
            request {
                url = currentPage.urlString
            }

            response {
                htmlDocument {
                    relaxed = true
                    a {
                        withClass = "next-webcomic-link" and "webcomic-link"
                        findAll {
                            eachHref
                        }.toSet()
                    }
                }
            }
        }
        logger.info { "Next page links found: $result" }
        return result.firstOrNull()?.let(::PageUrl)
    }
}