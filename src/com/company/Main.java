package com.company;

//import opin.*;
//import uk.ac.wlv.sentistrength.*;

import twitter4j.*;
import twitter4j.conf.ConfigurationBuilder;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class Main {

    public static void main(String[] args) {

        ConfigurationBuilder cb = new ConfigurationBuilder();
        cb.setDebugEnabled(true).setOAuthConsumerKey("rF1wVyeYmnlgkn59G901v0ioI")
                .setOAuthConsumerSecret("lyMEwuH0AYT7cF17LPOzo10xXUcw4dUjmAsSG8MWCXSfizzSMH")
                .setOAuthAccessToken("1349348400-mvh8Xd1IePBUakqeIAfc9TO2m5BaC6EqlkloU1t")
                .setOAuthAccessTokenSecret("5UHjc0d3h6FoP5toXLw6aObuYex8N5MV6wsZAbLhQ2MQc")
                .setTweetModeExtended(true);

String path = "C:/Users/aidan/tweets2.csv";
        Path file = Paths.get(path);



        // write your code sentiment140TweetCollection
        StatusListener listener = new StatusListener() {
            public void onStatus(Status status) {
                String[] d = new String[]{
                        status.getLang(),
                        String.format("%d",status.getId()),
                        status.getCreatedAt().toString(),
                        String.format("%d",status.getUser().getId()),
                        status.getText().replace("\r"," ").replace("\n"," "),
                        status.getUser().getScreenName()};

                String s = "";
                for(int i = 0;i<d.length;i++)
                {
                    s=s+d[i]+(d[i].length()<11?"\t\t":"")+"\t";
                }
                s=s+"\n";
                try {
                    Files.write(file, Arrays.asList(new String[]{s}), StandardOpenOption.APPEND);
                    System.out.println(s);
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }

            public void onDeletionNotice(StatusDeletionNotice statusDeletionNotice) {
            }

            public void onScrubGeo(long a,long b) {

            }

            public void onStallWarning(StallWarning stallWarning) {

            }

            public void onTrackLimitationNotice(int numberOfLimitedStatuses) {
            }

            public void onException(Exception ex) {
                ex.printStackTrace();
            }
        };

        FilterQuery f = new FilterQuery();
        f.track(new String[] {
                "#tdf",
                "#tdf2017",
                "@letour",
                "@p_latour",
                "@romainbardet ‏",
                "@FabioAru1",
                "@jakob_fuglsang ",
                "@richie_porte ‏",
                "@EmuBuchmann ‏",
                "@petosagan",
                "@majkaformal ‏",
                "@pierrerolland",
                "@MeraKudus",
                "@MarkCavendish ‏",
                "@ArnaudDemare ‏",
                "@ThibautPinot",
                "@GroenewegenD ‏",
                "@AndreGreipel",
                "@DeGendtThomas",
                "@alejanvalverde ‏",
                "@NairoQuinCo ‏",
                "@SimonYatess ",
                "@marcelkittel",
                "@chrisfroome ‏",
                "@blingmatthews ‏",
                "@albertocontador ‏",
                "@LouisMeintjes ",
                "@AG2R La Mondiale",
                "@Astana",
                "@BMC Racing Team",
                "@Bora–Hansgrohe",
                "@Cannondale–Drapac",
                "@Dimension Data",
                "@FDJ",
                "@LottoNL–Jumbo",
                "@Lotto–Soudal",
                "@Movistar",
                "@Movistar Team",
                "@Orica–Scott",
                "@Quick-Step Floors",
                "@Sky",
                "@Sunweb",
                "@Trek-Segafredo",
                "@UAE Team Emirates"
        });
        f.language(new String[] {"es","en","fr","de"});
        TwitterStream twitterStream = new TwitterStreamFactory(cb.build()).getInstance();

        twitterStream.addListener(listener);
        twitterStream.filter(f);
        // sample() method internally creates a thread which manipulates TwitterStream and calls these adequate listener methods continuously.
        //twitterStream.sample();
    }
}
