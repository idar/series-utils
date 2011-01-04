package seriesmover

import org.scalatest.junit.AssertionsForJUnit
import org.junit.{Test, Before}
import org.junit.Assert._
import java.io.File
import java.net.{URL}
import java.lang.{Class, String}

class SerieDirTest extends AssertionsForJUnit {

  def truCallingSerieDir = {
    val clazz: Class[_] = getClass
    val url: URL = clazz.getResource("/serier/Tru.Calling.S02.DVDRip.XviD")
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

}