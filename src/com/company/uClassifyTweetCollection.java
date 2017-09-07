package com.company;

class uClassifyTweetCollection {
    public String[] texts;
    public uClassifyTweetCollection(Tweet[] tweets) {
        texts = new String[tweets.length];
        for(int i = 0;i<tweets.length;i++)
        {
            texts[i] = tweets[i].text;
        }
    }
}
