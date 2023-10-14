package com.pittacode.webcomic.crawler.heroreturns

import com.pittacode.webcomic.crawler.core.FindNextPageStrategy
import it.skrape.selects.Doc
import it.skrape.selects.eachHref
import it.skrape.selects.html5.a

internal object FindNextTheHeroReturnsPage : FindNextPageStrategy() {

    override fun Doc.nextPageLinksSelector(): List<String> =
        a {
            findAll {
                filter {
                    it.text.contains("Next")
                }.eachHref
            }
        }
}