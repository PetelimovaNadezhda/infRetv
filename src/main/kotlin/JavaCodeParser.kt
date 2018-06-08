import com.github.javaparser.JavaParser
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration
import com.github.javaparser.ast.body.*
import java.io.File
import java.nio.file.Path

data class JavaClass(val name: String,
                     val packageName: String,
                     val methods: List<String>,
                     val fields: List<String>)

fun parseClass(code: String): List<JavaClass> {
  val compUnit = JavaParser.parse(code)
  return compUnit
      .findAll(ClassOrInterfaceDeclaration::class.java)
      .map { c ->
        val methods = c.findAll(MethodDeclaration::class.java)
            .map { it.nameAsString }
        val fields = c.findAll(VariableDeclarator::class.java)
            .map { it.nameAsString }
        JavaClass(c.nameAsString, compUnit.packageDeclaration.get().nameAsString, methods, fields)
      }
}

fun parseClass(file: File) =
    parseClass(file.readText(Charsets.UTF_8))

fun parseDir(dir: File) =
    dir.walkTopDown()
        .toList()
        .filter { it.isFile }
        .flatMap{parseClass(it)}
