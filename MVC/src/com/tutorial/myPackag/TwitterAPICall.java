package com.tutorial.myPackag;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TwitterAPICall {
	String urlbasic="";
  
	 public TwitterAPICall(String Bname,String Location,String From,String To)
     {
		  String q=Bname;
	      q=q.replaceAll(" ", "%20");
	      String l=Location;
	      l=l.replaceAll(" ", "%20");
	      String t1=From;
	      String t2=To;
	       urlbasic="https://search.twitter.com/search.json?q="+q+"%20"+l+"&since="+t1+"&until="+t2+"&page=";
	      
	    }
	 
    public int search() throws MalformedURLException, IOException
    {
      int count=15;
      int i=1;
      int num=0;
      while(count==15||count==14)
      {
        URL url = new URL(urlbasic+i);
        URLConnection urlConnection = url.openConnection();
        InputStream result = urlConnection.getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(result));
        String line = null;
        while ((line = reader.readLine()) != null) {
        	Pattern p=Pattern.compile("\"text\":\"(.*?)}");
        	Matcher m = p.matcher(line);
        	int c1=line.length();
        	line=line.replaceAll("metadata", "");
        	int c2=line.length();
        	count=(c1-c2)/8;
        	if(count!=15&&count!=14)
        	{
        		num=num+count;
        		System.out.println("No of tweets for your Businees :"+num);
        	}
        	if(count==15)
        	{
        		i++;
        		num=num+15;
        	}
        	if(count==14)
        	{
        		i++;
        		num=num+14;
        	}
     } 
        reader.close();
          }
      return num;
    }
	
}


