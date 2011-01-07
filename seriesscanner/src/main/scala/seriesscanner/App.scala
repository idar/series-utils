package seriesscanner

object App {

  def foo(x: Array[String]) = x.foldLeft("")((a, b) => a + " " + b)

  def main(args: Array[String]) {
    println("Hello World!")
    println("concat arguments = " + foo(args))

    val episodes = SerieScanner.findEpisodesInDir(args(0))
    episodes.foreach(file => println(file))

    val candidates = SerieScanner.findEpisodesInSubdirs(args(1))
    println("found episodes:")
    candidates.foreach(file => println(file))

    println("\n Listing candidates for each episode: ")
    episodes.foreach(serie => move(serie, candidates.filter(candidate => candidate == serie)))
  }

  def move(target: SerieFile, candidates: List[SerieFile]): Unit = {
    candidates.length match {
      case 0 => println("No candidates for " + target + ".\nSkipping file")
      case 1 => move(target, candidates(0))
      case _ => println("Multiple candidates, need to be implemented")
    }

  }

  def move(target: SerieFile, candidate: SerieFile): Unit = {
    println("move? " + candidate.file.getAbsolutePath + " to " + target.file.getAbsolutePath)
    println("Yes(y) or No(n)")
    val response = readLine
    if ("y".equalsIgnoreCase(response)) println("moving")
    else println("skipping...")
  }

}
