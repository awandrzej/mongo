package com.tengen;

import java.net.UnknownHostException;
import java.util.ArrayList;

import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.Mongo;
import com.mongodb.MongoException;

public class Mongo3_1 {

	public static void main(String[] args) throws UnknownHostException,
			MongoException {

		Mongo mongo = new Mongo();

		DB db = mongo.getDB("school");

		System.out.println("Connected to database...");

		DBCollection students = db.getCollection("students");

		DBCursor cursor = students.find();

		while (cursor.hasNext()) {

			DBObject obj = cursor.next();
			BasicDBList scoresList = (BasicDBList) obj.get("scores");
			ArrayList<BasicDBObject> scoresArray = (ArrayList) scoresList;

			BasicDBObject toRemove = null;
			double min = 666;

			for (BasicDBObject score : scoresArray) {
				if (score.get("type").equals("homework")) {
					double thisScore = score.getDouble("score");
					if (thisScore < min) {
						toRemove = score;
						min = thisScore;
					}
				}
			}

			
			scoresArray.remove(toRemove);

			BasicDBObject findToUpdateQuery = new BasicDBObject("_id",
					obj.get("_id"));

			BasicDBObject updateQuery = new BasicDBObject("$set",
					new BasicDBObject("scores", scoresArray));

			System.out.println(students.update(findToUpdateQuery, updateQuery));

		}

		System.out.println("Done!");
	}
}