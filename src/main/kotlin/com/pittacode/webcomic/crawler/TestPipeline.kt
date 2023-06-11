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
    FindAuroraComicImages.on(PageUrl("https://comicaurora.com/aurora/1-6-26/"))
}