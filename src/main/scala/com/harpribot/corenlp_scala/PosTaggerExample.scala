package com.harpribot.corenlp_scala

/**
  * Created by harshal on 1/12/17.
  */
import java.io.File
import java.nio.charset.Charset
import java.util.Properties

import com.google.common.io.Files
import edu.stanford.nlp.ling.CoreAnnotations.{PartOfSpeechAnnotation, SentencesAnnotation, TextAnnotation, TokensAnnotation}
import edu.stanford.nlp.ling.CoreLabel
import edu.stanford.nlp.pipeline.{Annotation, StanfordCoreNLP}
import edu.stanford.nlp.util.CoreMap

import scala.collection.JavaConverters._

/**
  * Created by harshal on 1/11/17.
  */
object PosTaggerExample {

  def main(args: Array[String]): Unit = {
    val props: Properties = new Properties()
    props.put("annotators", "tokenize, ssplit, pos")

    val pipeline: StanfordCoreNLP = new StanfordCoreNLP(props)

    // read some text from a file - Uncomment this and comment the val text = "Quick...." below to load from a file
    //val inputFile: File = new File("src/test/resources/sample-content.txt")
    //val text: String = Files.toString(inputFile, Charset.forName("UTF-8"))
    val text = "Quick brown fox jumps over the lazy dog. This is Harshal."

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

    } yield (token, word, pos)) foreach(t => println("token: " + t._1 + " word: " +  t._2 + " pos: " +  t._3))


  }

}

