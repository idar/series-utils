package seriesscanner

import java.io.File
import collection.mutable.ArrayOps

class SubDirScanner(dir: File) {


  private def files(file: File): List[File] = {
    var result: List[File] = List()
    val filelist: ArrayOps[File] = file.listFiles
    result ++= filelist
    for (file <- filelist; if file.isDirectory) {
      result ++= files(file)
    }
    return result
  }

  def results: List[SerieFile] = files(dir).map(new SerieFile(_)).filter(serie => serie.isValid).toList


}