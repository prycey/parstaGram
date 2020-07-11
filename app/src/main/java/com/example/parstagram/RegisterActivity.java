package com.example.parstagram;

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
import com.parse.SignUpCallback;

public class RegisterActivity extends AppCompatActivity {
    public static final String TAG = "RegisterActivity";
    private EditText rUsername;
    private EditText rPassword;
    private Button rButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        rUsername = findViewById(R.id.rUsername);
        rButton = findViewById(R.id.rButton);
        rPassword = findViewById(R.id.rPassword);

        rButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = rUsername.getText().toString();
                String password = rPassword.getText().toString();
                registerUser(username, password);
            }
        });

    }

    private void registerUser(final String username, final String password) {

        ParseUser user = new ParseUser();
// Set core properties
        user.setUsername(username);
        user.setPassword(password);


        user.signUpInBackground(new SignUpCallback() {
            public void done(ParseException e) {
                if (e == null) {
                    ParseUser.logInInBackground(username, password, new LogInCallback() {
                        public void done(ParseUser user, ParseException e) {
                            if (user != null) {
                                // Hooray! The user is logged in.
                                goMainActivity();
                            } else {
                                // Signup failed. Look at the ParseException to see what happened.
                            }
                        }
                    });
                } else {
                    // Sign up didn't succeed. Look at the ParseException
                    // to figure out what went wrong
                }
            }
        });

    }

    private void goMainActivity() {
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);

    }
}