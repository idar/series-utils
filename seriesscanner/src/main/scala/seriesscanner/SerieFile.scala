package seriesscanner

import java.io.File
import util.matching.Regex
import java.lang.String

class SerieFile(val file: File) {

  def isDir = file isDirectory

  def isValid = (!isDir && fileNameInfo.isValid && fileNameInfo.isVideoFile)

  private val fileNameInfo = new FileNameInfoExtractor(file.getName())

  def season = 2

  val episode = fileNameInfo.episode

  override def toString = fileNameInfo.toString
}

class FileNameInfoExtractor(val filename: String) {

  def isValid = regexpresult.isDefined

  private val regexpresult = Regexp.serieRegexp.findFirstMatchIn(filename)

  def result = regexpresult.get

  def episode = {
    if (isValid)
      result.group("episode").toInt
    else 0
  }

  def season = {
    if (isValid)
      result.group("season").toInt
    else 0
  }

  def name = {
    if (isValid)
      result.group("seriesname").replace(".", " ")
    else ""
  }

  def isVideoFile: Boolean = filename match {
    case Regexp.videoRegexp() => return true
    case _ => return false
  }

  override def toString = "" + name + " Season " + season + " Episode " + episode
}

object Regexp {
  val videoRegexp = """^.*[?i\.avi|?i\.mkv]$""".r

  val serieRegexp = new Regex("""^((.+?)[ \._\-])?\[?[Ss]([0-9]+)[\.\- ]?[Ee]?([0-9]+)\]?[^\\/]*$""", "tull", "seriesname", "season", "episode")
}