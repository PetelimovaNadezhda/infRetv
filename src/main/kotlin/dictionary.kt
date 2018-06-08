import net.sf.extjwnl.data.POS
import net.sf.extjwnl.dictionary.Dictionary

fun getSynonyms(word: String): List<String> {
  val dictionary = Dictionary.getDefaultResourceInstance()
  return POS.getAllPOS()
      .mapNotNull { dictionary.getIndexWord(it, word) }
      .flatMap { it.senses }
      .flatMap { it.words }
      .map { it.lemma }.distinct()
}