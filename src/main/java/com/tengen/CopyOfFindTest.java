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

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;

import java.net.UnknownHostException;
import java.util.Random;

public class CopyOfFindTest {
	
	public static void main(String[] args) throws UnknownHostException {
		
		MongoClient client = new MongoClient();
        DB db = client.getDB("students");
        DBCollection collection = db.getCollection("grades");

        System.out.println("Find one:");
        DBObject one = collection.findOne();
        System.out.println(one);

        System.out.println("\nFind all: ");
        DBCursor cursor = collection.find(new BasicDBObject("type", "homework")).sort(new BasicDBObject("student_id",1).append("score", 1));
        System.out.println(cursor.count());
        int i = 0; 
        try {
          while (cursor.hasNext()) {
        	  i++;
        	  DBObject cur = cursor.next();
              System.out.println(cur.get("_id") +" -->" +cur.toMap().toString()  +"-> " +Integer.toString(i) );
              if (i>=2){
            	  i =0;
              } else if (i==1){
                //  collection.remove(new BasicDBObject("_id", cur.get("_id")));
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
