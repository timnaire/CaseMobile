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
import android.widget.LinearLayout;
import android.widget.ListView;

import org.kidzonshock.acase.acase.Interfaces.Case;
import org.kidzonshock.acase.acase.Models.GetLawPractice;
import org.kidzonshock.acase.acase.Models.LawPractice;
import org.kidzonshock.acase.acase.Models.PreferenceData;
import org.kidzonshock.acase.acase.Models.Setting;
import org.kidzonshock.acase.acase.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AccountFragment extends Fragment {

    String lawyer_id,profile_pic,email;
    String[] law_practice;
    ListView lv;
    ArrayList<Setting> titles = new ArrayList<>();
    SettingAdapter adapter;
    LinearLayout loading;
    private static final String TAG = "AccountFragment";
    
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_account, null);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        loading = view.findViewById(R.id.linlaHeaderProgress);

        getPractice();
        loading.setVisibility(View.VISIBLE);
        getEmail();
//        law_practice = getArguments().getStringArray("law_practice");
        lawyer_id = PreferenceData.getLoggedInLawyerid(getActivity());
        profile_pic = PreferenceData.getLoggedInProfilePicture(getActivity());

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
                    Intent changeInfo = new Intent(getActivity(), ChangeInformation.class);
                    changeInfo.putExtra("law_practice",law_practice);
                    startActivity(changeInfo);
                } else if(position == 2){
                    Intent changeEmail = new Intent(getActivity(), ChangeEmail.class);
                    changeEmail.putExtra("email", email);
                    startActivity(changeEmail);
                } else if(position == 3){
                    Intent changePass = new Intent(getActivity(), ChangePassword.class);
                    startActivity(changePass);
                }
            }
        });

    }

    @Override
    public void onResume() {
        getPractice();
        getEmail();
        super.onResume();
    }

    public String getEmail(){
        return email = PreferenceData.getLoggedInEmail(getActivity());
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
                loading.setVisibility(View.GONE);
                GetLawPractice data = response.body();
                List<LawPractice> lawpracticelist = data.getPractice();

                law_practice = new String[lawpracticelist.size()];

                for(int i=0; i < lawpracticelist.size(); i++){
                    law_practice[i] = lawpracticelist.get(i).getLaw_practice();
                }
            }
            @Override
            public void onFailure(Call<GetLawPractice> call, Throwable t) {
                loading.setVisibility(View.VISIBLE);
            }
        });
    }

}
