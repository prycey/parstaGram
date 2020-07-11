package com.example.parstagram;

import com.parse.ParseClassName;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseUser;


@ParseClassName("Comment")
public class Comment extends ParseObject {

    public static final String KEY_COMMENT = "Comment";
    public static final String KEY_USER = "User";
    public static final String KEY_CREATEDAT = "createdAt";
    public static final String KEY_POST = "Post";

    public String getComment() {
        return getString(KEY_COMMENT);
    }

    public void setComment(String description) {
        put(KEY_COMMENT, description);

    }

    public ParseUser getUser() {
        return getParseUser(KEY_USER);
    }

    public void setUser(ParseUser user) {
        put(KEY_USER, user);

    }

    public String getKeyCreatedat() {
        return getCreatedAt().toString();
    }
    public  Post getPost(){
        return (Post)getParseObject(KEY_POST);
    }
    public void setKeyPost(Post post){
        put(KEY_POST, post);
    }


}
