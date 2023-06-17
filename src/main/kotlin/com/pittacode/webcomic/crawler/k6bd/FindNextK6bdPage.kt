package com.pittacode.webcomic.crawler.k6bd

import com.pittacode.webcomic.crawler.core.multiplepage.FindNextPageStrategy
import it.skrape.selects.Doc
import it.skrape.selects.and
import it.skrape.selects.eachHref
import it.skrape.selects.html5.a

internal object FindNextK6bdPage : FindNextPageStrategy() {

    override fun Doc.nextPageLinksSelector(): List<String> =
        a {
            withClass = "comic-nav-next" and "navi-next"
            findAll {
                eachHref
            }
        }
}