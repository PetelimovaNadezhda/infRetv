fun tokenizeQuery(str: String): List<String> {
  return str.split(" ").map { it.trim() }
}