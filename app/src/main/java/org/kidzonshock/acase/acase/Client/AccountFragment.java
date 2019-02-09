package org.kidzonshock.acase.acase.Client;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;

import org.kidzonshock.acase.acase.Models.PreferenceDataClient;
import org.kidzonshock.acase.acase.Models.PreferenceDataLawyer;
import org.kidzonshock.acase.acase.Models.Setting;
import org.kidzonshock.acase.acase.Models.SettingAdapter;
import org.kidzonshock.acase.acase.R;

import java.util.ArrayList;

public class AccountFragment extends Fragment {

    LinearLayout loading;

    ListView lv;
    ArrayList<Setting> titles = new ArrayList<>();
    SettingAdapter adapter;

    String client_id,profile_pic,email;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_account_client,null);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
//        loading = view.findViewById(R.id.linlaHeaderProgress);
//        loading.setVisibility(View.VISIBLE);
        getEmail();
        client_id = PreferenceDataClient.getLoggedInClientid(getActivity());
        profile_pic = PreferenceDataClient.getLoggedInProfilePicture(getActivity());

        lv = view.findViewById(R.id.list_setting_client);

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
                    Intent changepic = new Intent(getActivity(), ClientChangePicture.class);
                    changepic.putExtra("client_id",client_id);
                    changepic.putExtra("profile_pic",profile_pic);
                    startActivity(changepic);
                } else if(position == 1){
                    Intent changeInfo = new Intent(getActivity(), ClientChangeInformation.class);
                    startActivity(changeInfo);
                } else if(position == 2){
                    Intent changeEmail = new Intent(getActivity(), ClientChangeEmail.class);
                    changeEmail.putExtra("email", email);
                    startActivity(changeEmail);
                } else if(position == 3){
                    Intent changePass = new Intent(getActivity(), ClientChangePassword.class);
                    startActivity(changePass);
                }
            }
        });

    }

    @Override
    public void onResume() {
        getEmail();
        super.onResume();
    }

    public String getEmail(){
        return email = PreferenceDataLawyer.getLoggedInEmail(getActivity());
    }

}
