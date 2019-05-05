package org.kidzonshock.acase.acase.Lawyer;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import org.kidzonshock.acase.acase.R;

public class DashboardFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_dashboard, null);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        BottomNavigationView navigation = (BottomNavigationView) view.findViewById(R.id.dashboard_nav);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);


//        navigation.setOnNavigationItemSelectedListener();
        navigation.setItemIconTintList(null);

        Fragment fragment = new PreAppointFragment();
        FragmentTransaction ft = getChildFragmentManager().beginTransaction();
        ft.replace(R.id.dashboard_screen, fragment);
        ft.commit();
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            int id = item.getItemId();
            Fragment fragment = null;

            if(id == R.id.pre_appoint) {
                fragment = new PreAppointFragment();
                Toast.makeText(getActivity(), "Pre Appoint Request", Toast.LENGTH_SHORT).show();
            } else if(id == R.id.client_request){
                fragment = new IncomingClientFragment();
                Toast.makeText(getActivity(), "Client Request", Toast.LENGTH_SHORT).show();
            }

            if(fragment != null) {
                FragmentTransaction ft = getChildFragmentManager().beginTransaction();
                ft.replace(R.id.dashboard_screen, fragment);
                ft.commit();
            }

            return true;
        }
    };
}
