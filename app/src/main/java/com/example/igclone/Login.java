package com.example.igclone;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;

public class Login extends AppCompatActivity implements View.OnClickListener {

    private EditText edtLGemail, edtLGpassword;
    private Button btnLGlogin;
    private TextView txtSignUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        setTitle("Log In");
        edtLGemail = findViewById(R.id.edtLGemail);
        edtLGpassword = findViewById(R.id.edtLGpassword);
        edtLGpassword.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int keyCode, KeyEvent event) {

                if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN) {

                    onClick(btnLGlogin);
                }
                return false;
            }
        });
        btnLGlogin = findViewById(R.id.btnLGlogin);
        txtSignUp = findViewById(R.id.txtsignUp);

        btnLGlogin.setOnClickListener(this);
        txtSignUp.setOnClickListener(this);


        if (ParseUser.getCurrentUser() != null) { //TOKEN SESSION TO LOG OUT USER AFTER SIGN UP
            ParseUser.getCurrentUser().logOut();

        }


    }


    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.btnLGlogin:
                if (edtLGemail.getText().toString().equals("")
                        || edtLGpassword.getText().toString().equals("")) {

                    Toast.makeText(Login.this, "Email and Password Fields Required !", Toast.LENGTH_SHORT).show();

                } else {

                    ParseUser.logInInBackground(edtLGemail.getText().toString(),
                            edtLGpassword.getText().toString(),
                            new LogInCallback() {
                                @Override
                                public void done(ParseUser user, ParseException e) {

                                    if (user != null && e == null) {
                                        Toast.makeText(Login.this, user.getUsername() + " is logged in successfully", Toast.LENGTH_LONG).show();
                                        TransitionToSocialMediaActivty();
                                    }
                                }
                            });
                }
                break;
            case R.id.txtsignUp:
                Intent intent = new Intent(Login.this, MainActivity.class);
                startActivity(intent);
                break;
        }


    }
    public void LayoutTapped(View view){ //to hide keyboard..


        try {

            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }catch (Exception e){

            e.printStackTrace();
        }
    }

    public void TransitionToSocialMediaActivty(){

        Intent intent = new Intent(Login.this,SocialMedia.class);
        startActivity(intent);
    }


    }

