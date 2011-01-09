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

  @Test def testQuality() {
    assertTrue(truCallingSerieFileEp1.equals(truCallingSerieFileEp1))
    assertFalse(truCallingSerieFileEp1.equals(truCallingSerieFileEp2));
    assertFalse(truCallingSerieFileEp1.equals(truCallingSerieFileEp1S01))
    assertEquals(truCallingSerieFileEp1.hashCode, truCallingSerieFileEp1.hashCode)
    assertFalse(truCallingSerieFileEp1.hashCode == truCallingSerieFileEp2.hashCode)
  }

  @Test def testSort() {
    val test = Array(truCallingSerieFileEp1, truCallingSerieFileEp2, truCallingSerieFileEp1S01).sorted
    assertEquals(truCallingSerieFileEp1S01, test(0))
    assertEquals(truCallingSerieFileEp1, test(1))
    assertEquals(truCallingSerieFileEp2, test(2))
  }

  @Test def testAlmostIdenticalName() {
    val testfile1 = new SerieFile(new File(getClass.getResource("/testfiles/defying.gravity.us.s01e04.hdtv.xvid-2hd.avi").getFile()))
    val testfile2 = new SerieFile(new File(getClass.getResource("/testfiles/defying.gravity.s01e04.hdtv.xvid-2hd.avi").getFile()))
    assertTrue(testfile1 == testfile2)
  }
}

class FileNameInfoExtractorTest extends JUnitSuite {

  private var testObj: FileNameInfoExtractor = _

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

  @Test def testS03E01divx() {
    testObj = new FileNameInfoExtractor("S03E01.divx")
    assertFalse("should be unvalid", testObj.isValid)
  }

  @Test def testSfvfile {
    testObj = new FileNameInfoExtractor("one.tree.hill.s07e08.hdtv.xvid-xii.sfv")
    assertFalse(testObj.isVideoFile)
  }

  @Test def testFileNamesWithCorrectResult {
    val test = testFilenameInfoExtractor(_)
    test(Tuple4("scrubs.s01e01.avi", "scrubs", 1, 1))
    test(Tuple4("dexter.0401.hdtv.xvid-notv.avi", "dexter", 4, 1))
    test(Tuple4("leverage.303.hdtv.xvid-sys.avi", "leverage", 3, 3))
    test(Tuple4("Tru.Calling.S02E02.Grace.WS.DVDRip.XviD-TVEP.avi", "Tru Calling", 2, 2))
    test(Tuple4("Tru.Calling.S02E01.Perfect.Storm.WS.AC3.DVDRip.XviD-MEDiEVAL.avi", "Tru Calling", 2, 1))
    test(Tuple4("QI.S04E01.2006-09-29.blah.avi", "QI", 4, 1))
    test(Tuple4("The Wire s05e10 30.mp4", "The Wire", 5, 10))
    test(Tuple4("Arrested Development - S2 E 02 - Dummy Ep Name.blah", "Arrested Development", 2, 2))
    test(Tuple4("Scrubs 1x01-720p.avi", "Scrubs", 1, 1))
  }

  def testFilenameInfoExtractor(testParameters: Tuple4[String, String, Int, Int]): Unit = {
    testObj = new FileNameInfoExtractor(testParameters._1);
    assertTrue("Not valid with " + testParameters, testObj.isValid)
    assertEquals(testParameters._2, testObj.name)
    assertEquals(testParameters._3, testObj.season)
    assertEquals(testParameters._4, testObj.episode)
  }

}