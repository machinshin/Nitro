package controllers;

import play.*;
import play.libs.Json;
import play.libs.Json.*;
import static play.libs.Json.toJson;
import play.mvc.*;
import play.data.*;
import org.joda.time.Duration;
import java.util.*;
import play.data.validation.*;
import play.libs.Jsonp;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.node.ObjectNode;
import org.codehaus.jackson.node.ArrayNode;
import org.codehaus.jackson.annotate.JsonAutoDetect.Visibility;
import org.codehaus.jackson.annotate.JsonMethod;

import models.*;
import views.html.*;

public class Application extends Controller {

    static Form<BizId> searchForm = Form.form(BizId.class);

    public static Result index() {
        return redirect(routes.Application.search());
    }

    public static Result search() {
        return status(405, "Error Code 405: Use /api/, this won't work");
    }

    public static Result allService(String callback) {
        Logger.debug("ALL SERVICE CALLBACK!: " + callback);
        Logger.debug("json uri: " + request().uri());
        ObjectNode result = Json.newObject();
        String uri = request().uri();
        String businessId = uri.substring(uri.lastIndexOf("?id=")+4, uri.indexOf("&"));
        Logger.debug("substring: " + businessId );

        if(businessId == null ) {
            result.put("status", "error");
            result.put("message", "Missing parameter [id]");
            return badRequest(result);
        }

        BusinessRating rating = BusinessRating.find.byId(businessId);
        if( rating == null){
            rating = WebRequest.loadFromWeb(businessId);
            if( rating == null ) {
               result.put("status", "error");
               result.put("message", "Unable to process, Check your request");
               return badRequest(result);
            }
        }
        List<YelpReview> reviews = YelpReview.findReviewsForBiz(rating.businessId);
        List<Tweets> tweets = Tweets.findTweetsForBiz(rating.businessId);

        if (tweets.isEmpty()) {
            WebRequest.loadFromTwitter(rating);
            tweets = Tweets.findTweetsForBiz(rating.businessId);
        }

        try {
            ObjectNode ratingNode = result.putObject("rating");
            rating.toObjectNode(ratingNode);

            ArrayNode reviewsNode = result.putArray("reviews");
            for( YelpReview review: reviews) {
                ObjectNode reviewNode =reviewsNode.addObject();
                review.toObjectNode(reviewNode);
            }
            ArrayNode tweetsNode = result.putArray("tweets");
            for( Tweets tweet: tweets ) {
                ObjectNode tn = tweetsNode.addObject();
                tweet.toObjectNode(tn);
            }
            result.put("status", "ok");
        } catch (Exception e) {
            result.put("message", "unable to write json, check server logs!="+ e.getMessage());
            result.put("status", "error");
            e.printStackTrace();
        }


        return ok(Jsonp.jsonp(callback, result));
    }

    @BodyParser.Of(BodyParser.Json.class)
    public static Result yelpToJson(){
        JsonNode json = request().body().asJson();
        ObjectNode result = Json.newObject();
        if( json == null ){
            result.put("status", "error");
            result.put("message", "Expecting Json data");
            return badRequest(result);
        }
        String businessId = json.findPath("id").getTextValue();
        if(businessId == null ) {
            result.put("status", "error");
            result.put("message", "Missing parameter [id]");
            return badRequest(result);
        }
        BusinessRating rating = BusinessRating.find.byId(businessId);
        if( rating == null){
            rating = WebRequest.loadFromWeb(businessId);
            if( rating == null ) {
               result.put("status", "error");
               result.put("message", "Unable to process, Check your request");
               return badRequest(result);
            }
        }
        List<YelpReview> reviews = YelpReview.findReviewsForBiz(rating.businessId);
        try {
            ArrayNode reviewsNode = result.putArray("reviews");
            for( YelpReview review: reviews) {
                ObjectNode reviewNode =reviewsNode.addObject();
                review.toObjectNode(reviewNode);
            }

            result.put("status", "ok");
        } catch (Exception e) {
            result.put("message", "unable to write json, check server logs!="+ e.getMessage());
            result.put("status", "error");
            e.printStackTrace();
        }
        return ok(result);
    }

