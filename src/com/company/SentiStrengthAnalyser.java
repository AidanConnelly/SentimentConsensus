package com.company;

import uk.ac.wlv.sentistrength.SentiStrength;

class SentiStrengthAnalyser implements Analyser
{
    Writer writer;
    SentiStrength s;

    public SentiStrengthAnalyser()
    {
        s = new SentiStrength();
        s.initialise(new String[]{"sentidata","YOUR URL TO CORPUS HERE","explain"});
    }

    public void Analyse(Tweet tweet) {
        String[] outputFromSentiStrength = this.s.computeSentimentScores(tweet.text).split("[( -) ]");
        writer.lines.add(tweet.inputLine + ",\""+ outputFromSentiStrength[0]+ ",\""+ outputFromSentiStrength[1] +"\"");
    }

    public void Finish() {
        writer.Write();

    }
}
