package it.tabaccaiofurbo.com.tabaccaiofurboapp.activities;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import it.tabaccaiofurbo.com.tabaccaiofurboapp.R;
import it.tabaccaiofurbo.com.tabaccaiofurboapp.fragments.TabFragment;
import it.tabaccaiofurbo.com.tabaccaiofurboapp.fragments.ViewFragment;
import it.tabaccaiofurbo.com.tabaccaiofurboapp.utils.Constants;

public class MainActivity extends AppCompatActivity implements DialogInterface.OnClickListener{

    private Toolbar toolbar;
    private DrawerLayout mDrawerLayout;
    private Context mContext;
    private String user;
    private static final int REQUEST_PHONE_CALL = 1;
    private Typeface typeface;
    private int itemID;
    private FragmentTransaction fragmentTransaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeAsUpIndicator(R.mipmap.ic_menu_white_24dp);
        actionBar.setDisplayHomeAsUpEnabled(true);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mContext = this.getApplicationContext();

        NavigationView navigationView = (NavigationView) findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                menuItem.setChecked(true);
                mDrawerLayout.closeDrawers();
                setItemID(menuItem.getItemId());
                //Check to see which item was being clicked and perform appropriate action
                switch (menuItem.getItemId()) {
                    case R.id.item_load_data:
                        TabFragment tabFragment = new TabFragment();
                        tabFragment.setId(1);
                        fragmentTransaction = getSupportFragmentManager().beginTransaction();
                        fragmentTransaction.setCustomAnimations(R.anim.enter_from_left,R.anim.exit_to_left);
                        fragmentTransaction.replace(R.id.content_frame,tabFragment);
                        fragmentTransaction.commit();
                        Snackbar.make(mDrawerLayout,getResources().getString(R.string.menu_load_data),Snackbar.LENGTH_LONG).show();
                        return true;
                    case R.id.item_create_file:
                        Snackbar.make(mDrawerLayout,getResources().getString(R.string.menu_create_file),Snackbar.LENGTH_LONG).show();
                        return true;
                    case R.id.item_create_file_new:
                        Snackbar.make(mDrawerLayout,getResources().getString(R.string.menu_create_file_new),Snackbar.LENGTH_LONG).show();
                        return true;
                    case R.id.item_la_ciana:
                        requestBrowserOpening(menuItem.getItemId());
                        return true;
                    case R.id.item_view:
                        //Task per query a DB che reperisce le 3 liste di prodotti:
                        //- IN CARICO
                        //- IN STORICO
                        //- MAI ORDINATI
                        //Al termine, dati 3 JSON salva i dati in 3 array di oggetti "Prodotto"
                        //con le liste di oggetti, in base alla TAB scelta, si popola la relativa recycleview
                        ViewFragment viewFragment = new ViewFragment();
                        fragmentTransaction = getSupportFragmentManager().beginTransaction();
                        fragmentTransaction.setCustomAnimations(R.anim.push_up_in,R.anim.push_up_out);
                        fragmentTransaction.replace(R.id.content_frame,viewFragment);
                        fragmentTransaction.commit();
                        //PREPARAZIONE FRAGMENT
                        //CHIAMATA A FRAGMENT MANAGER
                        Snackbar.make(mDrawerLayout,getResources().getString(R.string.menu_view),Snackbar.LENGTH_LONG).show();
                        return true;
                    case R.id.item_forward:
                        requestBrowserOpening(menuItem.getItemId());
                        return true;
                    case R.id.item_how_to:
                        Snackbar.make(mDrawerLayout,getResources().getString(R.string.menu_how_to),Snackbar.LENGTH_LONG).show();
                        return true;
                    case R.id.item_send_mail:
                        Intent sendMail = new Intent(Intent.ACTION_SEND);
                        sendMail.putExtra(Intent.EXTRA_EMAIL,new String[] {"postmaster@tabaccaiofurbo.it"});
                        sendMail.setType("plain/text");
                        startActivity(Intent.createChooser(sendMail, "Scegli un'applicazione per l'invio"));
                        return true;
                    case R.id.item_help:
                        openOKCancelDialog(getResources().getString(R.string.menu_help),getResources().getString(R.string.request_start_call));
                        return true;
                }
                return false;
            }
        });

        Bundle extras = getIntent().getExtras();
        if(extras!= null)
            user = this.getIntent().getExtras().getString("username");

        TextView tvDrawerTitle = (TextView) navigationView.getHeaderView(0).findViewById(R.id.drawer_title);
        typeface = Typeface.createFromAsset(getAssets(),"fonts/Neuropolitical.ttf");
        tvDrawerTitle.setTypeface(typeface);
        TextView tvDrawerUserInfo  = (TextView) navigationView.getHeaderView(0).findViewById(R.id.drawer_user_info);
        typeface = Typeface.createFromAsset(getAssets(),"fonts/Newtown_Italic.ttf");
        tvDrawerUserInfo.setTypeface(typeface);
        tvDrawerUserInfo.setText(getResources().getString(R.string.string_user)+": "+user);
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public void onBackPressed() {
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
            case R.id.action_settings:
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void requestCallPermission(){
        if (ActivityCompat.shouldShowRequestPermissionRationale(this,Manifest.permission.CALL_PHONE)) {
            Snackbar.make(mDrawerLayout, R.string.permission_phone_rationale,
                Snackbar.LENGTH_LONG).setAction(R.string.string_accept, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.CALL_PHONE},REQUEST_PHONE_CALL);

                    }
                })
                .show();
        } else {
            // Call permission has not been granted yet. Request it directly.

            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CALL_PHONE},
                    REQUEST_PHONE_CALL);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,@NonNull String permissions[], @NonNull int[] grantResults) {
        if(requestCode==REQUEST_PHONE_CALL) {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    startHelpCall();
                } else {
                    Snackbar.make(mDrawerLayout, R.string.request_modify_permissions,
                            Snackbar.LENGTH_LONG)
                            .setAction(R.string.string_accept, new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            openAppSettings(MainActivity.this);
                        }
                    }).show();
                }
                return;
        }
        else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    private void requestBrowserOpening(int item){
        this.itemID = item;
        Snackbar.make(mDrawerLayout, R.string.request_open_browser,
                Snackbar.LENGTH_LONG).setAction(R.string.string_accept, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch(itemID){
                    case R.id.item_la_ciana:
                        openUrl(Constants.URL_CIANA);
                        break;
                    case R.id.item_forward:
                        openUrl(Constants.URL_LOGISTA);
                        break;
                }

            }
        })
                .show();
    }

    private void startHelpCall(){
        try {
            Intent helpCall = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + Constants.HELP_PHONE_NUMBER));
            startActivity(helpCall);
        }  catch(SecurityException e ){
            Snackbar.make(mDrawerLayout, R.string.permission_phone_denied,Snackbar.LENGTH_SHORT).show();
        }
    }

    private static void openAppSettings(final Activity context) {
        if (context == null) {
            return;
        }
        final Intent i = new Intent();
        i.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        i.addCategory(Intent.CATEGORY_DEFAULT);
        i.setData(Uri.parse("package:" + context.getPackageName()));
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        i.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        i.addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
        context.startActivity(i);
    }

    private void openUrl(String url){
        Uri uri = Uri.parse(url);
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(intent);

    }

    private void openOKCancelDialog(String title, String message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.AppCompatAlertDialogStyle);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setPositiveButton("OK", this);
        builder.setNegativeButton("Cancel", this);
        builder.show();
    }

    public void onClick(DialogInterface dialog, int which){
        switch(itemID){
            case R.id.item_help:
                switch(which) {
                    //OK
                    case -1:
                        requestCallPermission();
                        break;
                    //CANCEL
                    case -2:
                        dialog.dismiss();
                        break;
                }
                break;
            default:
                break;
        }
    }

    public void setItemID(int itemID){
        this.itemID = itemID;
    }
}
