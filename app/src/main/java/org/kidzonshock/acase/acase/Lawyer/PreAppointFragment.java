package org.kidzonshock.acase.acase.Lawyer;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import org.kidzonshock.acase.acase.Interfaces.Case;
import org.kidzonshock.acase.acase.Models.CommonResponse;
import org.kidzonshock.acase.acase.Models.PreAppoint;
import org.kidzonshock.acase.acase.Models.PreAppointAdapter;
import org.kidzonshock.acase.acase.Models.PreAppointModel;
import org.kidzonshock.acase.acase.Models.PreAppointRequestResponse;
import org.kidzonshock.acase.acase.Models.PreAppointment;
import org.kidzonshock.acase.acase.Models.PreferenceDataLawyer;
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

    private final String TAG = "PreAppointmentFragment";
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

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        getActivity().getMenuInflater().inflate(R.menu.menu_option_dashboard,menu);
        super.onCreateContextMenu(menu, v, menuInfo);
        info = (AdapterView.AdapterContextMenuInfo) menuInfo;
        menu.setHeaderTitle(list.get(info.position).getName());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        String pid = list.get(info.position).getPid();
        String client_id = list.get(info.position).getClient_id();
        String status = "";
        switch(id){
            case R.id.accept_user:
                status = "accept";
                acceptAppointment(client_id, lawyer_id,status);
                Toast.makeText(getActivity(), "Accepted pre appointment", Toast.LENGTH_SHORT).show();
                break;
            case R.id.decline_user:
                status = "decline";
                declineAppointment(client_id,status);
                Toast.makeText(getActivity(), "Declined a pre appointment", Toast.LENGTH_SHORT).show();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    private void acceptAppointment(String client_id, String lawyer_id, String status) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Case.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        Case service = retrofit.create(Case.class);
        Call<CommonResponse> accept = service.acceptPreAppoint(client_id, new PreAppointment(lawyer_id,status));
        accept.enqueue(new Callback<CommonResponse>() {
            @Override
            public void onResponse(Call<CommonResponse> call, Response<CommonResponse> response) {
                CommonResponse resp = response.body();
                if(!resp.isError()){
                    Toast.makeText(getActivity(), resp.getMessage(), Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getActivity(), resp.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<CommonResponse> call, Throwable t) {
                Toast.makeText(getActivity(), "Please check your internet connection.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void declineAppointment(String client_id, String status) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Case.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        Case service = retrofit.create(Case.class);
        Call<CommonResponse> decline = service.declinePreAppoint(client_id, new PreAppointment(lawyer_id,status));
        decline.enqueue(new Callback<CommonResponse>() {
            @Override
            public void onResponse(Call<CommonResponse> call, Response<CommonResponse> response) {
                CommonResponse resp = response.body();
                if(!resp.isError()){
                    Toast.makeText(getActivity(), resp.getMessage(), Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getActivity(), resp.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<CommonResponse> call, Throwable t) {
                Toast.makeText(getActivity(), "Please check your internet connection.", Toast.LENGTH_SHORT).show();
            }
        });
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
                    String pid,client_id,name,email,phone,address,profile_pic;
                    for (int i=0; i < list_preappoint.size(); i++) {
                        client_id = list_preappoint.get(i).getClient().getClient_id();
                        pid = list_preappoint.get(i).getId();
                        name = list_preappoint.get(i).getClient().getFirst_name() + " " + list_preappoint.get(i).getClient().getLast_name();
                        email = list_preappoint.get(i).getClient().getEmail();
                        phone = list_preappoint.get(i).getClient().getPhone();
                        address = list_preappoint.get(i).getClient().getAddress();
                        profile_pic = list_preappoint.get(i).getClient().getProfile_pic();
                        list.add(new PreAppointModel(pid,client_id,name,email,phone,address,profile_pic));
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
