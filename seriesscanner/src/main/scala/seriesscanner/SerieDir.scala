package seriesmover

import java.io.File

class SerieDir (val dir : File) {

  def isDir = dir isDirectory
}