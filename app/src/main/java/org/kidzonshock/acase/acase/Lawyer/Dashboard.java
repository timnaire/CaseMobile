package org.kidzonshock.acase.acase.Lawyer;

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
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

import org.kidzonshock.acase.acase.Interfaces.Case;
import org.kidzonshock.acase.acase.Models.CommonResponse;
import org.kidzonshock.acase.acase.Models.PreferenceDataLawyer;
import org.kidzonshock.acase.acase.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Dashboard extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    Toolbar toolbar;
    private String first_name,last_name,email,profile_pic,lawyer_id;
    final String TAG = "Dashboard";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        first_name = PreferenceDataLawyer.getLoggedInFirstname(Dashboard.this);
        last_name = PreferenceDataLawyer.getLoggedInLastname(Dashboard.this);
        email = PreferenceDataLawyer.getLoggedInEmail(Dashboard.this);
        profile_pic = PreferenceDataLawyer.getLoggedInProfilePicture(Dashboard.this);
        lawyer_id = PreferenceDataLawyer.getLoggedInLawyerid(Dashboard.this);

        Fragment fragment = new DashboardFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction ft = fragmentManager.beginTransaction();

        ft.replace(R.id.screen_area, fragment);
        ft.commit();

//        getPractice();
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        View headerLayout = navigationView.getHeaderView(0);

        ImageView ivProfilePic = headerLayout.findViewById(R.id.nav_profile_pic);
        RequestOptions options = new RequestOptions()
                .circleCrop()
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true)
                .placeholder(R.mipmap.ic_launcher_round)
                .error(R.mipmap.ic_launcher_round);

        Glide.with(this).load(profile_pic).apply(options).into(ivProfilePic);

        TextView tvName  = headerLayout.findViewById(R.id.nav_lawyer_name);
        tvName.setText(first_name + " " + last_name);
        TextView tvEmail = headerLayout.findViewById(R.id.nav_lawyer_email);
        tvEmail.setText(email);

        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setItemIconTintList(null);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Case.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Case service = retrofit.create(Case.class);

        Call<CommonResponse> commonResponseCall = service.notify(lawyer_id);
        commonResponseCall.enqueue(new Callback<CommonResponse>() {
            @Override
            public void onResponse(Call<CommonResponse> call, Response<CommonResponse> response) {
                CommonResponse resp = response.body();
                if(!resp.isError()){
                    Toast.makeText(Dashboard.this, resp.getMessage(), Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(Dashboard.this, resp.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<CommonResponse> call, Throwable t) {
                Log.d(TAG,"Error: "+t.getMessage());
            }
        });

    }

    @Override
    protected void onResume() {
        first_name = PreferenceDataLawyer.getLoggedInFirstname(Dashboard.this);
        last_name = PreferenceDataLawyer.getLoggedInLastname(Dashboard.this);
        email = PreferenceDataLawyer.getLoggedInEmail(Dashboard.this);
        profile_pic = PreferenceDataLawyer.getLoggedInProfilePicture(Dashboard.this);
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
//        getMenuInflater().inflate(R.menu.dashboard, menu);
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
        if (id == R.id.nav_dashboard) {

            toolbar.setTitle("Dashboard");
            fragment = new DashboardFragment();

        } else if (id == R.id.nav_mycase) {

            toolbar.setTitle("My Case");
            fragment = new MyCaseFragment();

        } else if (id == R.id.nav_clients) {

            toolbar.setTitle("My Client");
            fragment = new ClientFragment();

        }
        else if (id == R.id.nav_account) {

            toolbar.setTitle("My Account");
//            Bundle info = new Bundle();
//            info.putStringArray("law_practice",law_practice);
            fragment = new AccountFragment();
//            fragment.setArguments(info);

        } else if (id == R.id.nav_events) {

            toolbar.setTitle("My Events");
//            Bundle info = new Bundle();
//            info.putStringArray("law_practice",law_practice);
            fragment = new EventFragment();
//            fragment.setArguments(info);

        } else if (id == R.id.nav_subscription) {

            toolbar.setTitle("Subscription");

        } else if (id == R.id.nav_signout) {
            PreferenceDataLawyer.setUserLoggedInStatus(Dashboard.this,false);
            PreferenceDataLawyer.clearLoggedInLawyer(Dashboard.this);
            this.finish();
        }

        if(fragment != null){
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction ft = fragmentManager.beginTransaction();

            ft.replace(R.id.screen_area, fragment);
            ft.commit();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}
