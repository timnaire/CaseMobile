package org.kidzonshock.acase.acase.Lawyer;

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
import android.widget.Toast;

import org.kidzonshock.acase.acase.Interfaces.Case;
import org.kidzonshock.acase.acase.Models.ClientAdapter;
import org.kidzonshock.acase.acase.Models.ClientModel;
import org.kidzonshock.acase.acase.Models.PreAppoint;
import org.kidzonshock.acase.acase.Models.PreAppointAdapter;
import org.kidzonshock.acase.acase.Models.PreAppointModel;
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
    ListView lv;
    ArrayList<PreAppointModel> list = new ArrayList<>();
    PreAppointAdapter adapter;
    LinearLayout loading;
    AdapterView.AdapterContextMenuInfo info;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_pre_appoint, null);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        lawyer_id = PreferenceDataLawyer.getLoggedInLawyerid(getActivity());

        lv = view.findViewById(R.id.list_appointments);
        loading = view.findViewById(R.id.linlaHeaderProgress);
        registerForContextMenu(lv);

        listRequest();
        loading.setVisibility(View.VISIBLE);
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
                loading.setVisibility(View.GONE);
                PreAppointRequestResponse resp = response.body();
                if (!resp.isError()) {
                    ArrayList<PreAppoint> list_preappoint = resp.getPreappoints();
                    String name,email,phone,address,profile_pic;
                    for (int i=0; i < list_preappoint.size(); i++) {
                        name = list_preappoint.get(i).getClient().getFirst_name() + " " + list_preappoint.get(i).getClient().getLast_name();
                        email = list_preappoint.get(i).getClient().getEmail();
                        phone = list_preappoint.get(i).getClient().getPhone();
                        address = list_preappoint.get(i).getClient().getAddress();
                        profile_pic = list_preappoint.get(i).getClient().getProfile_pic();
                        list.add(new PreAppointModel(name,email,phone,address,profile_pic));
                    }
                    adapter = new PreAppointAdapter(getActivity(),list);
                    lv.setAdapter(adapter);
                    Toast.makeText(getActivity(), resp.getMessage(), Toast.LENGTH_SHORT).show();
                }else{
                    loading.setVisibility(View.GONE);
                    if(isAdded()) {
                        Toast.makeText(getActivity(), resp.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<PreAppointRequestResponse> call, Throwable t) {
                loading.setVisibility(View.GONE);
                Toast.makeText(getActivity(), "Unable to list pre appointment request, please try again. " + t.getMessage() , Toast.LENGTH_SHORT).show();
            }
        });
    }
}
