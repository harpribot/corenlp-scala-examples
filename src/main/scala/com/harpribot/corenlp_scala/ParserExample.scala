package com.harpribot.corenlp_scala

/**
  * Created by harshal on 1/12/17.
  */
import java.io.File
import java.nio.charset.Charset
import java.util.Properties

import com.google.common.io.Files
import edu.stanford.nlp.ling.CoreAnnotations.SentencesAnnotation
import edu.stanford.nlp.pipeline.{Annotation, StanfordCoreNLP}
import edu.stanford.nlp.semgraph.SemanticGraph
import edu.stanford.nlp.semgraph.SemanticGraphCoreAnnotations.{BasicDependenciesAnnotation, EnhancedDependenciesAnnotation, EnhancedPlusPlusDependenciesAnnotation}
import edu.stanford.nlp.trees.TreeCoreAnnotations.TreeAnnotation
import edu.stanford.nlp.util.CoreMap
import edu.stanford.nlp.trees.Tree

import scala.collection.JavaConverters._

/**
  * Created by harshal on 1/11/17.
  */
object ParserExample {

  def main(args: Array[String]): Unit = {
    val props: Properties = new Properties()
    props.put("annotators", "tokenize, ssplit, parse")

    val pipeline: StanfordCoreNLP = new StanfordCoreNLP(props)

    // read some text from a file - Uncomment this and comment the val text = "Quick...." below to load from a file
    //val inputFile: File = new File("src/test/resources/sample-content.txt")
    //val text: String = Files.toString(inputFile, Charset.forName("UTF-8"))
    val text = "Quick brown fox jumps over the lazy dog because he stole 20 dollars. This is Harshal. My home country is India. Today is 12th January 2017. This is 100% right. Manish and Ravish are best friends."

    // create blank annotator
    val document: Annotation = new Annotation(text)

    // run all Annotator - Tokenizer on this text
    pipeline.annotate(document)

    val sentences: List[CoreMap] = document.get(classOf[SentencesAnnotation]).asScala.toList

    (for {
      sentence: CoreMap <- sentences
      constituencyParse: Tree = sentence.get(classOf[TreeAnnotation])
      basicDependencyParse: SemanticGraph = sentence.get(classOf[BasicDependenciesAnnotation])
      enhancedDependencyParse: SemanticGraph = sentence.get(classOf[EnhancedDependenciesAnnotation])
      enhancedDependencyPlusPlusParse: SemanticGraph = sentence.get(classOf[EnhancedPlusPlusDependenciesAnnotation])

    } yield (sentence, constituencyParse, basicDependencyParse,
      enhancedDependencyParse, enhancedDependencyPlusPlusParse)) foreach(
        t => println("sentence:\n" + t._1 +
        "\n\n Constituency parse tree:\n" + t._2 +
        "\n Basic Dependency Parse tree:\n" + t._3 +
        "\n Enhanced Dependency Parse tree:\n" + t._4 +
        "\n Enhanced Plus Plus Dependency Parse tree:\n" + t._5 +
        "\n\n"))


  }

}



