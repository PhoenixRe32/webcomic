package com.pittacode.webcomic.crawler.multiplepage

import com.pittacode.webcomic.crawler.model.ComicImage
import com.pittacode.webcomic.crawler.model.PageUrl

interface FindComicImages {
    fun on(page: PageUrl): List<ComicImage>
}


