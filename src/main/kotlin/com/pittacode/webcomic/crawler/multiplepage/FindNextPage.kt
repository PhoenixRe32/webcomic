package com.pittacode.webcomic.crawler.multiplepage

import com.pittacode.webcomic.crawler.model.PageUrl

interface FindNextPage {
    fun of(currentPage: PageUrl): PageUrl?
}

