package com.capgemini.dto;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Vector;

public class ConnectionPool {
	private Vector<Connection> pool = new Vector<Connection>();
	private String driverClass = "com.mysql.jdbc.Driver";
	private String dbUrl = "jdbc:mysql://localhost:3306/capgemini?useSSL=false";
	private String userNM = "root";
	private String password = "root";
	private int pool_size = 20;
	
	//singleton class
	private static ConnectionPool instance = null;
	
	public static ConnectionPool getInstance() 
	throws Exception
	{
		if(instance==null){
			instance = new ConnectionPool();
		}
		return instance;
	}
	
	private ConnectionPool() 
	throws Exception
	{
		//1. Load the Driver
		Class.forName(driverClass);
		
		//2. Get the DB Connection via Driver
		for(int i=0; i<pool_size; i++) {
			Connection con = DriverManager.getConnection(dbUrl,userNM,password);
			pool.add(con);
		}
		
	}//End of Constructor
	
	public Connection getConnectionFromPool() 
	throws SQLException
	{		
		Connection con = null;
		if(pool.size()>0)
		{
			con = pool.get(0);
			pool.remove(0);
			
		}else{
			//if more than 20 users to connect the database that'll give one by one connection objects per time
			con = DriverManager.getConnection(dbUrl, userNM, password);
		}
		
		return con;
	}//End of getConnectionFromPool()
	
	// close all the connection objects at a time
	public void returnConnectionToPool(Connection con) 
	throws SQLException
	{
		if(pool.size()<=pool_size)
		{
			pool.add(con);
		}else{
			con.close();
		}	
	}//End of returnConnectionToPool()
}