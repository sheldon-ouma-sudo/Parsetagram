package com.example.myfbuinstagramapp;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.parse.ParseFile;

import java.util.List;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.ViewHolder> {
    //Create a constructor and use it with its parameters to fill in the methods


    //Have the constructor for have two parameters
    private Context context;
    private List<Post> posts;

    public PostAdapter(Context context, List<Post> posts) {
        this.context = context;
        this.posts = posts;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //uses the Layout Inflater which we are getting from the context
        //we are going to inflate the layout file we have just found
        //this will return to us a view
        View view = LayoutInflater.from(context).inflate(R.layout.item_post, parent, false);
        //Let us wrap the view inside the viewholder

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        //first we have to get the post located at this position and this returns to us a post
        Post post = posts.get(position);
        //now we taking the viewHolder past in and bind the post to that viewholder
        // we do by creating a bind method
        holder.bind(post);

    }

    @Override
    public int getItemCount() {
        return posts.size();
    }

    public void clear() {
        posts.clear();
        notifyDataSetChanged();
    }
    /* Within the RecyclerView.Adapter class */

    // Clean all elements of the recycler
    // Add a list of items -- change to type used
    public void addAll(List<Post> postsLists) {
        posts.addAll(postsLists);
        notifyDataSetChanged();
    }

    //define  a viewHolder
//Add a constructor
//Pramatize the viewHolder and reference it on the public class
    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView tvUserName;
        private ImageView ivImage;
        private TextView tvDescription;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            //what we have to do in the constructor of the viewholder is to identify the views according to the ID passed
            tvUserName = itemView.findViewById(R.id.tvUserName);
            ivImage = itemView.findViewById(R.id.ivImage);
            tvDescription = itemView.findViewById(R.id.tvDescription);


            itemView.setOnClickListener(this);
        }



        //Here we are binding the data coming from the post into the view elements
        //in order to do that we have to get access to the viewelements we defined inside the itempost
        public void bind(Post post) {
            //take the data from the post and bind to the views that we have identified
            //WE will grab the description attribute from the post.
            tvDescription.setText(post.getDescription());
            tvUserName.setText(post.getUser().getUsername());
            //we have to load in the image in the ivImageview
            //the way we do this is by using glide

            //however let's check if the post has a valid image
            ParseFile image = post.getImage();
            if (image != null) {
                Glide.with(context).load(post.getImage().getUrl()).into(ivImage);
            }
        }

        @Override
        public void onClick(View view) {
            int position = getAdapterPosition(); // gets item position
            Post post = posts.get(position);
            Intent i = new Intent(context, DetailActivity.class);
            i.putExtra(Post.class.getSimpleName(), post);
            context.startActivity(i);
        }
    }
}