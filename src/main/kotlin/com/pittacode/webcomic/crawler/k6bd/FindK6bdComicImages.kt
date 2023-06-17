package com.pittacode.webcomic.crawler.k6bd

import com.pittacode.webcomic.crawler.core.model.ComicImage
import com.pittacode.webcomic.crawler.core.model.ImgUrl
import com.pittacode.webcomic.crawler.core.model.IndexedComicImage
import com.pittacode.webcomic.crawler.core.model.PageUrl
import com.pittacode.webcomic.crawler.core.multiplepage.FindComicImagesStrategyWithIndexing
import it.skrape.selects.Doc
import it.skrape.selects.DocElement
import it.skrape.selects.html5.div
import it.skrape.selects.html5.img

internal class FindK6bdComicImages(startingIndex: Int = 0) : FindComicImagesStrategyWithIndexing(startingIndex) {

    override fun Doc.comicImageElementsSelector(): List<DocElement> =
        div {
            withId = "comic"
            findAll {
                img {
                    findAll {
                        filter { it.hasAttribute("src") }
                    }
                }
            }
        }

    override fun DocElement.asComicImage(page: PageUrl): ComicImage =
        IndexedComicImage(
            pageUrl = page,
            imgUrl = ImgUrl(value = this attribute "src"),
            title = this attribute "title",
            index = index.getAndIncrement()
        )
}