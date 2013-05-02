package com.tutorial.myPackag;

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

	public String [] start(String bid) {
		// Define your keys, tokens and secrets.  These are available from the Yelp website.  
		String CONSUMER_KEY = "F1IWn950Cua0xPRrC18jUg";
		String CONSUMER_SECRET = "mgE-UjUxeEFAO6nvNXFIpneHUnc";
		String TOKEN = "K4dtPAHehuTwqkaHbMYBjRlh2vN-OBLg";
		String TOKEN_SECRET = "1esUhMkTc7Y0wc-6I6R-4kkAhHk";
		String lat = "37.3041";
		String lng = "-121.87";
		String category ="coffee";
		String location="San Fransisco";
		String [] arr=new String[3];
		// Execute a signed call to the Yelp service.  
		OAuthService service = new ServiceBuilder().provider(YelpV2API.class).apiKey(CONSUMER_KEY).apiSecret(CONSUMER_SECRET).build();
		Token accessToken = new Token(TOKEN, TOKEN_SECRET);
		//OAuthRequest request = new OAuthRequest(Verb.GET, "http://api.yelp.com/v2/search");
	//	OAuthRequest request = new OAuthRequest(Verb.GET, "http://api.yelp.com/v2/business/starbucks-san-jose-67");
		OAuthRequest request = new OAuthRequest(Verb.GET, "http://api.yelp.com/v2/business/"+bid);
		request.addQuerystringParameter("ll", lat + "," + lng);
		request.addQuerystringParameter(location, location);
		request.addQuerystringParameter("category", category);
		service.signRequest(accessToken, request);
		Response response = request.send();
		String rawData = response.getBody();
		//YelpSearchResult places = new Gson().fromJson(rawData, YelpSearchResult.class)
		//System.out.println(rawData);
		 String strval[];
		 String intdata[];
		 strval=rawData.split(",");
		 for(int i=0;i<=strval.length;i++)
		 {
			 intdata= strval[i].split(":");
			 intdata[0]=intdata[0].replace('"',' ').trim();
	    	 if(intdata[0].equalsIgnoreCase("rating"))
			 {
				 System.out.println("Rating:: "+intdata[1].trim());
				 arr[0]=intdata[1].trim().toString();
			 }
			 if(intdata[0].equalsIgnoreCase("review_count"))
			 {
				 System.out.println("Review Count:: "+intdata[1].trim());
				 arr[1]=intdata[1].trim().toString();
			 }
			 
			 if(intdata[0].equalsIgnoreCase("excerpt"))
			 {
				 System.out.println("Reviews :: "+intdata[1].trim());
				 i++;
				 if(!strval[i].contains("time_created"))
				 {
					 
					  System.out.println(strval[i]);
				 }
				 else{
					 break;
				 }
				 System.out.println();
			 }  
		  }
		 return arr;
		}
	
	}
