package com.pittacode.webcomic.crawler.aurora

import com.pittacode.webcomic.crawler.core.FindNextPageStrategy
import it.skrape.selects.Doc
import it.skrape.selects.and
import it.skrape.selects.eachHref
import it.skrape.selects.html5.a

internal object FindNextAuroraPage : FindNextPageStrategy() {

    override fun Doc.nextPageLinksSelector(): List<String> =
        a {
            withClass = "next-webcomic-link" and "webcomic-link"
            findAll {
                eachHref
            }
        }
}