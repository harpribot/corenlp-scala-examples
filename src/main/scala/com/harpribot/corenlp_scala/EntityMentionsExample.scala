package com.harpribot.corenlp_scala

/**
  * Created by harshal on 1/12/17.
  */
import java.io.File
import java.nio.charset.Charset
import java.util.Properties

import com.google.common.io.Files
import edu.stanford.nlp.coref.CorefCoreAnnotations
import edu.stanford.nlp.ling.CoreAnnotations
import edu.stanford.nlp.pipeline.{Annotation, StanfordCoreNLP}
import edu.stanford.nlp.util.CoreMap

import scala.collection.JavaConverters._

/**
  * Created by harshal on 1/11/17.
  */
object EntityMentionsExample {

  def main(args: Array[String]): Unit = {
    val props: Properties = new Properties()
    props.put("annotators", "tokenize, ssplit, pos, lemma, ner, depparse, mention")

    val pipeline: StanfordCoreNLP = new StanfordCoreNLP(props)

    // read some text from a file - Uncomment this and comment the val text = "Quick...." below to load from a file
    //val inputFile: File = new File("src/test/resources/sample-content.txt")
    //val text: String = Files.toString(inputFile, Charset.forName("UTF-8"))
    val text = "Barack Obama has one mention but contains two words. New York City has one mention but has three words."

    // create blank annotator
    val document: Annotation = new Annotation(text)

    // run all Annotator - Tokenizer on this text
    pipeline.annotate(document)


    val sentences: List[CoreMap] = document.get(classOf[CoreAnnotations.SentencesAnnotation]).asScala.toList

    sentences.foreach { sentence =>
      println(sentence)
      println("---")
      println("mentions")
      sentence.get(classOf[CorefCoreAnnotations.CorefMentionsAnnotation]).asScala.foreach(m => println("\t" + m ))
      println("\n\n")
    }


  }

}




