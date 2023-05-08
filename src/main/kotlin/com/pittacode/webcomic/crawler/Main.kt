package com.pittacode.webcomic.crawler

// TODO assumes that src="..." is always without any spaces or in someway separated in the source html
internal val imgTagRegex = Regex("<img[^>]*/>", RegexOption.IGNORE_CASE)
internal val imgTagSrcRegex = Regex("src=\"[^\"]*\"", RegexOption.IGNORE_CASE)

fun main(args: Array<String>) {
    println("Hello World!")

    // Try adding program arguments via Run/Debug configuration.
    // Learn more about running applications: https://www.jetbrains.com/help/idea/running-applications.html.
    println("Program arguments: ${args.joinToString()}")
}

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
                fileName = Filename("${index.toString().padStart(3, '0')}-${imgSrc.substringAfterLast('/')}")
            )
        }
        .toList()
}

internal data class ImgDlLink(val link: Link, val fileName: Filename)

@JvmInline
internal value class Link(val value: String)

@JvmInline
internal value class Filename(val value: String)