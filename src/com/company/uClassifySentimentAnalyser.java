package com.company;

import com.google.gson.Gson;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

class uClassifySentimentAnalyser implements Analyser
{
    Writer writer;
    ArrayList<Tweet> tweetsList = new ArrayList<>();

    public void Analyse(Tweet tweet) {
        tweetsList.add(tweet);
        if(tweetsList.size()>5) {
            sendToUclassify();
            tweetsList.clear();
        }
    }

    public void Finish() {
        sendToUclassify();
        writer.Write();
    }

    private void sendToUclassify(){
        try {
            uClassifyTweetCollection tweets = new uClassifyTweetCollection(tweetsList.toArray(new Tweet[tweetsList.size()]));
            byte[] requestData = new Gson().toJson(tweets).getBytes("UTF-8");
            URL url = new URL("https://api.uclassify.com/v1/uclassify/Sentiment/classify");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setDoOutput(true);
            conn.setUseCaches(false);
            conn.setInstanceFollowRedirects(false);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Content-Length", Integer.toString(requestData.length));
            conn.setRequestProperty("Authorization", "YOUR API TOKEN HERE");
            DataOutputStream wr = new DataOutputStream(conn.getOutputStream());
            wr.write(requestData);
            InputStream inputStream = conn.getInputStream();
            Reader in = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
/*            String s = "";
            for (int c = in.read(); c != -1; c = in.read()) {
                s = s + ((char) c);
            }*/
            resp[] typedresponse = new Gson().fromJson(in,resp[].class);
            for(int i = 0;i<tweetsList.size()&&i<typedresponse.length;i++)
            {
                writer.lines.add(tweetsList.get(i).inputLine + ",\"" + typedresponse[i].classification[0].p+"\",\"" + typedresponse[i].classification[1].p+"\"");
            }
        }
        catch (Exception e)
        {
            System.out.println(e.fillInStackTrace());

        }
    }
}
