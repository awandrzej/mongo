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

public class FindUsers {
    public static void main(String[] args) throws UnknownHostException {
        MongoClient client = new MongoClient();
        DB db = client.getDB("blog");
        DBCollection collection = db.getCollection("users");
        //collection.drop();

        // insert 10 documents with a random integer as the value of field "x"
        
        System.out.println("\nFind all: ");
        DBCursor cursor = collection.find(new BasicDBObject("_id" , "andrzej1"));
        
        
        try {
          while (cursor.hasNext()) {
        	  DBObject cur = cursor.next();
        	  System.out.println(cur.get("_id"));
                       }
        } finally {
            cursor.close();
        }

        System.out.println("\nCount:");
        long count = collection.count();
        System.out.println(count);
    }
}
