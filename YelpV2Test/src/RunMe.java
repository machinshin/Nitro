import java.io.IOException;
import java.net.MalformedURLException;

import org.scribe.builder.ServiceBuilder;
import org.scribe.model.OAuthRequest;
import org.scribe.model.Response;
import org.scribe.model.Token;
import org.scribe.model.Verb;
import org.scribe.oauth.OAuthService;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.yelp.v2.Business;
import com.yelp.v2.Location;
import com.yelp.v2.Region;
import com.yelp.v2.YelpSearchResult;

public class RunMe {

	public void start() {
		// Define your keys, tokens and secrets.  These are available from the Yelp website.  
		String CONSUMER_KEY = "F1IWn950Cua0xPRrC18jUg";
		String CONSUMER_SECRET = "mgE-UjUxeEFAO6nvNXFIpneHUnc";
		String TOKEN = "K4dtPAHehuTwqkaHbMYBjRlh2vN-OBLg";
		String TOKEN_SECRET = "1esUhMkTc7Y0wc-6I6R-4kkAhHk";
		
		// Some example values to pass into the Yelp search service.  
		//String lat = "30.361471";
		//String lng = "-87.164326";
		String lat = "37.3041";
		String lng = "-121.87";
	//	String category = "restaurants";
		String category ="coffee";
	//	Location loc=new Location();
		//loc.setCity("San Jose");
		String location="San Fransisco";
		//String location="";
		// Execute a signed call to the Yelp service.  
		OAuthService service = new ServiceBuilder().provider(YelpV2API.class).apiKey(CONSUMER_KEY).apiSecret(CONSUMER_SECRET).build();
		Token accessToken = new Token(TOKEN, TOKEN_SECRET);
		//OAuthRequest request = new OAuthRequest(Verb.GET, "http://api.yelp.com/v2/search");
		OAuthRequest request = new OAuthRequest(Verb.GET, "http://api.yelp.com/v2/business/starbucks-san-jose-67");
		request.addQuerystringParameter("ll", lat + "," + lng);
		request.addQuerystringParameter(location, location);
		request.addQuerystringParameter("category", category);
		//request.addQuerystringParameter(location, loc);
		//System.out.println("this is URL"+request);
		service.signRequest(accessToken, request);
		
		Response response = request.send();
		
		String rawData = response.getBody();
		
		//YelpSearchResult places = new Gson().fromJson(rawData, YelpSearchResult.class)
		//System.out.println(rawData);
		String strval[];
		 String intdata[];
		 strval=rawData.split(",");
		 for(int i=0;i<strval.length;i++)
		 {
			// System.out.println("value "+strval[i]);
			 intdata= strval[i].split(":");
		//	 System.out.println(intdata[1]);
			 intdata[0]=intdata[0].replace('"',' ').trim();
			 //System.out.println(intdata[0]); review_count  excerpt
			 if(intdata[0].equalsIgnoreCase("rating"))
			 {
				 System.out.println("Rating:: "+intdata[1].trim());
			 }
			 if(intdata[0].equalsIgnoreCase("review_count"))
			 {
				 System.out.println("Review Count:: "+intdata[1].trim());
			 }
			 
			 if(intdata[0].equalsIgnoreCase("excerpt"))
			 {
				 System.out.println("Reviews :: "+intdata[1].trim());
				 i++;
				 if(!strval[i].contains("time_created"))
				 {
					 
					 //System.out.println();
					 System.out.println(strval[i]);
				 }
				 else{
					 break;
				 }
				 System.out.println();
			 }  
			 
		 } 
		
		
	//System.out.println(""+rawData);	
		// Sample of how to turn that text into Java objects.  
	/*	try {
			YelpSearchResult places = new Gson().fromJson(rawData, YelpSearchResult.class);
			 
			//System.out.println("Your search found " + places.getTotal() + " results.");
			//System.out.println("Yelp returned " + places.getBusinesses().size() + " businesses in this request.");
			//System.out.println("Yelp returned " + places.getBusinesses().size() + " businesses in this request.");
			System.out.println();
			
			for(Business biz : places.getBusinesses()) {
				System.out.println(biz.getName());
				for(String address : biz.getLocation().getAddress()) {					
					//System.out.println("  " + address);
					System.out.println("Business ID" + biz.getId());
					//System.out.println("Business rating " + biz.getRatingImgUrl());
					//System.out.println("Business Review count " + biz.getReviewCount());
				}
			//	System.out.println("city  " + biz.getLocation().getCity());
				//System.out.println(biz.getUrl());
				System.out.println();
			}
			
			
		} catch(Exception e) {
			System.out.println("Error, could not parse returned data!");
			System.out.println(rawData);			
		}
		*/
			
	}


	public static void main(String[] args) throws MalformedURLException, IOException {
		// TODO Auto-generated method stub
		new RunMe().start();
		TwitterAPICall tsearch=new TwitterAPICall();

		  tsearch.search();
	}

}
