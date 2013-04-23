

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


	    public TwitterAPICall()

	    {
	      String q="Hilton";
	      String t1="2013-04-20";
	      String t2="2013-04-22";
	       urlbasic="http://search.twitter.com/search.json?q="+q+"&since="+t1+"&until="+t2+"&page=";
	      
	    }

	    

	    public void search() throws MalformedURLException, IOException
	 
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
	 
	    //System.out.println(line);
		Pattern p=Pattern.compile("\"text\":\"(.*?)}");
		Matcher m = p.matcher(line);
		while(m.find()) {
		System.out.println(m.group(1));}
		int c1=line.length();
		line=line.replaceAll("metadata", "");
		int c2=line.length();
	    count=(c1-c2)/8;
	    if(count!=15&&count!=14)
	    {
	    	System.out.println("tweets:"+num);
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
	    }
	     
//public static void main(String[] args) throws MalformedURLException, IOException {
	
  //  TwitterAPICall tsearch=new TwitterAPICall();

  //  tsearch.search();

  
 // }
}