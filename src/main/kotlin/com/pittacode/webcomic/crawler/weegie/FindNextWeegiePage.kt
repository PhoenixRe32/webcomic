package com.pittacode.webcomic.crawler.weegie

import com.pittacode.webcomic.crawler.core.FindNextPageStrategy
import it.skrape.selects.Doc

internal object FindNextWeegiePage : FindNextPageStrategy() {

    // a webcomic that contains all the pages in one "archive" page
    // there is no next page
    override fun Doc.nextPageLinksSelector(): List<String> = emptyList()
}