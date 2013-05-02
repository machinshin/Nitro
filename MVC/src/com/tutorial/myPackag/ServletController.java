package com.tutorial.myPackag;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/ServletController")
public class ServletController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String [] saveValues=new String[5];
		String [] Yelpval;
		String bID=request.getParameter("businessID");
		saveValues[0]=bID.toString();
		System.out.println("Business "+bID);
		String bName=request.getParameter("busineesName");
		saveValues[1]=bName.toString();
		String location=request.getParameter("location");
		saveValues[2]=location.toString();
		String from=request.getParameter("fromdate");
		String to=request.getParameter("todate");
		TwitterAPICall tweet=new TwitterAPICall(bName,location,from,to);
		
				
		RunMe yelp=new RunMe();
		
		Yelpval=yelp.start(bID);
		saveValues[2]=Yelpval[0];
		saveValues[3]=Yelpval[1];
		saveValues[4]= Integer.toString(tweet.search());
		PersonBeanModel person=new PersonBeanModel();
		try {
			person.InsertIntoDataBase(saveValues);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("error from insert ");
			e.printStackTrace();
		}
		
	//	PersonBeanModel person=new PersonBeanModel();
		//person.setBusinessID(businessID);
	//	String str="";
	/*	try {
			str = person.getBusinessID();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("str ="+str); */
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		
	}

}
