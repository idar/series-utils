package seriesscanner

import java.io.File
import util.matching.Regex
import java.lang.String

class SerieFile(val file: File) {

  def isDir = file isDirectory

  private val fileNameInfo = new FileNameInfoExtractor(file.getName())

  def season = 2

  val episode = fileNameInfo.episode
}

class FileNameInfoExtractor(val filename: String) {
  val serieRegexp = new Regex("""^((.+?)[ \._\-])?\[?[Ss]([0-9]+)[\.\- ]?[Ee]?([0-9]+)\]?[^\\/]*$""", "tull", "seriesname", "season", "episode")

  val result = serieRegexp.findFirstMatchIn(filename).get

  def episode = result.group("episode").toInt

  def season = result.group("season").toInt

  def name = result.group("seriesname").replace(".", " ")
}