/*
 * Copyright 2014 Julian Shen
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.mj.libmagneto;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.net.MalformedURLException;
import java.net.URLDecoder;


public class MagnetURI {
   public Part dn = null;
   public Part xt = null;
   public int xl = -1;
   public Part as = null;
   public Part xs = null;
   public Part mt = null;
   public Part tr = null;

   protected static class Part {
       private List<String> _values = new ArrayList<String>();

       public String value() {
           if(_values.size() == 0) {
               return null;
           }

           return _values.get(0);
       }

       public String[] values() {
           String[] result = new String[_values.size()];

           _values.toArray(result);
           return result;
       }

       public void addValue(String val) {
           _values.add(val);
       }
   }

   public static MagnetURI parse(String uriStr) throws MalformedURLException {
       if(!uriStr.startsWith("magnet:?")) {
           throw new MalformedURLException("Wrong scheme.");
       }

       MagnetURI magnetUri = new MagnetURI();

       Map<String, Part> parts = new HashMap<String, Part>();
       String queryStr = uriStr.substring(8);

       String[] queries = queryStr.split("&");
       for(String q:queries) {
           String[] v = q.split("=");
           String key = v[0];
           String val = v[1];

           if(key.length() > 2 && key.charAt('2') != '.') {
               continue;
           }

           key = key.substring(0,2);

           if("xl".equals(key)) {
               try {
                    magnetUri.xl = Integer.parseInt(val);
               } catch(NumberFormatException e) {
               }
           } else {
               Part part = parts.get(key);

               if(part == null) {
                   part = new Part();
                   parts.put(key, part);
               }
               part.addValue(URLDecoder.decode(val));
           }
       }

       magnetUri.dn = parts.get("dn");
       magnetUri.dn = assignDummyIfNull(magnetUri.dn);
       magnetUri.xt = parts.get("xt");
       magnetUri.xt = assignDummyIfNull(magnetUri.xt);
       magnetUri.as = parts.get("as");
       magnetUri.as = assignDummyIfNull(magnetUri.as);
       magnetUri.xs = parts.get("xs");
       magnetUri.xs = assignDummyIfNull(magnetUri.xs);
       magnetUri.mt = parts.get("mt");
       magnetUri.mt = assignDummyIfNull(magnetUri.mt);
       magnetUri.tr = parts.get("tr");
       magnetUri.tr = assignDummyIfNull(magnetUri.tr);

       return magnetUri;
   }

   private static Part assignDummyIfNull(Part p) {
     if(p == null) {
       return new Part();
     }

     return p;
   }
}
