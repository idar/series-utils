package seriesscanner

import org.junit.{Before, Test}
import org.scalatest.junit.JUnitSuite
import java.io.File
import org.junit.Assert._

class SerieFileTest extends JUnitSuite {

  def truCallingSerieFileEp1 = new SerieFile(new File(getClass.getResource("/serier/Tru.Calling.S02.DVDRip.XviD/Tru.Calling.S02E01.Perfect.Storm.WS.AC3.DVDRip.XviD-MEDiEVAL.avi").getFile()))

  def truCallingSerieFileEp2 = new SerieFile(new File(getClass.getResource("/serier/Tru.Calling.S02.DVDRip.XviD/Tru.Calling.S02E02.Grace.WS.DVDRip.XviD-TVEP.avi").getFile()))
   def truCallingSerieFileEp1S01 = new SerieFile(new File(getClass.getResource("/serier/Tru.Calling.S01.WS.DVDRip.Xvid-RiVER.REPACK/Tru.Calling.S01E01.WS.DVDRip.Xvid-RiVER.REPACK.avi").getFile()))


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

  @Test def testQuality(){
    assertTrue(truCallingSerieFileEp1.equals(truCallingSerieFileEp1))
    assertFalse(truCallingSerieFileEp1.equals(truCallingSerieFileEp2));
    assertFalse(truCallingSerieFileEp1.equals(truCallingSerieFileEp1S01))
    assertEquals(truCallingSerieFileEp1.hashCode, truCallingSerieFileEp1.hashCode)
    assertFalse(truCallingSerieFileEp1.hashCode == truCallingSerieFileEp2.hashCode)
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