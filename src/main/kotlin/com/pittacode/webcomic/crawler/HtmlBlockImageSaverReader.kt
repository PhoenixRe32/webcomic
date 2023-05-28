package com.pittacode.webcomic.crawler

import java.io.File
import java.net.URL
import java.nio.file.Files
import java.nio.file.Path

class HtmlBlockImageSaverReader {
    fun processAndSave(filePath: Path) {
        val blockOfHtmlSource: String = readFile(filePath)
        processAndSave(blockOfHtmlSource)
    }

    fun processAndSave(blockOfHtmlSource: String) {
        extractImgLinksInOrder(blockOfHtmlSource)
            .map { imgDlLink ->
                val imageData = URL(imgDlLink.link.value).readBytes()
                    .also { println("Read bytes of ${imgDlLink.link}") }
                File(imgDlLink.fileName.value).writeBytes(imageData)
                    .also { println("Wrote bytes of ${imgDlLink.fileName}") }
            }
    }

    private fun readFile(filePath: Path): String = Files.readString(filePath)
    // TODO figure out why resource isn't working
//        if (filePath.isAbsolute)
//            Files.readString(filePath)
//        else this::class.java.getResourceAsStream(filePath.pathString)
//            ?.bufferedReader()
//            ?.readText()
//            ?: throw RuntimeException("Couldn't open file $filePath")

}