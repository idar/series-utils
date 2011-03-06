package seriesmover

import java.io.File
import collection.mutable.Buffer
import seriesscanner.{SerieFile, SerieScanner}

object App {

  def foo(x: Array[String]) = x.foldLeft("")((a, b) => a + " " + b)

  def main(args: Array[String]) {
    checkArgs(args)

    val episodes = SerieScanner.findEpisodesInDir(args(args.length-1)).sorted
    episodes.foreach(file => println(file))
    val candidates_ = SerieScanner.findEpisodesInSubdirs(args(0))
    val candidates = candidates_.filterNot(candidate => isInTarget(candidate,episodes));
    println("\n Listing candidates for each episode: ")
    episodes.foreach(serie => move(serie, candidates.filter(candidate => candidate == serie)))
  }

  def isInTarget(candidate: SerieFile, episodes:Array[SerieFile]) : Boolean = {
    episodes.foreach(sf => if(sf.file.equals(candidate.file)) return true)
    return false;
  }

  def checkArgs(args: Array[String]) {
    if (args.length < 2) {
      println("Need source and target dir");
      println("fillseasonpack <sources> <target>")
      exit(1)
    }
    for (arg <- args) {
      if (!new File(arg).isDirectory) {
        println(arg + " is not a dir.");
        exit(2)
      }
    }
  }

  def move(target: SerieFile, candidates: List[SerieFile]): Unit = {
    candidates.length match {
      case 0 => println("No candidates for " + target + ".\nSkipping file")
      case 1 => move(target, candidates(0))
      case _ => chooseCandidate(target, candidates)
    }

  }

  def chooseCandidate(target: SerieFile, candidates: List[SerieFile]): Unit = {
    println("\nMultiplecandidates for \"" + target + "\"" + " size: " + target.file.length)
    println(target.file.getAbsolutePath)
    println("\nCandidates:")
    for (i <- 0 until candidates.length) {
      println("(" + i + ") " + candidates(i).file.getAbsolutePath + " size: " + candidates(i).file.length)
    }
    println("Please choose a number or No(no)")
    val input = readLine
    val inputnumber = toIntOr(input, -1)
    if (candidates.indices.contains(inputnumber)) {
      move(target, candidates(inputnumber))
      val remaining = candidates.toBuffer
      remaining.remove(inputnumber)
      handleRemaining(remaining)
    }
    else println("\nSkipping file...")
  }

  def handleRemaining(remainingCandidates: Buffer[SerieFile]): Unit = {
    println("Remaining " + remainingCandidates.length + " candidates")
    for (i <- 0 until remainingCandidates.length) {
      println("(" + i + ") " + remainingCandidates(i).file.getAbsolutePath)
    }
    println("Delete all(all), or select number or No(no)")
    val input = readLine
    val inputnumber = toIntOr(input, -1)
    if ("all".equalsIgnoreCase(input)) deleteAll(remainingCandidates)
    else if (remainingCandidates.indices.contains(inputnumber)) deleteOne(remainingCandidates, inputnumber)
    else println("Continuing without deleting")
  }

  def deleteOne(remainingCandidates: Buffer[SerieFile], num: Int): Unit = {
    println("Realy delete file\n" + remainingCandidates(num).file.getAbsolutePath + " ?")
    println("Yes or No")
    val input = readLine
    if ("yes".equalsIgnoreCase(input) || "y".equalsIgnoreCase(input)) {
      if (deleteFile(remainingCandidates(num).file)) {
        remainingCandidates.remove(num)
        if (remainingCandidates.length > 0)
          handleRemaining(remainingCandidates)
      }
    }
  }

  def deleteAll(remainingCandidates: Buffer[SerieFile]): Unit = {
    printf("Remaining ")
    for (i <- 0 until remainingCandidates.length) {
      println(remainingCandidates(i).file.getAbsolutePath)
    }
    printf("Realy delete all files? Yes or No\n")
    val input = readLine
    if ("yes".equalsIgnoreCase(input) || "y".equalsIgnoreCase(input)) {
      remainingCandidates.foreach(serie => deleteFile(serie.file))
    } else println("Not deleting files. Continuing...")
  }

  def deleteFile(file: File): Boolean = {
    print("Deleting " + file.getAbsoluteFile)
    val status = file.delete
    if (status) println(" Success")
    else println(" failure")
    return status
  }

  def toIntOr(input: String, othervalue: Int): Int = {
    try {
      input.toInt
    } catch {
      case e: Exception => othervalue
    }

  }

  def move(target: SerieFile, candidate: SerieFile): Unit = {
    println("\n\nEpisode \"" + target + "\"")
    println("\n move ? ")
    println(candidate.file.getAbsolutePath + " size: " + candidate.file.length)
    println("to")
    println(target.file.getAbsolutePath + " size: " + target.file.length)
    println("Yes(y) or No(n)")
    val response = readLine
    if ("y".equalsIgnoreCase(response)) moveFile(candidate.file, target.file)
    else println("skipping...")
  }


  def moveFile(from: File, to: File) = {
    try {
      from.renameTo(to)
    }
    catch {
      case e: Exception => println("Problem moving file from " + from + " to " + to); println(e.getMessage)
    }
  }
}