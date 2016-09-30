package it.tabaccaiofurbo.com.tabaccaiofurboapp.activities;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.net.HttpURLConnection;

import it.tabaccaiofurbo.com.tabaccaiofurboapp.R;
import it.tabaccaiofurbo.com.tabaccaiofurboapp.async.LoginTask;
import it.tabaccaiofurbo.com.tabaccaiofurboapp.utils.Constants;


public class LoginActivity extends AppCompatActivity implements View.OnClickListener{
    private static final String TAG = "LoginActivity";
    private EditText editUserID;
    private EditText editPassword;
    private Button buttonReset;
    private Button buttonLogin;
    private ProgressBar progressBar;
    private LinearLayout mLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mLayout = (LinearLayout) findViewById(R.id.login_activity_layout);

        TextView tvTitle = (TextView) findViewById(R.id.textViewTitle);
        Typeface typeFace = Typeface.createFromAsset(getAssets(),"fonts/Neuropolitical.ttf");
        tvTitle.setTypeface(typeFace);

        editUserID = (EditText) findViewById(R.id.editUserID);
        editPassword = (EditText) findViewById(R.id.editPassword);
        buttonLogin = (Button) findViewById(R.id.buttonLogin);
        buttonLogin.setOnClickListener(this);
        buttonReset = (Button) findViewById(R.id.buttonReset);
        buttonReset.setOnClickListener(this);
        progressBar = (ProgressBar) findViewById(R.id.loginProgresBar);
    }

    public void onClick(View v){
        String user;
        String password;
        String conn_url;
        switch(v.getId()){
            case R.id.buttonReset:

                if(!editUserID.getText().toString().isEmpty() || !editPassword.getText().toString().isEmpty()) {
                    editUserID.setText("");
                    editPassword.setText("");
                    progressBar.setVisibility(View.GONE);
                }
                break;
            case R.id.buttonLogin:
                if(checkLogData()) {
                    progressBar.setVisibility(View.VISIBLE);
                    conn_url = Constants.LOGIN_URL;
                    user = editUserID.getText().toString();
                    password = editPassword.getText().toString();
                    doLogin(conn_url, user, password);
                }else{
                    Snackbar.make(mLayout,getResources().getString(R.string.error_credentials),Snackbar.LENGTH_LONG).show();
                }
                break;
        }
    }

    private void doLogin(String conn_url, String user,String password){

        LoginTask loginTask = new LoginTask(this, progressBar);
        loginTask.execute(conn_url,user,password);
    }

    private boolean checkLogData(){
        if(!editUserID.getText().toString().isEmpty() && !editPassword.getText().toString().isEmpty())
            return true;
        else {
            return false;
        }
    }

    public void loggedIn(String user){
        // Lancio Activity main
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        intent.putExtra("username",user);
        startActivity(intent);
    }

    public void loginError(int errorCode){
        String msg = "";
        switch (errorCode) {

            case HttpURLConnection.HTTP_INTERNAL_ERROR:
                msg = getResources().getString(R.string.error_http_500) + ": ";
                break;
            default:
                break;
        }
        msg += errorCode;

        Snackbar.make(mLayout,getResources().getString(R.string.error_login_failed)+" "+ msg,Snackbar.LENGTH_LONG).show();
    }
}

