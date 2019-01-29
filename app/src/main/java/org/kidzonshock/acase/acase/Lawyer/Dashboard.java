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
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

import org.kidzonshock.acase.acase.Interfaces.Case;
import org.kidzonshock.acase.acase.Models.GetLawPractice;
import org.kidzonshock.acase.acase.Models.LawPractice;
import org.kidzonshock.acase.acase.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Dashboard extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    Toolbar toolbar;
    private String lawyer_id,first_name,last_name,email,phone,cityOrMunicipality,office,profile_pic,aboutme;
    private String[] law_practice;
    final String TAG = "Dashboard";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent prev = getIntent();
        lawyer_id = prev.getStringExtra("lawyer_id");
        first_name = prev.getStringExtra("first_name");
        last_name = prev.getStringExtra("last_name");
        email = prev.getStringExtra("email");
        phone = prev.getStringExtra("phone");
        cityOrMunicipality = prev.getStringExtra("cityOrMunicipality");
        office = prev.getStringExtra("office");
        profile_pic = prev.getStringExtra("profile_pic");
        aboutme = prev.getStringExtra("aboutme");
        getPractice();
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        View headerLayout = navigationView.getHeaderView(0);

        Log.d(TAG, first_name);
        Log.d(TAG, last_name);
        Log.d(TAG, profile_pic);

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.dashboard, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

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

        } else if (id == R.id.nav_account) {

            toolbar.setTitle("My Account");
            Bundle info = new Bundle();
            info.putString("lawyer_id",lawyer_id);
            info.putString("first_name",first_name);
            info.putString("last_name",last_name);
            info.putString("email",email);
            info.putString("phone",phone);
            info.putString("cityOrMunicipality",cityOrMunicipality);
            info.putString("office",office);
            info.putString("profile_pic",profile_pic);
            info.putString("aboutme", aboutme);
//            info.putStringArrayList("law_practice", new ArrayList<>(law_practice));
            fragment = new AccountFragment();
            fragment.setArguments(info);

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_signout) {
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

    public void getPractice(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Case.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Case service = retrofit.create(Case.class);
        Call<GetLawPractice> commonResponseCall = service.getPractice(lawyer_id);
        commonResponseCall.enqueue(new Callback<GetLawPractice>() {
            @Override
            public void onResponse(Call<GetLawPractice> call, Response<GetLawPractice> response) {
                GetLawPractice data = response.body();
                List<LawPractice> lawpracticelist = data.getPractice();

                law_practice = new String[lawpracticelist.size()];

                for(int i=0; i < lawpracticelist.size(); i++){
                    law_practice[i] = lawpracticelist.get(i).getLaw_practice();
                }
            }
            @Override
            public void onFailure(Call<GetLawPractice> call, Throwable t) {

            }
        });
    }
}
