package seriesscanner

import java.io.File

class SerieDir(val dir: File) {

  def isDir = dir isDirectory

  def isSeasonPack = true

  def season = 2

  def episodes: Array[SerieFile] = for (serie <- dir.listFiles.map(new SerieFile(_)); if serie.isValid) yield serie

}

object SerieScanner {

  def findEpisodesInDir(dir: File): Array[SerieFile] = new SerieDir(dir).episodes

  def findEpisodesInDir(dir: String): Array[SerieFile] = findEpisodesInDir(new File(dir))

}