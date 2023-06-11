package com.pittacode.webcomic.crawler.model

data class ComicImage(
    val pageUrl: PageUrl,
    val imgUrl: ImgUrl,
    val title: String
)