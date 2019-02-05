package org.kidzonshock.acase.acase.Client;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

import org.kidzonshock.acase.acase.Models.PreferenceDataClient;
import org.kidzonshock.acase.acase.R;

public class ClientNavigation extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    Toolbar toolbar;
    private String client_id,first_name,last_name,email,profile_pic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_navigation);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        client_id = PreferenceDataClient.getLoggedInClientid(ClientNavigation.this);
        first_name = PreferenceDataClient.getLoggedInFirstname(ClientNavigation.this);
        last_name = PreferenceDataClient.getLoggedInLastname(ClientNavigation.this);
        email = PreferenceDataClient.getLoggedInEmail(ClientNavigation.this);
        profile_pic = PreferenceDataClient.getLoggedInProfilePicture(ClientNavigation.this);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        View headerLayout = navigationView.getHeaderView(0);

        ImageView nav_client_pic = headerLayout.findViewById(R.id.nav_client_pic);
        RequestOptions options = new RequestOptions()
                .circleCrop()
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true)
                .placeholder(R.mipmap.ic_launcher_round)
                .error(R.mipmap.ic_launcher_round);

        Glide.with(this).load(profile_pic).apply(options).into(nav_client_pic);

        TextView tvName  = headerLayout.findViewById(R.id.nav_client_name);
        tvName.setText(first_name + " " + last_name);
        TextView tvEmail = headerLayout.findViewById(R.id.nav_client_email);
        tvEmail.setText(email);
        TextView tvId = headerLayout.findViewById(R.id.nav_client_id);
        tvId.setText("ID: "+client_id);

        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    protected void onResume() {
        client_id = PreferenceDataClient.getLoggedInClientid(ClientNavigation.this);
        first_name = PreferenceDataClient.getLoggedInFirstname(ClientNavigation.this);
        last_name = PreferenceDataClient.getLoggedInLastname(ClientNavigation.this);
        email = PreferenceDataClient.getLoggedInEmail(ClientNavigation.this);
        profile_pic = PreferenceDataClient.getLoggedInProfilePicture(ClientNavigation.this);
        super.onResume();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            Intent a = new Intent(Intent.ACTION_MAIN);
            a.addCategory(Intent.CATEGORY_HOME);
            a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(a);
        }
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.client_navigation, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        Fragment fragment = null;
        if (id == R.id.nav_mycase_client) {
            toolbar.setTitle("My Case");
            // Handle the camera action
        } else if (id == R.id.nav_myaccount_client) {
            toolbar.setTitle("My Account");
            fragment = new AccountFragment();
        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_signout_client) {
            PreferenceDataClient.setUserLoggedInStatus(ClientNavigation.this,false);
            PreferenceDataClient.clearLoggedInClient(ClientNavigation.this);
            this.finish();
        }

        if(fragment != null){
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction ft = fragmentManager.beginTransaction();

            ft.replace(R.id.screen_area_client, fragment);
            ft.commit();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
