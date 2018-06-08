import java.io.File

data class Index(val index: Map<String, List<String>>)

fun tokenizeMetodName(text: String) =
    Regex("[A-Z][a-z]*")
        .findAll(text.capitalize())
        .map { it.value.toLowerCase() }
        .toList()


fun buildIndex(classes: List<JavaClass>) =
    Index(
        classes.flatMap { c ->
          (listOf(c.name) + c.methods + c.fields)
              .flatMap { tokenizeMetodName(it) }
              .map { it to (c.packageName + "." + c.name) }
        }
            .groupBy { it.first }
            .mapValues { it.value.map { it.second }.distinct() }
    )

fun buildIndexForDir(dir: File) =
    buildIndex(parseDir(dir))

fun search(index: Index, query: String) =
    tokenizeQuery(query)
        .flatMap { w -> getSynonyms(w).map { s -> w to s } }
        .mapNotNull { (w, s) ->
          val x = index.index[s]
          if (x == null) null
          else (w to s) to x
        }.toMap()
