package seriesscanner

import org.junit.{Before, Test}
import java.net.URL
import org.scalatest.junit.JUnitSuite
import java.io.File
import org.junit.Assert._

class SerieFileTest extends JUnitSuite {

  def truCallingSerieFileEp1 = {
    val clazz: Class[_] = getClass
    val url: URL = clazz.getResource("/serier/Tru.Calling.S02.DVDRip.XviD/Tru.Calling.S02E01.Perfect.Storm.WS.AC3.DVDRip.XviD-MEDiEVAL.avi")
    val filename: String = url.getFile()
    new SerieFile(new File(filename))
  }

  def truCallingSerieFileEp2 = {
    val clazz: Class[_] = getClass
    val url: URL = clazz.getResource("/serier/Tru.Calling.S02.DVDRip.XviD/Tru.Calling.S02E02.Grace.WS.DVDRip.XviD-TVEP.avi")
    val filename: String = url.getFile()
    new SerieFile(new File(filename))
  }


  private var testObj: SerieFile = _

  @Before def initialize() {
    testObj = truCallingSerieFileEp1
  }

  @Test def verifyEasy() {
    assertTrue(true)
  }

  @Test def isDir() {
    assertFalse(testObj isDir)
  }

  @Test def testEpisodeEp1() {
    testObj = truCallingSerieFileEp1
    assertEquals(1, testObj.episode)
  }

  @Test def testEpisodeEp2() {
    testObj = truCallingSerieFileEp2
    assertEquals(2, testObj.episode)
  }
}

class FileNameInfoExtractorTest extends JUnitSuite {

  private var testObj: FileNameInfoExtractor = _

  @Test def testEpisodeS02E01() {
    testObj = new FileNameInfoExtractor("Tru.Calling.S02E01.Perfect.Storm.WS.AC3.DVDRip.XviD-MEDiEVAL.avi")
    assertEquals(2, testObj.season)
    assertEquals(1, testObj.episode)
    assertEquals("Tru Calling", testObj.name)
  }

  @Test def testEpisodeS02E02() {
    testObj = new FileNameInfoExtractor("Tru.Calling.S02E02.Grace.WS.DVDRip.XviD-TVEP.avi")
    assertEquals(2, testObj.season)
    assertEquals(2, testObj.episode)
    assertEquals("Tru Calling", testObj.name)
  }

  @Test def testInvalidFilename() {
    testObj = new FileNameInfoExtractor("Tru.Calling.nfo")
    assertFalse(testObj.isValid)
  }

  @Test def testNfoFileNameWithEpSyntax() {
    testObj = new FileNameInfoExtractor("tru.calling.s01e01.ws.dvdrip.xvid-river.repack.nfo")
    assertFalse(testObj.isVideoFile)
    testObj = new FileNameInfoExtractor("tru.calling.s01e01.ws.dvdrip.xvid-river.repack.mkv")
    assertTrue(testObj.isVideoFile)
  }
}