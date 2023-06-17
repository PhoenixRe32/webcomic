package com.pittacode.webcomic.htmlblock

// TODO assumes that src="..." is always without any spaces or in someway separated in the source html
private val imgTagRegex = Regex("<img[^>]*>", RegexOption.IGNORE_CASE)
private val imgTagSrcRegex = Regex("src=\"[^\"]*\"", RegexOption.IGNORE_CASE)

internal fun extractImgLinksInOrder(blockOfHtmlSource: String): List<ImgDlLink> {
    return imgTagRegex.findAll(blockOfHtmlSource).map { it.value }
        .mapNotNull { imgTag ->
            imgTagSrcRegex.find(imgTag)?.value
                .also {
                    if (it == null) println("$imgTag did not have a source attached to it")
                    else println("Found img source @ $it")
                }
        }
        .map { it.substringAfter("src=\"").trimEnd('"') }
        .mapIndexed { index, imgSrc ->
            ImgDlLink(
                link = Link(imgSrc),
                fileName = Filename("${index.toString().padStart(3, '0')}-${sanitise(imgSrc.substringAfterLast('/'))}")
            )
        }
        .toList()
}

fun sanitise(filename: String): String {
    // very naive way to determine if the link path ends in a file name or it's another link thingy
    // TODO make it a bit smarter but it's never going to be 100% correct, take it with each site you encounter
    val extensionProbably = filename.substringAfterLast('.')
    if ((3..4).contains(extensionProbably.length)) return filename
    return "page.jpg"
}

internal data class ImgDlLink(val link: Link, val fileName: Filename)

@JvmInline
internal value class Link(val value: String) {
    override fun toString(): String {
        return value
    }
}

@JvmInline
internal value class Filename(val value: String) {
    override fun toString(): String {
        return value
    }
}