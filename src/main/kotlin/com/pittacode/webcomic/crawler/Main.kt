package com.pittacode.webcomic.crawler

import kotlin.io.path.Path

fun main(args: Array<String>) {
    val htmlBlockFile = Path(args[0])
    HtmlBlockImageSaverReader().processAndSave(htmlBlockFile)
}