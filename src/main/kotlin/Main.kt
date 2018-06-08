import java.io.File
import kotlin.system.exitProcess


fun main(args: Array<String>) {
  if (args.size != 1) {
    println("You should run program with argument <pathToAnalysingPackage>")
    exitProcess(1)
  }
  val dir = File(args[0])
  if (!dir.exists()) {
    println("Directory $dir does not exist")
    exitProcess(1)
  }
  println("Building an index for directory $dir")
  val index = buildIndexForDir(dir)
  while (true) {
    println("\nEnter a query to search")
    val query = readLine()?.toLowerCase() ?: continue
    val res = search(index, query)
    if (res.isEmpty()) {
      println("Nothing found")
    } else {
      println("Found packages satisfying '$query' query:")
      res.forEach {
        println("${it.key.first} -> ${it.key.second}: ")
        it.value.forEach { println("   $it") }
      }
    }
  }

}