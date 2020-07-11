package com.example.myfbuinstagramapp;


import com.parse.ParseClassName;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseUser;

@ParseClassName("Post")
public class Post extends ParseObject {

        // Ensure that your subclass has a public default constructor

     public static final  String KEY_DESCRIPTION = "description";
     public static final  String KEY_IMAGE = "image";
     public static final  String KEY_USER = "description";
     public static final String KEY_CREATED_KEY= "createdAt";

      public String getDescription() {
          return getString("KEY_DESCRIPTION");
      }
          public void setDescription(String description) {
              put(KEY_DESCRIPTION, description);
          }


    public ParseFile  getImage() {
        return getParseFile("KEY_IMAGE");
    }
        public void setImage(ParseFile parsefile) {
            put(KEY_IMAGE, parsefile);
        }


    public ParseUser getUser() {
        return getParseUser("KEY_USER");
    }
    public void setUser(ParseUser user) {
        put(KEY_IMAGE,  user);
    }


}

