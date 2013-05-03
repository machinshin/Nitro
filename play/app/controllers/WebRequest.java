package controllers;

import models.BusinessRating;
import models.YelpReview;
import models.Tweets;

import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.scribe.builder.ServiceBuilder;
import org.scribe.model.OAuthRequest;
import org.scribe.model.Response;
import org.scribe.model.Token;
import org.scribe.model.Verb;
import org.scribe.oauth.OAuthService;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.node.ObjectNode;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.yelp.v2.Business;
import com.yelp.v2.Location;
import com.yelp.v2.Region;
import com.yelp.v2.YelpSearchResult;
import play.Logger;
import com.yelp.ApiAccessor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.twitter.LocalDateRange;

import org.joda.time.*;

public class WebRequest {
    // Define your keys, tokens and secrets.  These are available from the Yelp website.
    private static String YELP_CONSUMER_KEY       = "F1IWn950Cua0xPRrC18jUg";
    private static String YELP_CONSUMER_SECRET    = "mgE-UjUxeEFAO6nvNXFIpneHUnc";
    private static String YELP_TOKEN              = "K4dtPAHehuTwqkaHbMYBjRlh2vN-OBLg";
    private static String YELP_TOKEN_SECRET       = "1esUhMkTc7Y0wc-6I6R-4kkAhHk";

    private static String TWITTER_CONSUMER_KEY    = "aghhkkIt2WQrwVHzibUGlw";
    private static String TWITTER_CONSUMER_SECRET = "YtNCBxNUEjdI6A5GEJ51AY7lVjqcWXh9jiZUY3zAg";
    private static String TWITTER_TOKEN           = "316142697-PtuRA7TheCMNAA2CPyoGWns0MKlX0cip19AdeAA";
    private static String TWITTER_TOKEN_SECRET    = "BngacegFpm35OvbtcpCz3WLjCo15NN3znCPSmgMIF9g";

    private static BusinessRating loadFromYelp(String businessId) {
        if(( businessId == null) || businessId.trim().equals("")){
            Logger.info("no business id!");
            return null;
        }
        try {
            Logger.info("business id!="+businessId);
            // Execute a signed call to the Yelp service.
            OAuthService service = new ServiceBuilder().provider(
                    ApiAccessor.class).apiKey(YELP_CONSUMER_KEY).apiSecret(
                        YELP_CONSUMER_SECRET).build();
            Token accessToken = new Token(YELP_TOKEN, YELP_TOKEN_SECRET);
            String url = "http://api.yelp.com/v2/business/"+businessId;
            Logger.debug("url="+url);
            OAuthRequest request = new OAuthRequest(Verb.GET, url);
            service.signRequest(accessToken, request);
            Response response = request.send();
            ObjectMapper mapper = new ObjectMapper();
            Logger.info("response=> [" + response.getBody()+"]\n");
            JsonNode root = mapper.readTree( response.getBody());

            String name = root.path("name").getValueAsText();
            Float avgRating = new Float( root.path("rating").getValueAsDouble());
            Integer reviewCount = new Integer( root.path("review_count").
                    getValueAsInt());

            Float latitude = new Float ( root.path("location").
                        path("coordinate").path("latitude").getValueAsInt());

            Float longitude = new Float ( root.path("location").
                        path("coordinate").path("longitude").getValueAsInt());

            BusinessRating r = new BusinessRating( businessId, name,
                    avgRating, reviewCount, new Date(),latitude, longitude);
            r.save();
            //get the full Yelp data as well
            JsonNode reviewsNode = root.path("reviews");
            Iterator<JsonNode> it = reviewsNode.getElements();

            while(it.hasNext() ){
                JsonNode review = it.next();
                JsonNode user = review.path("user");
                Integer yReviewId = new Integer( review.path("id").getValueAsInt());
                Float yRating = new Float( review.path("rating").getValueAsDouble());
                String excerpt = review.path("excerpt").getValueAsText();
                //java expects this in milliseconds
                Date timeCreated =
                    new Date( review.path("time_created").getValueAsLong()*1000 );
                String yUserName = user.path("name").getValueAsText();
                String yUserId = user.path("id").getValueAsText();
                new YelpReview( yReviewId, yRating, excerpt,
                        yUserName, yUserId, timeCreated, r).save();
            }
            return r;
        }catch( Exception e ){
            Logger.error("yelp connection failed!"+e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    public static void loadFromTwitter( final BusinessRating business ) {
            Logger.error("is this being called even?!");
        if(( business.name == null) || business.name.trim().equals("")){
            Logger.info("no business name!");
            return;
        }

        try {
            Logger.debug("biz name="+business.name);
            String name= business.name.replaceAll(" ", "");
            Logger.debug("twitNAME="+business.name);
            //get tweets within 300 miles of the business location
            String geocode="&geocode="+business.latitude+","+business.longitude+
                ",300mi";

            OAuthService service = new ServiceBuilder().provider(
                ApiAccessor.class).apiKey(
                    TWITTER_CONSUMER_KEY).apiSecret(
                    TWITTER_CONSUMER_SECRET).build();

            Token accessToken = new Token(TWITTER_TOKEN, TWITTER_TOKEN_SECRET);

            String prefix="https://api.twitter.com/1.1/search/tweets.json?q="
                +name+"&count=100";

            //we go back a week and iterate thru, getting tweets
            LocalDate now = LocalDate.now();
            LocalDate weekAgo = LocalDate.now().minusDays(7);
            Logger.debug("now ="+now.toString());
            Logger.debug("week ago="+weekAgo.toString());

            //for( int i = 0; i < 7; ++i ){
            LocalDateRange range = new LocalDateRange( weekAgo, now );
            for( LocalDate date: range ){
                String sinceTil = "&since="+date.toString() +
                    "&until=" + date.plusDays(1).toString();

                String urlStr = prefix+sinceTil;
                Logger.debug("Twit URL="+urlStr);
                Logger.debug("date="+date.toString());
                OAuthRequest request = new OAuthRequest(Verb.GET, urlStr);
                service.signRequest(accessToken, request);
                Response response = request.send();
                ObjectMapper mapper = new ObjectMapper();
                Logger.debug("response=>[" + response.getBody()+"]");
                JsonNode root = mapper.readTree(response.getBody());
                String rawData = response.getBody();
                int count=0;
                int c1 = rawData.length();
                rawData = rawData.replaceAll("metadata", "");
                //Logger.debug("raw Data = " + rawData);
                int c2 = rawData.length();
                count = (c1-c2)/8;
                Logger.debug("twit count+ "+Integer.toString(count));
                //int pageCount = root.path("page").getValueAsInt();
                //int rpp = root.path("results_per_page").getValueAsInt();
                //TODO add followerCount, but looks like a seperate webgl call? 
                //int followerCount = 0;
                Tweets t = new Tweets(Integer.valueOf(count),
                        date.toDateTimeAtStartOfDay().toDate(), 0, business);
                t.save();
            }
        } catch(Exception e){
            Logger.error("twitter connection failed!"+e.getMessage());
            e.printStackTrace();
        }
    }

    public static BusinessRating loadFromWeb (final String businessId ) {
        BusinessRating r = loadFromYelp(businessId);
        loadFromTwitter(r);
        return r;
    }

}

