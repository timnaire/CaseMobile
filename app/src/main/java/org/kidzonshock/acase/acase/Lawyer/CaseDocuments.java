package org.kidzonshock.acase.acase.Lawyer;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import org.kidzonshock.acase.acase.R;

public class CaseDocuments extends AppCompatActivity {

    private final String TAG = "CaseDocuments";
    String case_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_case_documents);

        assert getSupportActionBar() != null;
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Documents");

        Intent prev = getIntent();
        case_id = prev.getStringExtra("case_id");

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        Fragment fragment = new AllFragment();

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction ft = fragmentManager.beginTransaction();

        ft.replace(R.id.screenfiles, fragment);
        ft.commit();

    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            int id = item.getItemId();
            Fragment fragment = null;

            if(id == R.id.navigation_all) {

                Bundle all = new Bundle();
                all.putString("case_id", case_id);
                fragment = new AllFragment();
                fragment.setArguments(all);

            } else if(id == R.id.navigation_research){

                Bundle research = new Bundle();
                research.putString("case_id", case_id);
                fragment = new ResearchFragment();
                fragment.setArguments(research);

            } else if(id == R.id.navigation_publicdoc){

                Bundle publicdoc = new Bundle();
                publicdoc.putString("case_id",case_id);
                fragment = new PublicFragment();
                fragment.setArguments(publicdoc);
            }

            if(fragment != null) {
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction ft = fragmentManager.beginTransaction();

                ft.replace(R.id.screenfiles, fragment);
                ft.commit();
            }
            return true;
        }
    };

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_mycase, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.search){

        } else if(id == R.id.add){
            Intent intent = new Intent(CaseDocuments.this,FileUpload.class);
            intent.putExtra("case_id", case_id);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
}
