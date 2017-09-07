package com.company;

import com.google.gson.Gson;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;

import java.util.ArrayList;

class Sentiment140Analyser implements Analyser {
    ArrayList<Tweet> tweets = new ArrayList<>();
    Writer writer;

    public void Analyse(Tweet tweet) {
        tweets.add(tweet);
        if(tweets.size()>240)
        {
            SendTweets();
            tweets.clear();
        }
    }

    public void Finish() {
        SendTweets();
        writer.Write();
    }

    public void SendTweets() {
        try {
            Tweet[] tweetArray = tweets.toArray(new Tweet[tweets.size()]);
            sentiment140TweetCollection tweetCollection = new sentiment140TweetCollection(tweetArray);
            HttpClient httpClient = new DefaultHttpClient();
            Gson gsonJsonConverter = new Gson();
            HttpPost request = new HttpPost("http://www.sentiment140.com/api/bulkClassifyJson");
            StringEntity params = null;
            params = new StringEntity(gsonJsonConverter.toJson(tweetCollection));
            request.addHeader("content-type", "application/x-www-form-urlencoded");
            request.setEntity(params);
            HttpResponse response = httpClient.execute(request);
            sentiment140TweetCollection fromTheAPI = (sentiment140TweetCollection)gsonJsonConverter.fromJson(new BasicResponseHandler().handleResponse(response), sentiment140TweetCollection.class);
            for(int i =0;i<fromTheAPI.data.length;i++)
            {
                writer.lines.add(fromTheAPI.data[i].inputLine + ",\""+ fromTheAPI.data[i].polarity+"\"");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
