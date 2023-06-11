package com.pittacode.webcomic.crawler

import com.pittacode.webcomic.crawler.model.PageUrl
import com.pittacode.webcomic.crawler.multiplepage.DefaultComicImageDownloader
import com.pittacode.webcomic.crawler.multiplepage.FindAllPagesDefault
import com.pittacode.webcomic.crawler.multiplepage.FindAuroraComicImages
import com.pittacode.webcomic.crawler.multiplepage.FindNextAuroraPage
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import kotlin.io.path.exists

fun main() {
    val parentDirectory = create("Aurora")
    val findAllPages = FindAllPagesDefault(
        findComicImages = FindAuroraComicImages,
        findNextPage = FindNextAuroraPage,
        comicImageDownloader = DefaultComicImageDownloader(parentDirectory)
    )
    val allPages = findAllPages.startingFrom(PageUrl("https://comicaurora.com/aurora/1-6-26/"))

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