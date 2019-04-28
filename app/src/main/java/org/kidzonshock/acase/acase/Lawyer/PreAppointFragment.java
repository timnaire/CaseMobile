package org.kidzonshock.acase.acase.Lawyer;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.kidzonshock.acase.acase.Interfaces.Case;
import org.kidzonshock.acase.acase.Models.PreAppointRequestResponse;
import org.kidzonshock.acase.acase.Models.PreferenceDataLawyer;
import org.kidzonshock.acase.acase.Models.Relation;
import org.kidzonshock.acase.acase.R;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PreAppointFragment extends Fragment {

    String lawyer_id;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_pre_appoint, null);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        lawyer_id = PreferenceDataLawyer.getLoggedInLawyerid(getActivity());
    }

    public void listRequest(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Case.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        Case service = retrofit.create(Case.class);

        Call<PreAppointRequestResponse> preAppointRequestResponseCall = service.listRequest(lawyer_id);
        preAppointRequestResponseCall.enqueue(new Callback<PreAppointRequestResponse>() {
            @Override
            public void onResponse(Call<PreAppointRequestResponse> call, Response<PreAppointRequestResponse> response) {
                PreAppointRequestResponse resp = response.body();
                ArrayList<Relation> list_relation = resp.getRelation();
                for (int i=0; i < list_relation.size(); i++) {

                }
            }

            @Override
            public void onFailure(Call<PreAppointRequestResponse> call, Throwable t) {

            }
        });
    }
}
