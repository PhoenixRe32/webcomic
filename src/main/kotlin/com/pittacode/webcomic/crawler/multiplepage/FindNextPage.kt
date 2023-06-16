package com.pittacode.webcomic.crawler.multiplepage

import com.pittacode.webcomic.crawler.model.PageUrl
import mu.KotlinLogging

private val logger = KotlinLogging.logger {}

interface FindNextPage {
    fun of(currentPage: PageUrl): PageUrl?
}

fun PageUrl?.log() {
    when (this == null) {
        true -> logger.warn { "Found no images" }
        false -> logger.info { "Next page link found: ${this.urlString}" }
    }
}