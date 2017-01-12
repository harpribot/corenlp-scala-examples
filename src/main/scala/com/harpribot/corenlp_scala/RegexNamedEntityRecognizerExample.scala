package com.harpribot.corenlp_scala

/**
  * Created by harshal on 1/12/17.
  */
import java.io.File
import java.nio.charset.Charset
import java.util.Properties

import com.google.common.io.Files
import edu.stanford.nlp.ling.CoreAnnotations.{NamedEntityTagAnnotation, LemmaAnnotation, PartOfSpeechAnnotation, SentencesAnnotation, TextAnnotation, TokensAnnotation}
import edu.stanford.nlp.ling.CoreLabel
import edu.stanford.nlp.pipeline.{Annotation, StanfordCoreNLP}
import edu.stanford.nlp.util.CoreMap

import scala.collection.JavaConverters._

/**
  * Created by harshal on 1/11/17.
  */
object RegexNamedEntityRecognizerExample {

  def main(args: Array[String]): Unit = {
    val props: Properties = new Properties()
    props.put("annotators", "tokenize, ssplit, pos, lemma, ner, regexner")
    props.put("regexner.mapping", "src/main/resources/jg-regexner.txt")

    val pipeline: StanfordCoreNLP = new StanfordCoreNLP(props)

    // read some text from a file - Uncomment this and comment the val text = "Quick...." below to load from a file
    //val inputFile: File = new File("src/test/resources/sample-content.txt")
    //val text: String = Files.toString(inputFile, Charset.forName("UTF-8"))
    val text = "Quick brown fox jumps over the lazy dog because he stole 20 dollars. This is Harshal. My home country is India. Today is 12th January 2017. This is 100% right. I have completed my Bachelor of Technology"

    // create blank annotator
    val document: Annotation = new Annotation(text)

    // run all Annotator - Tokenizer on this text
    pipeline.annotate(document)

    val sentences: List[CoreMap] = document.get(classOf[SentencesAnnotation]).asScala.toList

    (for {
      sentence: CoreMap <- sentences
      token: CoreLabel <- sentence.get(classOf[TokensAnnotation]).asScala.toList
      word: String = token.get(classOf[TextAnnotation])
      pos: String = token.get(classOf[PartOfSpeechAnnotation])
      lemma: String = token.get(classOf[LemmaAnnotation])
      regexner: String = token.get(classOf[NamedEntityTagAnnotation])


    } yield (token, word, pos, lemma, regexner)) foreach(t => println("token: " + t._1 + " word: " +  t._2 + " pos: " +  t._3 +  " lemma: " + t._4 + " ner (with regex):" + t._5))


  }

}


