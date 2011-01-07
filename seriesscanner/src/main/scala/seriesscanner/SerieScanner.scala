package seriesscanner

import java.io.File


object SerieScanner {

  def findEpisodesInDir(dir: File): Array[SerieFile] = new SerieDir(dir).episodes

  def findEpisodesInDir(dir: String): Array[SerieFile] = findEpisodesInDir(new File(dir))

  def findEpisodesInSubdirs(dir: String): List[SerieFile] = findEpisodesInSubdirs(new File(dir))

  def findEpisodesInSubdirs(dir: File): List[SerieFile] = new SubDirScanner(dir).results

}