package com.emp.dao;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.MongoClient;

public class MongoDbCollection {
	@SuppressWarnings({ "resource", "deprecation" })
	public static DBCollection getCollection() {
		MongoClient mongoClient = new MongoClient("localhost", 27017);
		DB db = mongoClient.getDB("capgemini");
		DBCollection table = db.getCollection("emp");
		return table;
	}
}