    @BodyParser.Of(BodyParser.Json.class)
    public static Result twitterToJson(){
        JsonNode json = request().body().asJson();
        ObjectNode result = Json.newObject();
        if( json == null ){
            result.put("status", "error");
            result.put("message", "Expecting Json data");
            return badRequest(result);
        }
        String businessId = json.findPath("id").getTextValue();
        if(businessId == null ) {
            result.put("status", "error");
            result.put("message", "Missing parameter [id]");
            return badRequest(result);
        }
        BusinessRating rating = BusinessRating.find.byId(businessId);
        if( rating == null){
            rating = WebRequest.loadFromWeb(businessId);
            if( rating == null ) {
                result.put("status", "error");
                result.put("message", "Unable to process, Check your request");
                return badRequest(result);
            }
        }

        List<Tweets> tweets = Tweets.findTweetsForBiz(rating.businessId);

        try {
            if (tweets.isEmpty()) {
                WebRequest.loadFromTwitter(rating);
                tweets = Tweets.findTweetsForBiz(rating.businessId);
            }
            ArrayNode tweetsNode = result.putArray("tweets");
            for( Tweets tweet: tweets ) {
                ObjectNode tn = tweetsNode.addObject();
                tweet.toObjectNode(tn);
            }
            result.put("status", "ok");
        } catch (Exception e) {
            result.put("message", "unable to write json, check server logs!="+ e.getMessage());
            result.put("status", "error");
            e.printStackTrace();
        }
        return ok(result);

    }

    @BodyParser.Of(BodyParser.Json.class)
    public static Result ratingToJson(){
        JsonNode json = request().body().asJson();
        ObjectNode result = Json.newObject();
        if( json == null ){
            result.put("status", "error");
            result.put("message", "Expecting Json data");
            return badRequest(result);
        }
        String businessId = json.findPath("id").getTextValue();
        if(businessId == null ) {
            result.put("status", "error");
            result.put("message", "Missing parameter [id]");
            return badRequest(result);
        }

        BusinessRating rating = BusinessRating.find.byId(businessId);
        if( rating == null){
            rating = WebRequest.loadFromWeb(businessId);
            if( rating == null ) {
               result.put("status", "error");
               result.put("message", "Unable to process, Check your request");
               return badRequest(result);
            }
        }

        try {
            ObjectNode ratingNode = result.putObject("rating");
            rating.toObjectNode(ratingNode);

            result.put("status", "ok");
        } catch (Exception e) {
            result.put("message", "unable to write json, check server logs!="+ e.getMessage());
            result.put("status", "error");
            e.printStackTrace();
        }
        return ok(result);
    }

    @BodyParser.Of(BodyParser.Json.class)
    public static Result allToJson(){
        JsonNode json = request().body().asJson();
        ObjectNode result = Json.newObject();
        if( json == null ){
            result.put("status", "error");
            result.put("message", "Expecting Json data");
            return badRequest(result);
        }
        String businessId = json.findPath("id").getTextValue();
        if(businessId == null ) {
            result.put("status", "error");
            result.put("message", "Missing parameter [id]");
            return badRequest(result);
        }

        BusinessRating rating = BusinessRating.find.byId(businessId);
        if( rating == null){
            rating = WebRequest.loadFromWeb(businessId);
            if( rating == null ) {
               result.put("status", "error");
               result.put("message", "Unable to process, Check your request");
               return badRequest(result);
            }
        }
        List<YelpReview> reviews = YelpReview.findReviewsForBiz(rating.businessId);
        List<Tweets> tweets = Tweets.findTweetsForBiz(rating.businessId);

        if (tweets.isEmpty()) {
            WebRequest.loadFromTwitter(rating);
            tweets = Tweets.findTweetsForBiz(rating.businessId);
        }

        try {
            ObjectNode ratingNode = result.putObject("rating");
            rating.toObjectNode(ratingNode);

            ArrayNode reviewsNode = result.putArray("reviews");
            for( YelpReview review: reviews) {
                ObjectNode reviewNode =reviewsNode.addObject();
                review.toObjectNode(reviewNode);
            }
            ArrayNode tweetsNode = result.putArray("tweets");
            for( Tweets tweet: tweets ) {
                ObjectNode tn = tweetsNode.addObject();
                tweet.toObjectNode(tn);
            }
            result.put("status", "ok");
        } catch (Exception e) {
            result.put("message", "unable to write json, check server logs!="+ e.getMessage());
            result.put("status", "error");
            e.printStackTrace();
        }

        return ok(result);
    }

    public static void displayErrorsToLog(Map<String, List<ValidationError>> errors ){
        for(Map.Entry<String, List<ValidationError>> entry: errors.entrySet()){
            Logger.info("error key:"+entry.getKey());
            List<ValidationError> listErrors = entry.getValue();
            Iterator<ValidationError> it = listErrors.iterator();
            while( it.hasNext()) {
                ValidationError err = it.next();
                Logger.info("errkey:" +err.key());
                Logger.info("errval:" +err.message());
            }
        }
    }
}
