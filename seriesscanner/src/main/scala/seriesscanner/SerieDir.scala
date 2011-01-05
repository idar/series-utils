package seriesmover

import java.io.File

class SerieDir(val dir: File) {

  def isDir = dir isDirectory

  def isSeasonPack = true

  def season = 2
}