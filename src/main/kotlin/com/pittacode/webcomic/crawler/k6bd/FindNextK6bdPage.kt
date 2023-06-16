package com.pittacode.webcomic.crawler.k6bd

import com.pittacode.webcomic.crawler.model.PageUrl
import com.pittacode.webcomic.crawler.multiplepage.FindNextPage
import com.pittacode.webcomic.crawler.multiplepage.log
import it.skrape.core.htmlDocument
import it.skrape.fetcher.HttpFetcher
import it.skrape.fetcher.response
import it.skrape.fetcher.skrape
import it.skrape.selects.and
import it.skrape.selects.eachHref
import it.skrape.selects.html5.a
import mu.KotlinLogging

internal object FindNextK6bdPage : FindNextPage {
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
                        withClass = "comic-nav-next" and "navi-next"
                        findAll {
                            eachHref
                        }.toSet()
                    }
                }
            }
        }

        return result.firstOrNull()?.let(::PageUrl).also { it.log() }
    }
}