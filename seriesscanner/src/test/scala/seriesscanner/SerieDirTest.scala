package seriesmover

import org.junit.{Test, Before}
import org.junit.Assert._
import java.io.File
import org.scalatest.junit.JUnitSuite
import seriesscanner.SerieDir

class SerieDirTest extends JUnitSuite {

  def truCallingSerieDir = new SerieDir(new File(getClass.getResource("/serier/Tru.Calling.S02.DVDRip.XviD/").getFile()))

  def serieDir = new SerieDir(new File(getClass.getResource("/serier").getFile()))


  private var testObj: SerieDir = _

  @Before def initialize() {
    testObj = truCallingSerieDir
  }

  @Test def verifyEasy() {
    assertTrue(true)
  }

  @Test def isDir() {
    assertTrue(testObj isDir)
  }

  @Test def isSeasonPack() {
    assertTrue(testObj isSeasonPack)
    assertTrue(testObj.season == 2)
  }

  @Test def testEpisodes() {
    assertEquals(6, testObj.episodes.length)
  }


}