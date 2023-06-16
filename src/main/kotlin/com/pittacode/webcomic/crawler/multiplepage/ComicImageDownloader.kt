package com.pittacode.webcomic.crawler.multiplepage

import com.pittacode.webcomic.crawler.model.ComicImage
import kotlinx.coroutines.*
import mu.KotlinLogging
import java.io.File
import java.nio.file.Path

interface ComicImageDownloader {
    fun downloadAndSave(comicImages: List<ComicImage>)
}


class DefaultComicImageDownloader(private val parentDirectory: Path) : ComicImageDownloader {

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun downloadAndSave(comicImages: List<ComicImage>) {
        CoroutineScope(Dispatchers.IO).launch {
            val images = mutableListOf<Deferred<File>>()
            for (comicImage in comicImages) {
                images.add(async(Dispatchers.IO) { downloadAndSaveImage(comicImage, parentDirectory) })
            }
            images.awaitAll()
            logger.info {
                "Status\n\t" + images
                    .filter { it.isCompleted }
                    .map { it.getCompleted() }
                    .map { "${it.absolutePath} (${it.length()})" }
                    .joinToString("\n\t")
            }
        }
    }

    private suspend fun downloadAndSaveImage(comicImage: ComicImage, parentDirectory: Path): File {
        return withContext(Dispatchers.IO) {
            val pageNumber = comicImage.pageNumber()
            val imageName = comicImage.imageName()
            val extension = comicImage.extension()
            val title = comicImage.title()
            val fileName = "$pageNumber $imageName [$title].$extension"
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

    companion object {
        private val logger = KotlinLogging.logger {}
    }
}