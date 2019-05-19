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
import android.widget.Toast;

import org.kidzonshock.acase.acase.Interfaces.Case;
import org.kidzonshock.acase.acase.Models.CaseAdapter;
import org.kidzonshock.acase.acase.Models.CaseModel;
import org.kidzonshock.acase.acase.Models.Cases;
import org.kidzonshock.acase.acase.Models.GetCase;
import org.kidzonshock.acase.acase.Models.PreferenceDataClient;
import org.kidzonshock.acase.acase.R;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CaseFragment extends Fragment {

    String client_id;
    ListView lv;
    CaseAdapter adapter;
    ArrayList<CaseModel> caselist = new ArrayList<>();
    LinearLayout loading;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_case,null);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        client_id = PreferenceDataClient.getLoggedInClientid(getActivity());
        lv = view.findViewById(R.id.list_case_clients);

        loading = view.findViewById(R.id.linlaHeaderProgress);
        getAllCase();
        loading.setVisibility(View.VISIBLE);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(position == parent.getItemIdAtPosition(position)){
                    CaseModel caseitem = (CaseModel) parent.getItemAtPosition(position);
                    Intent intent = new Intent(getActivity(), CaseDocument.class);
                    intent.putExtra("case_id", caseitem.getCase_id());
                    intent.putExtra("title", caseitem.getTitle());
                    intent.putExtra("clientName", caseitem.getClientName());
                    intent.putExtra("date", caseitem.getDate_created());
                    intent.putExtra("status", caseitem.getStatus());
                    intent.putExtra("clientEmail", caseitem.getClientEmail());
                    intent.putExtra("clientPhone", caseitem.getClientPhone());
                    intent.putExtra("clientAddress", caseitem.getClientAddress());
                    intent.putExtra("lawyerName", caseitem.getLawyerName());
                    intent.putExtra("lawyerEmail", caseitem.getLawyyerEmail());
                    intent.putExtra("lawyerPhone", caseitem.getLawyerPhone());
                    intent.putExtra("lawyerOffice", caseitem.getLawyerOffice());
                    startActivity(intent);
                }
            }
        });

    }

    public void getAllCase(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Case.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        Case service = retrofit.create(Case.class);
        Call<GetCase> getCaseCall = service.getCasesClient(client_id);
        getCaseCall.enqueue(new Callback<GetCase>() {
            @Override
            public void onResponse(Call<GetCase> call, Response<GetCase> response) {
                GetCase getCase = response.body();
                loading.setVisibility(View.GONE);
                if(isAdded() && !getCase.isError()){
                    ArrayList<Cases> cases = response.body().getCases();
                    String case_id, client_id,title,name,description,remarks,date,status,clientEmail, clientPhone,clientAddress, lawyerName, lawyerEmail, lawyerPhone, lawyerOffice;
                    for(int i=0; i < cases.size(); i++){
                        case_id = cases.get(i).getCase_id();
                        client_id = cases.get(i).getClient_id();
                        title = cases.get(i).getCase_title();
                        name = cases.get(i).getLawyer().getFirst_name() +" "+cases.get(i).getLawyer().getLast_name();
                        date = cases.get(i).getCreated();
                        description = cases.get(i).getCase_description();
                        status = cases.get(i).getCase_status();
                        remarks = cases.get(i).getRemarks();
                        clientEmail = cases.get(i).getClient().getEmail();
                        clientPhone = cases.get(i).getClient().getPhone();
                        clientAddress = cases.get(i).getClient().getAddress();
                        lawyerName = cases.get(i).getLawyer().getFirst_name() +" "+cases.get(i).getLawyer().getLast_name();
                        lawyerEmail = cases.get(i).getLawyer().getEmail();
                        lawyerPhone = cases.get(i).getLawyer().getPhone();
                        lawyerOffice = cases.get(i).getLawyer().getOffice();
                        caselist.add(new CaseModel(case_id,client_id,title,name,date,description,status,remarks,clientEmail,clientPhone,clientAddress,lawyerName,lawyerEmail,lawyerPhone,lawyerOffice));
                    }
                    adapter = new CaseAdapter(getActivity(),caselist);
                    lv.setAdapter(adapter);
                    lv.setDivider(getActivity().getResources().getDrawable(R.drawable.transparentColor));
                    lv.setDividerHeight(20);
                    Toast.makeText(getActivity(), getCase.getMessage(), Toast.LENGTH_SHORT).show();
                }else{
                    loading.setVisibility(View.GONE);
                    if(isAdded()) {
                        Toast.makeText(getActivity(), getCase.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<GetCase> call, Throwable t) {
                Toast.makeText(getActivity(), "Unable to get cases, please try again. " + t.getMessage() , Toast.LENGTH_SHORT).show();
            }
        });
    }
}
