package seriesmover

import org.junit.{Test, Before}
import org.junit.Assert._
import java.io.File
import java.net.{URL}
import java.lang.{Class, String}
import org.scalatest.junit.{JUnitSuite, AssertionsForJUnit}

class SerieDirTest extends JUnitSuite {

  def truCallingSerieDir = {
    val clazz: Class[_] = getClass
    val url: URL = clazz.getResource("/serier/Tru.Calling.S02.DVDRip.XviD")
    val filename: String = url.getFile()
    new SerieDir(new File(filename))
  }

  def serieDir = {
      val clazz: Class[_] = getClass
      val url: URL = clazz.getResource("/serier")
      val filename: String = url.getFile()
      new SerieDir(new File(filename))
    }


  private var testObj : SerieDir = _

  @Before def initialize() {
    testObj = truCallingSerieDir
  }

  @Test def verifyEasy() {
    assertTrue(true)
  }

  @Test def isDir(){
     assertTrue(testObj isDir)
  }

  @Test def isSeasonPack(){
    assertTrue(testObj isSeasonPack)
    assertTrue(testObj.season == 2)
  }


}