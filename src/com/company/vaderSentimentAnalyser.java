package com.company;
import java.io.*;
import java.lang.*;

public class vaderSentimentAnalyser implements Analyser{
    public Writer writer;

    public void Analyse(Tweet tweet) {
        try {
            String command = "python -c \"import nltk.sentiment.vader; s = nltk.sentiment.vader.SentimentIntensityAnalyzer(); str=" + tweet.text.replace("\"", "\"\"") + ";print(s.polarity_scores(str))";
            String output = new BufferedReader(new InputStreamReader(Runtime.getRuntime().exec(command).getInputStream())).readLine();
            writer.lines.add(tweet.inputLine+",\""+ output.split("[,:]")[1]+"\",\""+ output.split("[,:]")[3]+"\",\""+ output.split("[,:]")[5] +"\"");
        }
        catch(Exception e)
        {
            System.out.println(e.fillInStackTrace());
        }
    }

    public void Finish() {
        writer.Write();
    }
}
