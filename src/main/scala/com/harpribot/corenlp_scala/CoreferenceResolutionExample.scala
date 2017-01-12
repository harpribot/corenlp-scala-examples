package com.harpribot.corenlp_scala

/**
  * Created by harshal on 1/12/17.
  */
import java.io.File
import java.nio.charset.Charset
import java.util.Properties

import com.google.common.io.Files
import edu.stanford.nlp.coref.CorefCoreAnnotations
import edu.stanford.nlp.coref.data.CorefChain
import edu.stanford.nlp.pipeline.{Annotation, StanfordCoreNLP}

import scala.collection.JavaConverters._

/**
  * Created by harshal on 1/11/17.
  */
object CoreferenceResolutionExample {

  def main(args: Array[String]): Unit = {
    val props: Properties = new Properties()
    props.put("annotators", "tokenize, ssplit, pos, lemma, ner, parse, coref")

    val pipeline: StanfordCoreNLP = new StanfordCoreNLP(props)

    // read some text from a file - Uncomment this and comment the val text = "Quick...." below to load from a file
    //val inputFile: File = new File("src/test/resources/sample-content.txt")
    //val text: String = Files.toString(inputFile, Charset.forName("UTF-8"))
    val text = "Barack Obama was born in Hawaii.  He is the president. Obama was elected in 2008."

    // create blank annotator
    val document: Annotation = new Annotation(text)

    // run all Annotator - Tokenizer on this text
    pipeline.annotate(document)

    val coreferenceChain: List[CorefChain] = document.get(classOf[CorefCoreAnnotations.CorefChainAnnotation]).asScala.values.toList

    // print coreference chain
    println("coreference chains")
    coreferenceChain.foreach(x => println("\t" + x))

  }
}




