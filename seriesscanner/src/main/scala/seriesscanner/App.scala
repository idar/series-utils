package seriesscanner

object App {

  def foo(x: Array[String]) = x.foldLeft("")((a, b) => a + " " + b)

  def main(args: Array[String]) {
    println("Hello World!")
    println("concat arguments = " + foo(args))

    val episodes = SerieScanner.findEpisodesInDir(args(0))
    episodes.foreach(file => println(file))
  }

}
