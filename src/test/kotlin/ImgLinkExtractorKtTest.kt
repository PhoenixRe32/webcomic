import com.pittacode.webcomic.htmlblock.Filename
import com.pittacode.webcomic.htmlblock.ImgDlLink
import com.pittacode.webcomic.htmlblock.Link
import com.pittacode.webcomic.htmlblock.extractImgLinksInOrder
import io.kotest.assertions.assertSoftly
import io.kotest.matchers.shouldBe
import kotlin.test.Test

class ImgLinkExtractorKtTest {

    // given
    val niceBlock = """
<div class="item"> <a href="https://www.kevinbolk.com/wp-content/uploads/2021/07/luigi-anothercastle.jpg" data-fancybox="gallery"><img     src="https://www.kevinbolk.com/wp-content/uploads/2021/07/luigi-anothercastle.jpg" class="img-fluid" /></a></div>
<div class="item"> <a href="https://www.kevinbolk.com/wp-content/uploads/2021/07/luigi-kart.jpg" data-fancybox="gallery"><img
src="https://www.kevinbolk.com/wp-content/uploads/2021/07/luigi-kart.jpg" class="img-fluid" /></a></div>
<div class="item"> <a href="https://www.kevinbolk.com/wp-content/uploads/2021/07/luigi-freddie.jpg" data-fancybox="gallery"><img src="https://www.kevinbolk.com/wp-content/uploads/2021/07/luigi-freddie.jpg" class="img-fluid" /></a></div><div class="item"> <a href="https://www.kevinbolk.com/wp-content/uploads/2021/07/luigi-wario.jpg" data-fancybox="gallery"><img src="https://www.kevinbolk.com/wp-content/uploads/2021/07/luigi-wario.jpg" class="img-fluid" /></a></div>
""".trimIndent()

    @Test
    fun `given block of html source code extract image links`() {
        // when
        val result = extractImgLinksInOrder(niceBlock).map(ImgDlLink::link)

        // then
        assertSoftly {
            result[0] shouldBe Link("https://www.kevinbolk.com/wp-content/uploads/2021/07/luigi-anothercastle.jpg")
            result[1] shouldBe Link("https://www.kevinbolk.com/wp-content/uploads/2021/07/luigi-kart.jpg")
            result[2] shouldBe Link("https://www.kevinbolk.com/wp-content/uploads/2021/07/luigi-freddie.jpg")
            result[3] shouldBe Link("https://www.kevinbolk.com/wp-content/uploads/2021/07/luigi-wario.jpg")
        }
    }

    @Test
    fun `given block of html source code generate image downloaded filenames`() {
        // when
        val result = extractImgLinksInOrder(niceBlock).map(ImgDlLink::fileName)

        // then
        assertSoftly {
            result[0] shouldBe Filename("000-luigi-anothercastle.jpg")
            result[1] shouldBe Filename("001-luigi-kart.jpg")
            result[2] shouldBe Filename("002-luigi-freddie.jpg")
            result[3] shouldBe Filename("003-luigi-wario.jpg")
        }
    }
}