package seriesscanner

import java.io.File
import util.matching.Regex
import java.lang.String
import util.matching.Regex.Match

class SerieFile(val file: File) extends Ordered[SerieFile] {

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
    if (o.episode == episode && o.season == season && o.name.equalsIgnoreCase(name)) return true;
    false
  }

  override def hashCode = {
    name.hashCode + episode.hashCode + season.hashCode
  }

  def compare(that: SerieFile): Int = {
    if (!name.equalsIgnoreCase(that.name)) name.compareToIgnoreCase(that.name)
    else if (season != that.season) season.compare(that.season)
    else if (episode != that.episode) episode.compare(that.episode)
    else 0
  }

}

class FileNameInfoExtractor(val filename: String) {

  def isValid = option.isDefined && episoderes != null && seasonres != null && seriesnameres != null

  private val option = Regexp.findSerieREResult(filename)

  private def result = option.get

  private def episoderes: String = result.group("episode")

  private def seasonres: String = result.group("season")

  private def seriesnameres = result.group("seriesname")

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
  private val name: String = "seriesname"
  private val season: String = "season"
  private val episode: String = "episode"
  private val REs = Array(new Regex("""^((.+?)[ \._\-])?\[?[Ss]([0-9]+)[\.\- ]?[Ee]?([0-9]+)\]?[^\\/]*$""", "tull", name, season, episode),
    new Regex("""^(.+)[ \._\-]([0-9]{1})([0-9]{2})[\._ -][^\\/]*$""", name, season, episode),
    new Regex("""^(.+)[ \._\-]([0-9]{2})([0-9]{2,3})[\._ -][^\\/]*$""", name, season, episode)
  )

  def findSerieREResult(filename: String) = {
    val possiblematches = REs.map(re => re.findFirstMatchIn(filename)).filter(option => isOK(option)).map(option => option.get)
    if (possiblematches.length != 1) None
    else Some(possiblematches(0))
  }

  def isOK(rematch: Option[Match]): Boolean = {
    if (!rematch.isDefined) return false
    for (groupname <- rematch.get.groupNames) {
      if (rematch.get.group(groupname) == null) return false
    }
    true
  }
}
