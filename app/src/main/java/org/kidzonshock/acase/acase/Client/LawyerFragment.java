package org.kidzonshock.acase.acase.Client;

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
import org.kidzonshock.acase.acase.Models.ClientListCase;
import org.kidzonshock.acase.acase.Models.LawyerModel;
import org.kidzonshock.acase.acase.Models.ListLawyer;
import org.kidzonshock.acase.acase.Models.PreferenceDataClient;
import org.kidzonshock.acase.acase.R;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LawyerFragment extends Fragment {

    String client_id;
    ListView lv;
    ArrayList<LawyerModel> list = new ArrayList<>();
    LawyerAdapter adapter;
    LinearLayout loading;
    private final String TAG = "LawyerFragment";
    AdapterView.AdapterContextMenuInfo info;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_lawyer,null);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        lv = view.findViewById(R.id.list_of_lawyer);
        client_id = PreferenceDataClient.getLoggedInClientid(getActivity());
        loading = view.findViewById(R.id.linlaHeaderProgress);

        getLawyer();
        loading.setVisibility(View.VISIBLE);
        registerForContextMenu(lv);
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
            case R.id.edit_user:
                break;
            case R.id.remove_user:
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    private void getLawyer() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Case.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        Case service = retrofit.create(Case.class);
        Call<ListLawyer> listLawyerCall = service.listLawyer(client_id);
        listLawyerCall.enqueue(new Callback<ListLawyer>() {
            @Override
            public void onResponse(Call<ListLawyer> call, Response<ListLawyer> response) {

                ListLawyer listLawyer = response.body();
                loading.setVisibility(View.GONE);
                if(!listLawyer.isError()){
                    ArrayList<ClientListCase> list_lawyers = response.body().getList_lawyers();
                    String profile_pic,name,email,phone,office;
                    for(int i=0; i < list_lawyers.size(); i++){
                        profile_pic = list_lawyers.get(i).getLawyer().getProfile_pic();
                        name = list_lawyers.get(i).getLawyer().getFirst_name()+" "+list_lawyers.get(i).getLawyer().getLast_name();
                        email = list_lawyers.get(i).getLawyer().getEmail();
                        phone = list_lawyers.get(i).getLawyer().getPhone();
                        office = list_lawyers.get(i).getLawyer().getOffice();
                        list.add(new LawyerModel(name,email,phone,office,profile_pic));
                    }

                    adapter = new LawyerAdapter(getActivity(),list);
                    lv.setAdapter(adapter);
                    Toast.makeText(getActivity(), listLawyer.getMessage(), Toast.LENGTH_SHORT).show();

                }else{
                    loading.setVisibility(View.GONE);
                    Toast.makeText(getActivity(), listLawyer.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ListLawyer> call, Throwable t) {
                Toast.makeText(getActivity(), "Unable to list lawyers, please try again. " + t.getMessage() , Toast.LENGTH_SHORT).show();
            }
        });
    }
}
