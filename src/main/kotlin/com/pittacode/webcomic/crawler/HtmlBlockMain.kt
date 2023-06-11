package com.pittacode.webcomic.crawler

import com.pittacode.webcomic.crawler.singlepage.HtmlBlockImageSaverReader
import kotlin.io.path.Path

fun main(args: Array<String>) {
//    val htmlBlockFile = Path(args[0])
    HtmlBlockImageSaverReader().processAndSave(Path("/Users/andreasandreou/Projects/webcomic-crawler/src/main/resources/Doctor Who - The Forgotten 01.html"))
    HtmlBlockImageSaverReader().processAndSave(Path("/Users/andreasandreou/Projects/webcomic-crawler/src/main/resources/Doctor Who - The Forgotten 02.html"))
    HtmlBlockImageSaverReader().processAndSave(Path("/Users/andreasandreou/Projects/webcomic-crawler/src/main/resources/Doctor Who - The Forgotten 03.html"))
    HtmlBlockImageSaverReader().processAndSave(Path("/Users/andreasandreou/Projects/webcomic-crawler/src/main/resources/Doctor Who - The Forgotten 04.html"))
    HtmlBlockImageSaverReader().processAndSave(Path("/Users/andreasandreou/Projects/webcomic-crawler/src/main/resources/Doctor Who - The Forgotten 05.html"))
    HtmlBlockImageSaverReader().processAndSave(Path("/Users/andreasandreou/Projects/webcomic-crawler/src/main/resources/Doctor Who - The Forgotten 06.html"))
}