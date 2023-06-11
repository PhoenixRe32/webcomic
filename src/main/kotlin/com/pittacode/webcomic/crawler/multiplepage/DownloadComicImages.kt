package com.pittacode.webcomic.crawler.multiplepage

import com.pittacode.webcomic.crawler.model.ComicImage
import kotlinx.coroutines.*
import java.io.File
import java.nio.file.Path

object DownloadComicImages {

    suspend fun downloadAndSaveImages(comicImages: List<ComicImage>, parentDirectory: Path) {
        val images = mutableListOf<Deferred<File>>()
        CoroutineScope(Dispatchers.Default).launch {
            for (comicImage in comicImages) {
                images.add(async(Dispatchers.IO) { downloadAndSaveImage(comicImage, parentDirectory) })
            }
        }.join()

        println("Status: ")
        images
            .filter { it.isCompleted }
            .map { it.getCompleted() }
            .map { it.absolutePath }
            .forEach(::println)
    }

    private suspend fun downloadAndSaveImage(comicImage: ComicImage, parentDirectory: Path): File {
        return withContext(Dispatchers.IO) {
            val fileName =
                "${comicImage.pageUrl.lastPathSegment} [${comicImage.title}] ${comicImage.imgUrl.lastPathSegment}"
            val file = parentDirectory.resolve(fileName).toFile()
            comicImage.imgUrl.url
                .openStream().use { input ->
                    file.outputStream().use { output ->
                        input.copyTo(output)
                    }
                }
            file
        }
    }
}