package com.example.myfbuinstagramapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.parse.LogInCallback;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

import java.text.ParseException;

public class SignUpActivity extends AppCompatActivity {

    private TextView tvPassword;
    private TextView tvUserName;
    private Button btnSignUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        // Create the ParseUser
        final ParseUser user = new ParseUser();

        // Set core properties
        tvUserName = findViewById(R.id.tvDescription);
        tvPassword = findViewById(R.id.tvPassword);
        btnSignUp = findViewById(R.id.btnSignUp);

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = tvUserName.getText().toString();
                String password = tvPassword.getText().toString();
                signUpUser(username, password);
// Set custom properties
                user.put("phone", "650-253-0000");
// Invoke signUpInBackground
                user.signUpInBackground(new SignUpCallback() {
                    @Override
                    public void done(com.parse.ParseException e) {

                    }

                    public void done(ParseException e) {
                        if (e == null) {
                            // Hooray! Let them use the app now.
                        } else {
                            // Sign up didn't succeed. Look at the ParseException
                            // to figure out what went wrong
                        }
                    }
                });


            }

        });
    }

    private void signUpUser(String username, String password) {
        ParseUser newUser = new ParseUser();
        newUser.setUsername(username);
        newUser.setPassword(password);
        newUser.signUpInBackground(new SignUpCallback() {
            @Override
            public void done(com.parse.ParseException e) {
                if (e == null) {
                    // Hooray! Let them use the app now.
                    finish();
                } else {
                    // Sign up didn't succeed. Look at the ParseException
                    // to figure out what went wrong
                    Toast.makeText(SignUpActivity.this, "Oh no! An error occurred.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}






//Set up the xml with sign up buttons, and passwords and usernames
//

/*
public class LoginActivity extends AppCompatActivity {
    public static final String TAG = "LoginActivity";
    //Add a new button for the sign up
    private EditText etUsername;
    private EditText etPassword;
    private Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //Let's check if the user is already logged in
        if (ParseUser.getCurrentSessionToken() != null) {
            //let us go to the main activity
            goMainActivity();
        }
        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);
// Another question, do we have to refer this  so that the login activity could show or why do we have this here
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i(TAG, "onClick login button");
                String username = etUsername.getText().toString();
                String password = etPassword.getText().toString();
                loginUser(username, password);

            }

            private void loginUser(String username, String password) {
                Log.i(TAG, "Attempting to login user " + username + "Password: " + password);
                ParseUser.logInInBackground(username, password, new LogInCallback() {
                    @Override
                    public void done(ParseUser user, com.parse.ParseException e) {
                        if (e != null) {
                            Log.e(TAG, "Issue with login");//what of wrong passwords keyed in?
                            Toast.makeText(LoginActivity.this, "Issue with login", Toast.LENGTH_SHORT);
                            return;
                        }
                        //if the login succeeded navigate to the main activity
                        goMainActivity();
                        Toast.makeText(LoginActivity.this, "Success", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

    }

    private void goMainActivity() {
        //this is the context which is best because this is reffering to the activity and activity is an instance of a contest followed by which class we want to navigate to
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
        finish();
    }
}

//have an intent that takes them to the sign up
//just have another button for sign up and when they click on the button it takes them to the sign activity
*/

