package com.example.myfbuinstagramapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class DetailActivity extends AppCompatActivity {

    Post post;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ImageView ivPostImage = findViewById(R.id.ivPostImage);
        TextView tvUsername = findViewById(R.id.tvUserName);
        TextView tvDescription = findViewById(R.id.tvDescription);

        post = getIntent().getParcelableExtra(Post.class.getSimpleName());

        Glide.with(this).load(post.getImage().getUrl()).into(ivPostImage);
        tvUsername.setText(post.getUser().getUsername());
        tvDescription.setText(post.getDescription());
    }
}