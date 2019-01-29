package org.kidzonshock.acase.acase.Lawyer;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import org.kidzonshock.acase.acase.Models.Setting;
import org.kidzonshock.acase.acase.R;

import java.util.ArrayList;

public class AccountFragment extends Fragment {

    String lawyer_id,first_name,last_name,email,phone,cityOrMunicipality,office,profile_pic,aboutme;

    ListView lv;
    ArrayList<Setting> titles = new ArrayList<>();
    SettingAdapter adapter;

    private static final String TAG = "AccountFragment";
    
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_account, null);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //getting the data from bundle in dashboard activty
        lawyer_id = getArguments().getString("lawyer_id");
        first_name = getArguments().getString("first_name");
        last_name = getArguments().getString("last_name");
        email = getArguments().getString("email");
        phone = getArguments().getString("phone");
        cityOrMunicipality = getArguments().getString("cityOrMunicipality");
        office = getArguments().getString("office");
        profile_pic = getArguments().getString("profile_pic");
        aboutme = getArguments().getString("aboutme");

        lv = view.findViewById(R.id.listview);

        titles.add(new Setting("Profile Picture"));
        titles.add(new Setting("Profile Information"));
        titles.add(new Setting("Email"));
        titles.add(new Setting("Password"));
        adapter = new SettingAdapter(getActivity(),titles);
        lv.setAdapter(adapter);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(position == 0){
                    Intent changepic = new Intent(getActivity(), ChangePicture.class);
                    changepic.putExtra("lawyer_id",lawyer_id);
                    changepic.putExtra("profile_pic",profile_pic);
                    startActivity(changepic);
                } else if(position == 1){
                    Intent changeinfo = new Intent(getActivity(), ChangeInformation.class);
                    changeinfo.putExtra("lawyer_id",lawyer_id);
                    changeinfo.putExtra("first_name",first_name);
                    changeinfo.putExtra("last_name",last_name);
                    changeinfo.putExtra("email",email);
                    changeinfo.putExtra("phone",phone);
                    changeinfo.putExtra("cityOrMunicipality",cityOrMunicipality);
                    changeinfo.putExtra("office",office);
                    changeinfo.putExtra("aboutme",aboutme);
                    startActivity(changeinfo);
                } else if(position == 2){

                } else if(position == 3){

                }
            }
        });

    }


}
