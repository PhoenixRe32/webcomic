package com.pittacode.webcomic.crawler.aurora

import com.pittacode.webcomic.crawler.core.model.BaseComicImage
import com.pittacode.webcomic.crawler.core.model.ComicImage
import com.pittacode.webcomic.crawler.core.model.ImgUrl
import com.pittacode.webcomic.crawler.core.model.PageUrl
import com.pittacode.webcomic.crawler.core.multiplepage.FindComicImagesStrategy
import it.skrape.selects.Doc
import it.skrape.selects.DocElement
import it.skrape.selects.html5.a
import it.skrape.selects.html5.div
import it.skrape.selects.html5.img

internal class FindAuroraComicImages : FindComicImagesStrategy() {

    override fun Doc.comicImageElementsSelector(): List<DocElement> =
        div {
            withClass = "webcomic-media"
            findAll {
                a {
                    withClass = "next-webcomic-link"
                    findAll {
                        img {
                            findAll {
                                this
                            }
                        }
                    }
                }
            }
        }

    override fun DocElement.asComicImage(page: PageUrl): ComicImage =
        BaseComicImage(
            pageUrl = page,
            imgUrl = ImgUrl(value = this attribute "src"),
            title = this attribute "title"
        )
}