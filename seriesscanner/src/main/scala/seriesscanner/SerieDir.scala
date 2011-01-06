package seriesscanner

import java.io.File

class SerieDir(val dir: File) {

  def isDir = dir isDirectory

  def isSeasonPack = true

  def season = 2

  def episodes: Array[SerieFile] = dir.listFiles.map(new SerieFile(_)).filter(serie => serie.isValid)

}

