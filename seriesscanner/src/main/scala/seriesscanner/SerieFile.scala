package seriesscanner

import java.io.File
import util.matching.Regex
import java.lang.String

class SerieFile(val file: File) {

  def isDir = file isDirectory

  def isValid = (!isDir && fileNameInfo.isValid && fileNameInfo.isVideoFile)

  private val fileNameInfo = new FileNameInfoExtractor(file.getName())

  val season = fileNameInfo.season

  val episode = fileNameInfo.episode

  val name = fileNameInfo.name

  override def toString = fileNameInfo.toString


  override def equals(o: Any): Boolean = o match {
    case x: SerieFile => equals(x)
    case _ => false
  }

  def equals(o: SerieFile): Boolean = {
    if (o.episode == episode && o.season == season && o.name == name) return true;
    false
  }

  override def hashCode = {
    name.hashCode + episode.hashCode + season.hashCode
  }
}

class FileNameInfoExtractor(val filename: String) {

  def isValid = regexpresult.isDefined && episoderes != null && seasonres != null && seriesnameres != null

  private
  val regexpresult = Regexp.serieRegexp.findFirstMatchIn(filename)

  def result = regexpresult.get

  def episoderes: String = result.group("episode")

  def seasonres: String = result.group("season")

  def seriesnameres = result.group("seriesname")

  def episode = {
    if (isValid)
      episoderes.toInt
    else 0
  }

  def season = {
    if (isValid)
      seasonres.toInt
    else 0
  }

  def name = {
    if (isValid)
      seriesnameres.replace(".", " ")
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

