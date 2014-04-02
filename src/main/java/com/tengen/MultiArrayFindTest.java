/*
 * Copyright (c) 2008 - 2013 10gen, Inc. <http://10gen.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package com.tengen;

import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

import org.bson.BasicBSONObject;
import org.bson.types.BasicBSONList;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class MultiArrayFindTest {
	
	public static void main(String[] args) throws UnknownHostException {
		
		MongoClient client = new MongoClient();
        DB db = client.getDB("school");
        DBCollection collection = db.getCollection("students");

        System.out.println("Find one:");
        DBObject one = collection.findOne();
        System.out.println(one);

        System.out.println("\nFind all: ");
        DBCursor cursor = collection.find(new BasicDBObject("scores.type", "homework"));
        System.out.println(cursor.count());
        int i = 0; 
        try {
          while (cursor.hasNext()) {
        	  
              int id = (Integer) cursor.next().get("_id");
        	  
              BasicBSONList bl = (BasicBSONList) cursor.next().get("scores");
        	  for (Object bo : bl){
        		  
        		  BasicBSONObject bo1 = (BasicBSONObject) bo;
        		  if (bo1.containsValue("homework")){
        		  System.out.println(Integer.toString(id));
                  System.out.println(bo1.get("score"));
        		  }
        	  }
              

        	  
           }
        
        } finally {
            cursor.close();
        }

        System.out.println("\nCount:");
        long count = collection.count();
        System.out.println(count);
    }
}


/*
 * how to handle multiple json objects present in a file ? something like this

[{"NAME":"Alan","VALUE":12,"COLOR":"blue","DATE":"Sep. 25, 2009"},
{"NAME":"Shan","VALUE":13,"COLOR":"green\tblue","DATE":"Sep. 27, 2009"},
{"NAME":"John","VALUE":45,"COLOR":"orange","DATE":"Sep. 29, 2009"},
{"NAME":"Minna","VALUE":27,"COLOR":"teal","DATE":"Sep. 30, 2009"}]

How to parse such json file with this library.
 • Reply•Share › 
Avatar
Abhishek  Abhishek • 7 months ago
OK i figured it out. It's pretty simple

JSONArray jsonarray = (JSONArray)obj;

for (int i=0; i<jsonarray.size(); i++) {

JSONObject jsonObject= (JSONObject)jsonarray.get(i);
String name = (String) jsonObject.get("name");
System.out.println(name);
long age = (Long) jsonObject.get("age");
System.out.println(age);

// loop array
JSONArray msg = (JSONArray) jsonObject.get("messages");
Iterator iterator = msg.iterator();
while (iterator.hasNext()) {
System.out.println(iterator.next());	
}

}
 * 
 */


/*
 * 
 * i++;
        	  DBObject cur = cursor.next();
        	  int userId = (Integer) cursor.next().get("_id");
              BasicDBList c = (BasicDBList) cursor.next().get("scores");
              for (Object s : c) {
            	  //System.out.println(Integer.toString(userId));
            	  System.out.println(s.toString());
            	  */
