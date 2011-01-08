package seriesscanner

import java.io.File
import collection.mutable.{Buffer, ListBuffer}

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
      case _ => chooseCandidate(target, candidates)
    }

  }

  def chooseCandidate(target: SerieFile, candidates: List[SerieFile]): Unit = {
    println("\nMultiplecandidates for " + target)
    println(target.file.getAbsolutePath)
    println("\nCandidates:")
    for (i <- 0 until candidates.length) {
      println("(" + i + ") " + candidates(i).file.getAbsolutePath)
    }
    println("Please choose a number or No(n)")
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
    println("Delete all(All), or select number or No(no)")
    val input = readLine
    val inputnumber = toIntOr(input, -1)
    if ("all".equalsIgnoreCase(input)) deleteAll(remainingCandidates)
    else if (remainingCandidates.indices.contains(inputnumber)) deleteOne(remainingCandidates, inputnumber)
    else println("Continuing without deleting")
  }

  def deleteOne(remainingCandidates: Buffer[SerieFile], num: Int): Unit = {
    printf("Realy delete file " + remainingCandidates(num).file.getAbsolutePath + " ?")
    printf("Yes or No")
    val input = readLine
    if ("yes".equalsIgnoreCase(input)) {
      if (remainingCandidates(num).file.delete) {
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
    printf("Realy delete all files? Yes or No")
    val input = readLine
    if ("yes".equalsIgnoreCase(input)) {
      remainingCandidates.foreach(serie => serie.file.delete)
    }
  }

  def toIntOr(input: String, othervalue: Int): Int = {
    try {
      input.toInt
    } catch {
      case e: Exception => othervalue
    }

  }

  def move(target: SerieFile, candidate: SerieFile): Unit = {
    println("move? " + candidate.file.getAbsolutePath + " to " + target.file.getAbsolutePath)
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
