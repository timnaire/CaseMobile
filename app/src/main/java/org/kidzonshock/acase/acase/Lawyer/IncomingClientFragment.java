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
import org.kidzonshock.acase.acase.Models.IncomingClient;
import org.kidzonshock.acase.acase.Models.PreAppoint;
import org.kidzonshock.acase.acase.Models.PreAppointAdapter;
import org.kidzonshock.acase.acase.Models.PreAppointModel;
import org.kidzonshock.acase.acase.Models.PreferenceDataLawyer;
import org.kidzonshock.acase.acase.R;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class IncomingClientFragment extends Fragment {

    String lawyer_id;
    ListView lv;
    ArrayList<PreAppointModel> list = new ArrayList<>();
    PreAppointAdapter adapter;
    LinearLayout loading;
    AdapterView.AdapterContextMenuInfo info;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_incoming_client, null);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        lawyer_id = PreferenceDataLawyer.getLoggedInLawyerid(getActivity());

        lv = view.findViewById(R.id.list_incoming_clients);
        loading = view.findViewById(R.id.linlaHeaderProgress);
        registerForContextMenu(lv);

        listIncomingClient();
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
        switch(id){
            case R.id.accept_user:
                Toast.makeText(getActivity(), "Client accepted!", Toast.LENGTH_SHORT).show();
                break;
            case R.id.decline_user:
                Toast.makeText(getActivity(), "Client rejected!", Toast.LENGTH_SHORT).show();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    public void listIncomingClient(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Case.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        Case service = retrofit.create(Case.class);

        Call<IncomingClient> pendingClient = service.listPendingClient(lawyer_id);
        pendingClient.enqueue(new Callback<IncomingClient>() {
            @Override
            public void onResponse(Call<IncomingClient> call, Response<IncomingClient> response) {
                loading.setVisibility(View.GONE);
                IncomingClient resp = response.body();
                if (!resp.isError()) {
                    ArrayList<PreAppoint> list_incomingclient = resp.getPreappoints();
                    String iid,client_id,name,email,phone,address,profile_pic;
                    for (int i=0; i < list_incomingclient.size(); i++) {
                        iid = list_incomingclient.get(i).getId();
                        client_id = list_incomingclient.get(i).getClient().getClient_id();
                        name = list_incomingclient.get(i).getClient().getFirst_name() + " " + list_incomingclient.get(i).getClient().getLast_name();
                        email = list_incomingclient.get(i).getClient().getEmail();
                        phone = list_incomingclient.get(i).getClient().getPhone();
                        address = list_incomingclient.get(i).getClient().getAddress();
                        profile_pic = list_incomingclient.get(i).getClient().getProfile_pic();
                        list.add(new PreAppointModel(iid,client_id,name,email,phone,address,profile_pic));
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
            public void onFailure(Call<IncomingClient> call, Throwable t) {

            }
        });
    }
}
