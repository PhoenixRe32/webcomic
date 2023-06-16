package com.pittacode.webcomic.crawler.model

sealed interface ComicImage {
    fun pageNumber(): String
    fun imageName(): String
    fun title(): String
    fun extension(): String
    val imgUrl: ImgUrl
}

data class BaseComicImage(
    override val imgUrl: ImgUrl,
    val pageUrl: PageUrl,
    val title: String,
) : ComicImage {
    override fun pageNumber(): String =
        pageUrl.lastPathSegment

    override fun imageName(): String =
        imgUrl.lastPathSegment.substringBeforeLast('.')

    override fun title(): String =
        title.sanitiseForFileName()

    override fun extension(): String =
        imgUrl.lastPathSegment.substringAfterLast('.')
}

data class IndexedComicImage(
    override val imgUrl: ImgUrl,
    val pageUrl: PageUrl,
    val title: String,
    val index: Int
) : ComicImage {
    override fun pageNumber(): String =
        "${index.toString().padStart(4, '0')} ${pageUrl.lastPathSegment}"

    override fun imageName(): String =
        imgUrl.lastPathSegment.substringBeforeLast('.')

    override fun title(): String =
        title.sanitiseForFileName()

    override fun extension(): String =
        imgUrl.lastPathSegment.substringAfterLast('.')
}

private fun String.sanitiseForFileName(): String =
    replace("\\p{Punct}".toRegex(), "_")
        .replace("\\s".toRegex(), "_")
        .replace("__+".toRegex(), "_")
        .replace("_s_", "s_")
        .replace("_t_", "t_")
        .dropLastWhile { it == '_' }
        .take(100)
