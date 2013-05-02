package com.tutorial.myPackag;

import sun.jdbc.odbc.JdbcOdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.SQLException;

import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class PersonBeanModel {
String BusinessID;

public String getBusinessID() throws SQLException {
	Connection con=getConnection();
	 System.out.println("connection string"+ con.toString()); 
	Statement st=null;
	ResultSet rs=null;
	try{
	st=con.createStatement();
	rs=st.executeQuery("SELECT * FROM test.Customber where ID=1");
	rs.next();
 String strname=rs.getString("Name");
 	System.out.println("Employee name is "+strname);
	}
	finally
	{
		if(st!=null)
			try {
				st.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		if(rs!=null)
			try {
				rs.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		if(con!=null)
		{
			con.close();
		}
	}
	return BusinessID;
}

public void setBusinessID(String businessID) {
	BusinessID = businessID;
	
}

public static Connection getConnection()
{
	Connection con=null;
	try{
		Class.forName("com.mysql.jdbc.Driver");
		con=DriverManager.getConnection("jdbc:mysql://localhost:3306/test","root","install");
	}
	catch (SQLException ex)
	{
	System.out.println("Hello ");
		ex.printStackTrace();
	} catch (ClassNotFoundException e) {
		System.out.println("rohit ");
		e.printStackTrace();
	}
	return con;
}

public void InsertIntoDataBase(String [] arr) throws SQLException
{
	Connection con=getConnection();
	Statement st=null;
	ResultSet rs=null;
	try
	{
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		   //get current date time with Date()
		   Date date = new Date();
		   System.out.println("todays date "+dateFormat.format(date));
		   String todaysDate=dateFormat.format(date);
		for(int i=0;i<arr.length;i++)
		{
			System.out.println("arr "+i+" "+arr[i]);
		}
		   
		st=con.createStatement();
		
		String query="insert into Business_Ratings values ('"+arr[0]+"','"+arr[1]+"',"+arr[2]+","+arr[3]+","+arr[4]+",'"+todaysDate+"');";
		System.out.println("query "+query);
		st.executeUpdate(query);
	}
	finally
	{
		if(st!=null)
			try {
				st.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		if(rs!=null)
			try {
				rs.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		if(con!=null)
		{
			con.close();
		}
		
	}
}

}
