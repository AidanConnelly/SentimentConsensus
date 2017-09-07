package com.company;

import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.neural.rnn.RNNCoreAnnotations;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.sentiment.SentimentCoreAnnotations;
import edu.stanford.nlp.trees.Tree;
import edu.stanford.nlp.util.CoreMap;

import java.util.Properties;

class StandfordNLPAnalyser implements Analyser
{
    Writer writer;
    Properties props;
    StanfordCoreNLP pipeline ;

    public StandfordNLPAnalyser() {
        props = new Properties();
        props.setProperty("annotators", "tokenize, ssplit, parse, sentiment");
        pipeline = new StanfordCoreNLP(props);
    }

    public void Analyse(Tweet tweet) {
        Annotation annotation = new Annotation(tweet.text);
        pipeline.annotate(annotation);
        int mainSentiment = 0;
        int longest = 0;
        for (CoreMap sentence : annotation.get(CoreAnnotations.SentencesAnnotation.class)) {
            Tree tree = sentence.get(SentimentCoreAnnotations.SentimentAnnotatedTree.class);
            int sentiment = RNNCoreAnnotations.getPredictedClass(tree);
            String partText = sentence.toString();
            if (partText.length() > longest) {
                mainSentiment = sentiment;
                longest = partText.length();
            }
        }
        writer.lines.add(tweet.inputLine + ",\""+ mainSentiment+"\"");
    }

    public void Finish() {
        writer.Write();

    }
}
