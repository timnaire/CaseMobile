package org.kidzonshock.acase.acase.Lawyer;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.text.InputType;
import android.text.TextUtils;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import org.kidzonshock.acase.acase.Interfaces.Case;
import org.kidzonshock.acase.acase.Models.AddCase;
import org.kidzonshock.acase.acase.Models.CaseModel;
import org.kidzonshock.acase.acase.Models.Cases;
import org.kidzonshock.acase.acase.Models.CommonResponse;
import org.kidzonshock.acase.acase.Models.GetCase;
import org.kidzonshock.acase.acase.Models.PreferenceDataLawyer;
import org.kidzonshock.acase.acase.R;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MyCaseFragment extends Fragment {
    AlertDialog add;
    private static final String TAG = "MyCaseFragment";
    String lawyer_id;
    //    title
    TextInputLayout layoutCaseTitle;
    TextInputEditText inputCaseTitle;
    //    client id
    TextInputLayout layoutCaseClientid;
    TextInputEditText inputCaseClientid;
    //    description
    TextInputLayout layoutCaseDescription;
    TextInputEditText inputCaseDescription;

    ListView lv;
    CaseAdapter adapter;
    ArrayList<CaseModel> caselist = new ArrayList<>();
    LinearLayout loading;
    AdapterView.AdapterContextMenuInfo info;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        return inflater.inflate(R.layout.fragment_mycase, null);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        lawyer_id = PreferenceDataLawyer.getLoggedInLawyerid(getActivity());
        lv = view.findViewById(R.id.list_caseview);


        loading = view.findViewById(R.id.linlaHeaderProgress);

        getAllCase();
        registerForContextMenu(lv);
        loading.setVisibility(View.VISIBLE);

        layoutCaseTitle = new TextInputLayout(getActivity());
        inputCaseTitle = new TextInputEditText(getActivity());

        layoutCaseClientid = new TextInputLayout(getActivity());
        inputCaseClientid = new TextInputEditText(getActivity());

        layoutCaseDescription = new TextInputLayout(getActivity());
        inputCaseDescription = new TextInputEditText(getActivity());

        AlertDialog.Builder builder1 = new AlertDialog.Builder(getActivity());

//        layout for dialog
        LinearLayout dialayout = new LinearLayout(getActivity());
        dialayout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        dialayout.setOrientation(LinearLayout.VERTICAL);

//        title
        inputCaseTitle.setInputType(InputType.TYPE_CLASS_TEXT);
        layoutCaseTitle.setHint("Title");
        layoutCaseTitle.addView(inputCaseTitle);
        layoutCaseTitle.setPadding(getResources().getDimensionPixelOffset(R.dimen.dp_19),getResources().getDimensionPixelOffset(R.dimen.dp_19),getResources().getDimensionPixelOffset(R.dimen.dp_19),0);
        dialayout.addView(layoutCaseTitle);
//        client id
        inputCaseClientid.setInputType(InputType.TYPE_CLASS_NUMBER);
        layoutCaseClientid.setHint("Client ID");
        layoutCaseClientid.addView(inputCaseClientid);
        layoutCaseClientid.setPadding(getResources().getDimensionPixelOffset(R.dimen.dp_19),getResources().getDimensionPixelOffset(R.dimen.dp_19),getResources().getDimensionPixelOffset(R.dimen.dp_19),0);
        dialayout.addView(layoutCaseClientid);
//        description
        inputCaseDescription.setInputType(InputType.TYPE_CLASS_TEXT);
        layoutCaseDescription.setHint("Case Description");
        layoutCaseDescription.addView(inputCaseDescription);
        layoutCaseDescription.setPadding(getResources().getDimensionPixelOffset(R.dimen.dp_19),getResources().getDimensionPixelOffset(R.dimen.dp_19),getResources().getDimensionPixelOffset(R.dimen.dp_19),0);
        dialayout.addView(layoutCaseDescription);

        builder1.setTitle("Add New Case");
        builder1.setView(dialayout);
        builder1.setCancelable(false);

        builder1.setPositiveButton(
                "Add",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                    }
                });

        builder1.setNegativeButton(
                "Cancel",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        add = builder1.create();

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(position == parent.getItemIdAtPosition(position)){
                    CaseModel caseitem = (CaseModel) parent.getItemAtPosition(position);
                    Intent intent = new Intent(getActivity(), CaseDocument.class);
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

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_mycase, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.search){

        } else if(id == R.id.add){
            add.show();
            add.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String title, clientid, description;
                    title = inputCaseTitle.getText().toString();
                    clientid = inputCaseClientid.getText().toString();
                    description = inputCaseDescription.getText().toString();
                    if(validateForm(title,clientid,description)){
                        addCase(title,clientid,description);
                    }
                }
            });
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        getActivity().getMenuInflater().inflate(R.menu.menu_case_context,menu);
        super.onCreateContextMenu(menu, v, menuInfo);
        info = (AdapterView.AdapterContextMenuInfo) menuInfo;
        menu.setHeaderTitle(caselist.get(info.position).getTitle());
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        String title,date,client_name,client_email,client_phone,client_address,case_status;
        int id = item.getItemId();
        switch(id){
            case R.id.View:
                Intent vIntent = new Intent(getActivity(),ViewCase.class);
                title = caselist.get(info.position).getTitle();
                date = caselist.get(info.position).getDate_created();
                client_name = caselist.get(info.position).getClientName();
                client_email = caselist.get(info.position).getClientEmail();
                client_phone = caselist.get(info.position).getClientPhone();
                client_address = caselist.get(info.position).getClientAddress();
                case_status = caselist.get(info.position).getStatus();
                vIntent.putExtra("title",title);
                vIntent.putExtra("date",date);
                vIntent.putExtra("client_name",client_name);
                vIntent.putExtra("client_email",client_email);
                vIntent.putExtra("client_phone",client_phone);
                vIntent.putExtra("client_address",client_address);
                vIntent.putExtra("case_status",case_status);
                startActivity(vIntent);
                Toast.makeText(getActivity(), "View", Toast.LENGTH_SHORT).show();
                break;
            case R.id.Edit:
                Toast.makeText(getActivity(), "Edit", Toast.LENGTH_SHORT).show();
                break;
            case R.id.Delete:
                Toast.makeText(getActivity(), "Delete", Toast.LENGTH_SHORT).show();
                break;
        }
        return super.onContextItemSelected(item);
    }

    public void getAllCase(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Case.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        Case service = retrofit.create(Case.class);
        Call<GetCase> getCaseCall = service.getCases(lawyer_id);
        getCaseCall.enqueue(new Callback<GetCase>() {
            @Override
            public void onResponse(Call<GetCase> call, Response<GetCase> response) {
                GetCase getCase = response.body();
                loading.setVisibility(View.GONE);
                if(!getCase.isError()){
                    ArrayList<Cases> cases = response.body().getCases();
                    String title,name,date,status,clientEmail, clientPhone,clientAddress, lawyerName, lawyerEmail, lawyerPhone, lawyerOffice;
                    for(int i=0; i < cases.size(); i++){
                        title = cases.get(i).getCase_title();
                        name = cases.get(i).getClient().getFirst_name()+ " " +cases.get(i).getClient().getLast_name();
                        date = cases.get(i).getCreated();
                        status = cases.get(i).getCase_status();
                        clientEmail = cases.get(i).getClient().getEmail();
                        clientPhone = cases.get(i).getClient().getPhone();
                        clientAddress = cases.get(i).getClient().getAddress();
                        lawyerName = cases.get(i).getLawyer().getFirst_name() +" "+cases.get(i).getLawyer().getLast_name();
                        lawyerEmail = cases.get(i).getLawyer().getEmail();
                        lawyerPhone = cases.get(i).getLawyer().getPhone();
                        lawyerOffice = cases.get(i).getLawyer().getOffice();
                        caselist.add(new CaseModel(title,name,date,status,clientEmail,clientPhone,clientAddress,lawyerName,lawyerEmail,lawyerPhone,lawyerOffice));
                    }
                    adapter = new CaseAdapter(getActivity(),caselist);
                    lv.setAdapter(adapter);
                    Toast.makeText(getActivity(), getCase.getMessage(), Toast.LENGTH_SHORT).show();
                }else{
                    loading.setVisibility(View.GONE);
                    Toast.makeText(getActivity(), getCase.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<GetCase> call, Throwable t) {
                Toast.makeText(getActivity(), "Unable to get cases, please try again. " + t.getMessage() , Toast.LENGTH_SHORT).show();
            }
        });
    }



    public void addCase(String title, String clientid, String description){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Case.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        Case service = retrofit.create(Case.class);
        Call<CommonResponse> commonResponseCall = service.addCase(lawyer_id,new AddCase(title,clientid,description));
        commonResponseCall.enqueue(new Callback<CommonResponse>() {
            @Override
            public void onResponse(Call<CommonResponse> call, Response<CommonResponse> response) {
                CommonResponse commonResponse = response.body();
                if(!commonResponse.isError()){
                    Toast.makeText(getActivity(), commonResponse.getMessage(), Toast.LENGTH_SHORT).show();
                    add.dismiss();
                    FragmentTransaction ft = getFragmentManager().beginTransaction();
                    ft.detach(MyCaseFragment.this).attach(MyCaseFragment.this).commit();
                } else{
                    Toast.makeText(getActivity(), commonResponse.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<CommonResponse> call, Throwable t) {
                Toast.makeText(getActivity(), "Unable to add new case, please try again.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private boolean validateForm(String title, String clientid, String description){
        boolean valid = true;
        if (TextUtils.isEmpty(description)) {
            layoutCaseDescription.setError("Required");
            layoutCaseDescription.requestFocus();
            valid = false;
        } else {
            layoutCaseDescription.setError(null);
        }

        if (TextUtils.isEmpty(clientid)) {
            layoutCaseClientid.setError("Required");
            layoutCaseClientid.requestFocus();
            valid = false;
        } else {
            layoutCaseClientid.setError(null);
        }

        if (TextUtils.isEmpty(title)) {
            layoutCaseTitle.setError("Required");
            layoutCaseTitle.requestFocus();
            valid = false;
        } else {
            layoutCaseTitle.setError(null);
        }
        return valid;
    }

}
