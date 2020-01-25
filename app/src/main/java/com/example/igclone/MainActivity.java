package com.example.igclone;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText edtSPemail, edtSPusername, edtSPpassword;
    private Button btnSPsignup, btnSPlogin;
    private TextView txtLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setTitle("Sign Up");

        edtSPemail = findViewById(R.id.edtSPemail);
        edtSPusername = findViewById(R.id.edtSPusername);

        edtSPpassword = findViewById(R.id.edtSPpassword);
        edtSPpassword.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int keyCode, KeyEvent event) {

                if (keyCode == KeyEvent.KEYCODE_ENTER &&
                        event.getAction() == KeyEvent.ACTION_DOWN) {

                    onClick(btnSPsignup);
                }
                return false;
            }
        });


        btnSPsignup = findViewById(R.id.btnSPsignup);
        txtLogin = findViewById(R.id.txtLogin);
        //  btnSPlogin = findViewById(R.id.btnSPlogin);

        btnSPsignup.setOnClickListener(this);
        txtLogin.setOnClickListener(this);
        //btnSPlogin.setOnClickListener(this);

        if (ParseUser.getCurrentUser() != null) { //TOKEN SESSION
           // ParseUser.getCurrentUser().logOut();
            TransitionToSocialMediaActivty();
        }


    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.btnSPsignup:
                if (edtSPemail.getText().toString().equals("")
                        || edtSPusername.getText().toString().equals("")
                        || edtSPpassword.getText().toString().equals("")) {

                    Toast.makeText(MainActivity.this, "Email,Username and Password is required !", Toast.LENGTH_SHORT).show();



                } else {
                    final ParseUser appuser = new ParseUser();
                    appuser.setEmail(edtSPemail.getText().toString());
                    appuser.setUsername(edtSPusername.getText().toString());
                    appuser.setPassword(edtSPpassword.getText().toString());

                    final ProgressDialog progressDialog = new ProgressDialog(this); //progress dialog for signing up user
                    progressDialog.setMessage("Signing up " + edtSPusername.getText().toString());
                    progressDialog.show();

                    appuser.signUpInBackground(new SignUpCallback() {
                        @Override
                        public void done(ParseException e) {
                            if (e == null)
                            {
                                Toast.makeText(MainActivity.this, appuser.getUsername() + " is signed up", Toast.LENGTH_LONG).show();

                                TransitionToSocialMediaActivty();

                            } else {
                                Toast.makeText(MainActivity.this, "An error Occured " + e.getMessage(), Toast.LENGTH_LONG).show();
                            }
                            progressDialog.dismiss();
                        }
                    });
                }
                break;
            case R.id.txtLogin:

                Intent intent = new Intent(MainActivity.this, Login.class);
                startActivity(intent);
                finish();
                break;
        }

    }

    public void rootLayoutTapped(View view) {

        try {

            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }catch (Exception e){

            e.printStackTrace();
        }

    }

    public void TransitionToSocialMediaActivty(){

        Intent intent = new Intent(MainActivity.this,SocialMedia.class);
        startActivity(intent);
        finish();
    }
}
