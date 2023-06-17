package com.pittacode.webcomic.crawler.core.multiplepage

import com.pittacode.webcomic.crawler.core.model.ComicImage
import kotlinx.coroutines.*
import mu.KotlinLogging
import java.io.File
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import kotlin.io.path.exists

interface ComicImageDownloader {
    fun downloadAndSave(comicImages: List<ComicImage>)
}


class DefaultComicImageDownloader(storageDirectory: String) : ComicImageDownloader {

    private val storageDirectory = create(storageDirectory)

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun downloadAndSave(comicImages: List<ComicImage>) {
        CoroutineScope(Dispatchers.IO).launch {
            val images = mutableListOf<Deferred<File>>()
            for (comicImage in comicImages) {
                images.add(async(Dispatchers.IO) { downloadAndSaveImage(comicImage, storageDirectory) })
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

        private fun create(directory: String): Path {
            val parentDirectory = Paths.get(directory)
            return parentDirectory
                .takeIf { it.exists() } // use path it if it exists
                ?: Files.createDirectory(parentDirectory) // otherwise create it and then use it
        }
    }
}