package seriesmover

import org.junit.Test
import org.junit.Assert._
import java.io.File
import seriesscanner.SerieFile
import java.net.URL

class TestApp {

  @Test
  def testisInEpisodes(){
    val file = new File(getClass.getResource("/Tru.Calling.S02E01.Perfect.Storm.WS.AC3.DVDRip.XviD-MEDiEVAL.avi").getFile())
    val candidate = new SerieFile(file)
    val episodes = Array(new SerieFile(file))
    assertTrue(App.isInTarget(candidate, episodes))
    assertFalse(App.isInTarget(candidate, Array()))
  }
}