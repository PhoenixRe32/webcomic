package com.pittacode.webcomic.crawler.core

import com.pittacode.webcomic.crawler.core.model.PageUrl
import it.skrape.core.htmlDocument
import it.skrape.fetcher.HttpFetcher
import it.skrape.fetcher.response
import it.skrape.fetcher.skrape
import it.skrape.selects.Doc
import mu.KotlinLogging

private val logger = KotlinLogging.logger {}

interface FindNextPage {
    fun of(currentPage: PageUrl): PageUrl?
}

abstract class FindNextPageStrategy : FindNextPage {

    override fun of(currentPage: PageUrl): PageUrl? {
        val result = skrape(HttpFetcher) {
            request {
                url = currentPage.urlString
            }

            response {
                htmlDocument {
                    relaxed = true
                    nextPageLinksSelector()
                }
            }
        }
        return result
            .toSet()
            .firstOrNull()
            ?.let(::PageUrl)
            .also { log(it) }
    }

    protected abstract fun Doc.nextPageLinksSelector(): List<String>
}

fun log(pageUrl: PageUrl?) {
    when (pageUrl == null) {
        true -> logger.warn { "Found no images" }
        false -> logger.info { "Next page link found: ${pageUrl.urlString}" }
    }
}