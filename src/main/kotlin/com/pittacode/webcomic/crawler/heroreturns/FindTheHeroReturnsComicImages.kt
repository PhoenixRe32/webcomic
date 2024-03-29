package com.pittacode.webcomic.crawler.heroreturns

import com.pittacode.webcomic.crawler.core.FindComicImagesStrategyWithIndexing
import com.pittacode.webcomic.crawler.core.model.ComicImage
import com.pittacode.webcomic.crawler.core.model.ImgUrl
import com.pittacode.webcomic.crawler.core.model.IndexedComicImage
import com.pittacode.webcomic.crawler.core.model.PageUrl
import it.skrape.selects.Doc
import it.skrape.selects.DocElement
import it.skrape.selects.html5.img

internal class FindTheHeroReturnsComicImages(startingIndex: Int = 0) :
    FindComicImagesStrategyWithIndexing(startingIndex) {

    override fun Doc.comicImageElementsSelector(): List<DocElement> =
        img {
            findAll {
                filter {
                    it.hasAttribute("src")
                            && it.attribute("src").contains("media.reaperscans.com/file/")
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