package com.company;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class Main2 {
    public static void main(String[] args) {
            List<String> strns = new ArrayList<String>();
            String path = args[0];
            Path file = Paths.get(path);
            try {
                List<String> c = Files.readAllLines(file, Charset.forName("ISO-8859-1"));
                c.remove(0);
                strns.addAll(c);
                uClassifySentimentAnalyser u = new uClassifySentimentAnalyser();
                Sentiment140Analyser s = new Sentiment140Analyser();
                SentiStrengthAnalyser ss = new SentiStrengthAnalyser();
                StandfordNLPAnalyser nlp = new StandfordNLPAnalyser();
                vaderSentimentAnalyser vad = new vaderSentimentAnalyser();
                ArrayList<Analyser> analysers = new ArrayList<>();
                analysers.add(u);
                analysers.add(s);
                analysers.add(ss);
                analysers.add(nlp);
                analysers.add(vad);
                String dest = "Cargs[0];";
                u.writer = new Writer(dest +"_uCl.csv");
                s.writer = new Writer(dest +"_140.csv");
                ss.writer = new Writer(dest +"_Str.csv");
                nlp.writer = new Writer(dest +"_NLP.csv");
                vad.writer = new Writer(dest +"_Vad.csv");

                for(int j =0;j<strns.size();j++) {
                    Tweet thisTweet = new Tweet(strns.get(j), strns.get(j).split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)")[2]);
                    System.out.println(thisTweet.text);
                    for (int k = 0; k < analysers.size(); k++) {
                        analysers.get(k).Analyse(thisTweet);
                    }
                }
                for (int k = 0; k < analysers.size(); k++) {
                    analysers.get(k).Finish();
                }
            } catch (Exception e) {
                System.out.println(e.fillInStackTrace());
            }
    }

}

