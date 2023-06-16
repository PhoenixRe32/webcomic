package com.pittacode.webcomic.crawler.k6bd

import com.pittacode.webcomic.crawler.model.PageUrl
import com.pittacode.webcomic.crawler.multiplepage.DefaultComicImageDownloader
import com.pittacode.webcomic.crawler.multiplepage.FindAllPagesDefault
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import kotlin.io.path.exists

fun main() {
    val parentDirectory = create("K6BD")
    val findAllPages = FindAllPagesDefault(
        findComicImages = FindK6bdComicImages,
        findNextPage = FindNextK6bdPage,
        comicImageDownloader = DefaultComicImageDownloader(parentDirectory)
    )
    val allPages =
        findAllPages.startingFrom(PageUrl("https://killsixbilliondemons.com/comic/breaker-of-infinites-2-65-to-2-67"))

//    println("Will be downloading:")
//    allPages.map { it.imgUrl }.forEach(::println)
//    downloadAndSaveImages(allPages, parentDirectory)
}

private fun create(directory: String): Path {
    val parentDirectory = Paths.get(directory)
    return parentDirectory
        .takeIf { it.exists() } // use path it if it exists
        ?: Files.createDirectory(parentDirectory) // otherwise create it and then use it
}