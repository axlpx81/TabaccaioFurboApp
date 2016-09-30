package it.tabaccaiofurbo.com.tabaccaiofurboapp.async;

import android.os.AsyncTask;
import android.view.View;
import android.widget.ProgressBar;

import it.tabaccaiofurbo.com.tabaccaiofurboapp.activities.LoginActivity;
import it.tabaccaiofurbo.com.tabaccaiofurboapp.utils.Constants;

/**
 * Created by 87001187 on 26/09/2016.
 */
public class LoginTask extends AsyncTask<String, Integer, String>{

    private LoginActivity activity;
    private ProgressBar progressBar;
    private String username;
    public LoginTask(LoginActivity activity, ProgressBar progressBar) {
        this.activity = activity;
        this.progressBar = progressBar;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected String doInBackground(String... args)
    {
        this.username = args[1];
        return Constants.CONNECTION_OK;

    }

    @Override
    protected void onPostExecute(String result) {
        //SE TUTTO OK
        if(result.equals(Constants.CONNECTION_OK)) {
            progressBar.setVisibility(View.INVISIBLE);
            activity.loggedIn(this.username);
        }
        //ALTRIMENTI
        else{
            //activity.loginError(errorCode);
        }


    }
}
