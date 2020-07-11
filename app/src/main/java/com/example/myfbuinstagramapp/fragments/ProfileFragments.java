

package com.example.myfbuinstagramapp.fragments;

import android.util.Log;

import com.example.myfbuinstagramapp.Post;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.List;

import fragments.PostFragment;

public class ProfileFragments  extends PostFragment {

    @Override
    protected void queryPost() {
        ParseQuery<Post> query = ParseQuery.getQuery(Post.class);
        query.include(Post.KEY_USER);
        query.whereEqualTo(Post.KEY_CREATED_KEY, ParseUser.getCurrentUser());
        query.setLimit(200);
        query.addDescendingOrder(Post.KEY_CREATED_KEY);
        query.findInBackground(new FindCallback<Post>() {
            @Override
            public void done(List<Post> posts, ParseException e) {
                if (e == null) {
                    Log.e(TAG, "issue getting posts");
                    return;
                }
                for (Post post:posts){
                    Log.i(TAG, "Posts:"+post.getDescription() + "username:" + post.getUser().getUsername());
                    allPosts.addAll(posts);
                    adapter.notifyDataSetChanged();

                }
            }
        });
    }
}
//ADD A LOGOUT BUTTON
//REFFERENCE IT ON THE PROFILE FRAGMENT SETONCLICKLISTENER ON IT AND CALL ON LOG OUT ON IT
//log