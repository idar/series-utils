package seriesscanner

import org.scalatest.junit.JUnitSuite
import org.junit.Test
import java.net.URL
import java.io.File
import org.junit.Assert._

class SubdirScannerTest extends JUnitSuite {

  var testObj: SubDirScanner = _

  def serieDir = {
    val clazz: Class[_] = getClass
    val url: URL = clazz.getResource("/serier")
    val filename: String = url.getFile()
    new File(filename)
  }


  @Test def testSubDirScanning() {
    testObj = new SubDirScanner(serieDir)
    assertEquals(10, testObj.results.filter(serie => serie.isValid).length)
    assertEquals(0, testObj.results.filter(serie => serie.isDir).length)
  }
}