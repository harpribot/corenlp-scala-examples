package com.harpribot.corenlp_scala

/**
  * Created by harshal on 1/12/17.
  */
import java.io.File
import java.nio.charset.Charset
import java.util.Properties

import com.google.common.io.Files
import edu.stanford.nlp.ie.machinereading.structure.MachineReadingAnnotations
import edu.stanford.nlp.ling.CoreAnnotations.SentencesAnnotation
import edu.stanford.nlp.pipeline.{Annotation, StanfordCoreNLP}
import edu.stanford.nlp.util.CoreMap

import scala.collection.JavaConverters._

/**
  * Created by harshal on 1/11/17.
  */
object RelationExtractionExample {

  def main(args: Array[String]): Unit = {
    /**
    ****** NOTE - do this before running the code ********
    First Train the model using this command on the terminal. NOTE - Replace <USER-HOME> with the path of your home
    directory, which is obtained by doing
    "cd ~;pwd"
    on the terminal.

    The following assumes that you have jars installed in the maven repository, which will be the case if you imported
    this project properly and the IDE auto-imports using the provided POM. You can also just provide the absolute paths
    to wherever you have imported the two jars.

    java -cp "<USER-HOME>/.m2/repository/edu/stanford/nlp/stanford-corenlp/3.7.0/stanford-corenlp-3.7.0-models.jar:<USER-HOME>/.m2/repository/edu/stanford/nlp/stanford-corenlp/3.7.0/stanford-corenlp-3.7.0.jar" edu.stanford.nlp.ie.machinereading.MachineReading --arguments src/main/resources/roth.properties

     */
    val props: Properties = new Properties()
    props.put("annotators", "tokenize,ssplit,pos,lemma,ner,parse,depparse,relation")
    props.put("sup.relation.model", "src/main/resources/roth_relation_model_pipeline.ser")

    val pipeline: StanfordCoreNLP = new StanfordCoreNLP(props)

    // read some text from a file - Uncomment this and comment the val text = "Rome is in...." below to load from a file
    //val inputFile: File = new File("src/test/resources/sample-content.txt")
    //val text: String = Files.toString(inputFile, Charset.forName("UTF-8"))
    val text = "Rome is in Lazio province and Naples in Campania."

    // create blank annotator
    val document: Annotation = new Annotation(text)

    // run all Annotator - Tokenizer on this text
    pipeline.annotate(document)

    val sentences: List[CoreMap] = document.get(classOf[SentencesAnnotation]).asScala.toList

    (for {
      sentence: CoreMap <- sentences
      relation = sentence.get(classOf[MachineReadingAnnotations.RelationMentionsAnnotation])

    } yield (sentence, relation)) foreach(
      t => println("sentence:\n" + t._1 +
        "\n\n Relation:\n" + t._2 +
        "\n\n"))
  }

}



