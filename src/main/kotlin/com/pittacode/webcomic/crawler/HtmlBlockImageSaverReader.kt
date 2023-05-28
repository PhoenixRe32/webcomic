package com.pittacode.webcomic.crawler

import java.net.URL
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import kotlin.io.path.exists
import kotlin.io.path.nameWithoutExtension

class HtmlBlockImageSaverReader {
    fun processAndSave(filePath: Path) {
        val blockOfHtmlSource: String = readFile(filePath)
        processAndSave(blockOfHtmlSource, filePath.nameWithoutExtension)
    }

    fun processAndSave(blockOfHtmlSource: String, parentDirectoryName: String) {
        val parentDirectory = create(parentDirectoryName)

        extractImgLinksInOrder(blockOfHtmlSource)
            .filterNot { parentDirectory.contains(it) }
            .map { imgDlLink ->
                val imageData = URL(imgDlLink.link.value).readBytes()
                    .also { println("Read bytes of ${imgDlLink.link}") }

                val fileToCreate = parentDirectory.resolve(imgDlLink.fileName.value).toFile()
                fileToCreate.writeBytes(imageData)
                    .also { println("Wrote bytes of ${imgDlLink.fileName}") }
            }
    }

    private fun create(directory: String): Path {
        val parentDirectory = Paths.get(directory)
        return parentDirectory
            .takeIf { it.exists() } // use path it if it exists
            ?: Files.createDirectory(parentDirectory) // otherwise create it and then use it
    }

    private fun Path.contains(imgDlLink: ImgDlLink): Boolean =
        resolve(imgDlLink.fileName.value).exists()

    private fun readFile(filePath: Path): String = Files.readString(filePath)
    // TODO figure out why resource isn't working
//        if (filePath.isAbsolute)
//            Files.readString(filePath)
//        else this::class.java.getResourceAsStream(filePath.pathString)
//            ?.bufferedReader()
//            ?.readText()
//            ?: throw RuntimeException("Couldn't open file $filePath")

}