package com.example.myfbuinstagramapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;

public class LoginActivity extends AppCompatActivity {
    public static final String TAG = "LoginActivity";
    //Add a new button for the sign up
    private EditText etUsername;
    private EditText etPassword;
    private Button btnLogin;
    private Button btnSignup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);
        btnSignup = findViewById(R.id.btnSignup);

        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(LoginActivity.this, SignUpActivity.class);
                startActivity(i);
            }
        });

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
                    public void done(ParseUser user, ParseException e) {
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

    @Override
    protected void onResume() {
        super.onResume();

        //Let's check if the user is already logged in
        if (ParseUser.getCurrentSessionToken() != null) {
            //let us go to the main activity
            goMainActivity();
        }
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
