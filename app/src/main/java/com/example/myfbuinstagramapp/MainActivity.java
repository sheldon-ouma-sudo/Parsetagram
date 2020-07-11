package com.example.myfbuinstagramapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.myfbuinstagramapp.fragments.ProfileFragments;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.io.File;
import java.util.List;

import fragments.ComposeFragment;
import fragments.PostFragment;

import static android.widget.Toast.makeText;

public class MainActivity extends AppCompatActivity {
    final FragmentManager fragmentManager = getSupportFragmentManager();
    public static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE =42;
    public static final String TAG  = "MainActivitiy";
    private EditText etDescription;
    private Button btnSubmit;
    private ImageView ivPostImage;
    private Button btnCaptureImage;
    public String photoFileName = "photo.jpg";
    private File photoFile;
    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Find the toolbar view inside the activity layout
        Toolbar toolbar = (Toolbar) findViewById(R.id.topToolbar);
        // Sets the Toolbar to act as the ActionBar for this Activity window.
        // Make sure the toolbar exists in the activity and is not null
        setSupportActionBar(toolbar);

        etDescription = findViewById(R.id.etDescriprition);
        btnSubmit = findViewById(R.id.btnSubmit);
        ivPostImage = findViewById(R.id.ivPostImage);
        //btnCaptureImage = findViewById(R.id.btnCaptureImage);
        bottomNavigationView = findViewById(R.id.bottomNavigation);

        //we want a situation when one clicks on the capture image button they are able to upload an image
        //we are setting an onclick listener on the capture image button
        /*
        btnCaptureImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //we have to launch the camera
                launchCamera();

            }
        });

         */
        //queryPost();
        //Question: does this mean that when the user  clicks on the button he is taken to the new view or intent
        //when the user clicks on the button we want to collect all information and create a post from that
        /*
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String descripiton = etDescription.getText().toString();
                if(descripiton.isEmpty()){
                    makeText(MainActivity.this,"Description is empty", Toast.LENGTH_SHORT).show();
                 return;
                }
                ParseUser currentUser = ParseUser.getCurrentUser();
                //Question:why are calling this function though

                //error checking
                if(photoFile == null || ivPostImage.getDrawable() == null){
                    return;
                }
                savePost(descripiton,currentUser, photoFile);
            }
        });
        */
        //Adding a listener to the bottomNavigation view
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
            Fragment fragment = null;
            switch (menuItem.getItemId()) {
             case R.id.action_home :
                    // do something here
                    fragment = new PostFragment();
                    break;
                case R.id.action_compose:
                    // do something here
                    fragment = new ComposeFragment();
                    break;
                case R.id.action_profile:
                    fragment= new ProfileFragments();
                default:
                    break;
            }
            fragmentManager.beginTransaction().replace(R.id.filContainer, fragment).commit();
            return true;
            }

        });
        // Set default selection
        bottomNavigationView.setSelectedItemId(R.id.action_home);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        // Handle clicks on toolbar items
        switch (item.getItemId()) {
            case R.id.miLogout:
                onLogoutAction();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void onLogoutAction() {
        ParseUser.logOutInBackground();
        Intent i = new Intent(this, LoginActivity.class);
        startActivity(i);
    }

    private void launchCamera() {

        // create Intent to take a picture and return control to the calling application
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Create a File reference for future access
        photoFile = getPhotoFileUri(photoFileName);

        // wrap File object into a content provider
        // required for API >= 24
        // See https://guides.codepath.com/android/Sharing-Content-with-Intents#sharing-files-with-api-24-or-higher
        Uri fileProvider = FileProvider.getUriForFile(MainActivity.this, "com.codepath.fileprovider", photoFile);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileProvider);

        // If you call startActivityForResult() using an intent that no app can handle, your app will crash.
        // So as long as the result is not null, it's safe to use the intent.
        if (intent.resolveActivity(getPackageManager()) != null) {
            // Start the image capture intent to take photo
            startActivityForResult(intent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
            //to have the image taken by the user we have to use the overiding method onActivity result
        }
    }
      //The method will be invoked when the child application returns to the parent application
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                // by this point we have the camera photo on disk
                Bitmap takenImage = BitmapFactory.decodeFile(photoFile.getAbsolutePath());
                // RESIZE BITMAP, see section below
                // Load the taken image into a preview
                ivPostImage.setImageBitmap(takenImage);
            } else { // Result was a failure
                makeText(this, "Picture wasn't taken!", Toast.LENGTH_SHORT).show();
            }

        }

    }


    // Returns the File for a photo stored on disk given the fileName
    public File getPhotoFileUri(String fileName) {
        // Get safe storage directory for photos
        // Use `getExternalFilesDir` on Context to access package-specific directories.
        // This way, we don't need to request external read/write runtime permissions.
        File mediaStorageDir = new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES), TAG);

        // Create the storage directory if it does not exist
        if (!mediaStorageDir.exists() && !mediaStorageDir.mkdirs()){
            Log.d(TAG, "failed to create directory");
        }

        // Return the file target for the photo based on filename

        return new File(mediaStorageDir.getPath() + File.separator + fileName);
    }





    private void savePost(String descripiton, ParseUser currentUser, File photoFile) {
        //Creating a new post
        Post post = new Post();
        //Now let's set different attributes on the post
        post.setDescription(descripiton);
        post.setImage(new ParseFile(photoFile));
        post.setUser(currentUser);
        //Once we have the post object we can now save it
        post.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
               if(e != null){
                   Log.e(TAG, "Error while saving", e);
                   makeText(MainActivity.this,"Error while saving", Toast.LENGTH_SHORT).show();
                return;
               }
              Log.i(TAG, "Post saved successfully", e);
               etDescription.setText("");
               ivPostImage.setImageResource(0);
            }

        });


    }

    private void queryPost() {
        //This is how we use the api presented by parse to get the data from the database
        ParseQuery<Post> query = ParseQuery.getQuery(Post.class);
        query.include(Post.KEY_USER);
        query.findInBackground(new FindCallback<Post>() {
            @Override
            public void done(List<Post> posts, ParseException e) {
                if (e == null) {
                    Log.e(TAG, "issue getting posts");
                   return;
                }
            for (Post post:posts){
                Log.i(TAG, "Posts:"+post.getDescription() + "username:" + post.getUser().getUsername());
            }
            }
        });
    }

}