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
import org.kidzonshock.acase.acase.Models.ClientAdapter;
import org.kidzonshock.acase.acase.Models.ClientModel;
import org.kidzonshock.acase.acase.Models.LawyerListCase;
import org.kidzonshock.acase.acase.Models.ListClient;
import org.kidzonshock.acase.acase.Models.PreferenceDataLawyer;
import org.kidzonshock.acase.acase.R;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ClientFragment extends Fragment {

    String lawyer_id;
    ListView lv;
    ArrayList<ClientModel> list = new ArrayList<>();
    ClientAdapter adapter;
    LinearLayout loading;
    AdapterView.AdapterContextMenuInfo info;
    private final String TAG = "ClientFrag";
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_client, null);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        lv = view.findViewById(R.id.list_of_clients);
        lawyer_id = PreferenceDataLawyer.getLoggedInLawyerid(getActivity());
        loading = view.findViewById(R.id.linlaHeaderProgress);
        registerForContextMenu(lv);

        getClients();
        loading.setVisibility(View.VISIBLE);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        getActivity().getMenuInflater().inflate(R.menu.menu_lawyer_client,menu);
        super.onCreateContextMenu(menu, v, menuInfo);
        info = (AdapterView.AdapterContextMenuInfo) menuInfo;
        menu.setHeaderTitle(list.get(info.position).getName());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        switch(id){
            case R.id.view_user:
                break;
            case R.id.remove_user:
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    public void getClients(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Case.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        Case service = retrofit.create(Case.class);
        Call<ListClient> listClientCall = service.listClient(lawyer_id);
        listClientCall.enqueue(new Callback<ListClient>() {
            @Override
            public void onResponse(Call<ListClient> call, Response<ListClient> response) {

                ListClient listClient = response.body();
                loading.setVisibility(View.GONE);
                if(!listClient.isError()){
                    ArrayList<LawyerListCase> list_clients = response.body().getList_clients();
                    String profile_pic,name,email,phone,address;
                    for(int i=0; i < list_clients.size(); i++){
                        profile_pic = list_clients.get(i).getClient().getProfile_pic();
                        name = list_clients.get(i).getClient().getFirst_name()+" "+list_clients.get(i).getClient().getLast_name();
                        email = list_clients.get(i).getClient().getEmail();
                        phone = list_clients.get(i).getClient().getPhone();
                        address = list_clients.get(i).getClient().getAddress();
                        list.add(new ClientModel(name,email,phone,address,profile_pic));
                    }

                    adapter = new ClientAdapter(getActivity(),list);
                    lv.setAdapter(adapter);
                    Toast.makeText(getActivity(), listClient.getMessage(), Toast.LENGTH_SHORT).show();

                }else{
                    loading.setVisibility(View.GONE);
                    if(isAdded()) {
                        Toast.makeText(getActivity(), listClient.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<ListClient> call, Throwable t) {
                Toast.makeText(getActivity(), "Unable to list clients, please try again. " + t.getMessage() , Toast.LENGTH_SHORT).show();
            }
        });
    }
}
