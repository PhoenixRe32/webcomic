package com.pittacode.webcomic.crawler

import com.pittacode.webcomic.crawler.k6bd.FindK6bdComicImages
import com.pittacode.webcomic.crawler.k6bd.FindNextK6bdPage
import com.pittacode.webcomic.crawler.model.PageUrl
import com.pittacode.webcomic.crawler.multiplepage.DefaultComicImageDownloader
import com.pittacode.webcomic.crawler.multiplepage.FindAllPagesDefaultStopAt1
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import kotlin.io.path.exists

suspend fun main() {
//    FindAuroraComicImages.on(PageUrl("https://comicaurora.com/aurora/1-6-26/"))

    val findAllPages = FindAllPagesDefaultStopAt1(
        findComicImages = FindK6bdComicImages,
        findNextPage = FindNextK6bdPage,
        comicImageDownloader = DefaultComicImageDownloader(create("tmp"))
    )
    val allPages =
        findAllPages.startingFrom(PageUrl("https://killsixbilliondemons.com/comic/breaker-of-infinities-1-37-to-1-38"))

    runBlocking {
        delay(15000L)
    }
}

private fun create(directory: String): Path {
    val parentDirectory = Paths.get(directory)
    return parentDirectory
        .takeIf { it.exists() } // use path it if it exists
        ?: Files.createDirectory(parentDirectory) // otherwise create it and then use it
}