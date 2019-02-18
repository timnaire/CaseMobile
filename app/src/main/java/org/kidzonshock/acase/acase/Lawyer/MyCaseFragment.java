package org.kidzonshock.acase.acase.Lawyer;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import org.kidzonshock.acase.acase.Interfaces.Case;
import org.kidzonshock.acase.acase.Models.AddCase;
import org.kidzonshock.acase.acase.Models.CaseAdapter;
import org.kidzonshock.acase.acase.Models.CaseModel;
import org.kidzonshock.acase.acase.Models.Cases;
import org.kidzonshock.acase.acase.Models.CommonResponse;
import org.kidzonshock.acase.acase.Models.DeleteCase;
import org.kidzonshock.acase.acase.Models.EditCase;
import org.kidzonshock.acase.acase.Models.GetCase;
import org.kidzonshock.acase.acase.Models.LawyerListCase;
import org.kidzonshock.acase.acase.Models.ListClient;
import org.kidzonshock.acase.acase.Models.PreferenceDataLawyer;
import org.kidzonshock.acase.acase.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MyCaseFragment extends Fragment {
    AlertDialog add,edit;
    private static final String TAG = "MyCaseFragment";
    String lawyer_id,add_client_id;
    //    title
    TextInputLayout addlayoutCaseTitle;
    TextInputEditText addinputCaseTitle;
    TextInputLayout editlayoutCaseTitle;
    TextInputEditText editinputCaseTitle;

    TextInputLayout addlayoutCaseDescription;
    TextInputEditText addinputCaseDescription;
    TextInputLayout editlayoutCaseDescription;
    TextInputEditText editinputCaseDescription;

    ArrayList<String> spinnerArray,statusSpinnerArray;
    Spinner spinner,statusSpinner;

    ListView lv;
    CaseAdapter adapter;
    ArrayList<CaseModel> caselist = new ArrayList<>();
    LinearLayout loading;
    AdapterView.AdapterContextMenuInfo info;
    HashMap<String ,String> hmClient;
    private String output;

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

        hmClient = new HashMap<String,String>();

        loading = view.findViewById(R.id.linlaHeaderProgress);

        getAllCase();
        getClients();
        registerForContextMenu(lv);
        loading.setVisibility(View.VISIBLE);
        //add
        addlayoutCaseTitle = new TextInputLayout(getActivity());
        addinputCaseTitle = new TextInputEditText(getActivity());
        addlayoutCaseDescription = new TextInputLayout(getActivity());
        addinputCaseDescription = new TextInputEditText(getActivity());
        //edit
        editlayoutCaseTitle = new TextInputLayout(getActivity());
        editinputCaseTitle = new TextInputEditText(getActivity());
        editlayoutCaseDescription = new TextInputLayout(getActivity());
        editinputCaseDescription = new TextInputEditText(getActivity());

        statusSpinnerArray = new ArrayList<>();
        statusSpinnerArray.add("Case Open");
        statusSpinnerArray.add("Case Closed");

        statusSpinner = new Spinner(getActivity());
        ArrayAdapter<String> statusSpinnerArrayAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, statusSpinnerArray);
        statusSpinner.setAdapter(statusSpinnerArrayAdapter);

        spinnerArray = new ArrayList<String>();
        spinnerArray.add("Select Client");

        spinner = new Spinner(getActivity());
        spinner.setPadding(getResources().getDimensionPixelOffset(R.dimen.dp_19),getResources().getDimensionPixelOffset(R.dimen.dp_19),getResources().getDimensionPixelOffset(R.dimen.dp_19),0);
        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, spinnerArray);
        spinner.setAdapter(spinnerArrayAdapter);

        AlertDialog.Builder addCase = new AlertDialog.Builder(getActivity());
        AlertDialog.Builder EditCase = new AlertDialog.Builder(getActivity());

//        layout dialog for add
        LinearLayout dialayout = new LinearLayout(getActivity());
        dialayout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        dialayout.setOrientation(LinearLayout.VERTICAL);

        // layout dialog for edit
        LinearLayout editlayout = new LinearLayout(getActivity());
        editlayout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        editlayout.setOrientation(LinearLayout.VERTICAL);

//       add title
        addinputCaseTitle.setInputType(InputType.TYPE_CLASS_TEXT);
        addlayoutCaseTitle.setHint("Title");
        addlayoutCaseTitle.addView(addinputCaseTitle);
        addlayoutCaseTitle.setPadding(getResources().getDimensionPixelOffset(R.dimen.dp_19),getResources().getDimensionPixelOffset(R.dimen.dp_19),getResources().getDimensionPixelOffset(R.dimen.dp_19),0);
        dialayout.addView(addlayoutCaseTitle);

        dialayout.addView(spinner);

//       add description
        addinputCaseDescription.setInputType(InputType.TYPE_CLASS_TEXT);
        addlayoutCaseDescription.setHint("Case Description");
        addlayoutCaseDescription.addView(addinputCaseDescription);
        addlayoutCaseDescription.setPadding(getResources().getDimensionPixelOffset(R.dimen.dp_19),getResources().getDimensionPixelOffset(R.dimen.dp_19),getResources().getDimensionPixelOffset(R.dimen.dp_19),0);
        dialayout.addView(addlayoutCaseDescription);

//       edit  title
        editinputCaseTitle.setInputType(InputType.TYPE_CLASS_TEXT);
        editlayoutCaseTitle.setHint("Title");
        editlayoutCaseTitle.addView(editinputCaseTitle);
        editlayoutCaseTitle.setPadding(getResources().getDimensionPixelOffset(R.dimen.dp_19),getResources().getDimensionPixelOffset(R.dimen.dp_19),getResources().getDimensionPixelOffset(R.dimen.dp_19),0);
        editlayout.addView(editlayoutCaseTitle);

        editlayout.addView(statusSpinner);

//       edit  description
        editinputCaseDescription.setInputType(InputType.TYPE_CLASS_TEXT);
        editlayoutCaseDescription.setHint("Case Description");
        editlayoutCaseDescription.addView(editinputCaseDescription);
        editlayoutCaseDescription.setPadding(getResources().getDimensionPixelOffset(R.dimen.dp_19),getResources().getDimensionPixelOffset(R.dimen.dp_19),getResources().getDimensionPixelOffset(R.dimen.dp_19),0);
        editlayout.addView(editlayoutCaseDescription);

        addCase.setTitle("Add New Case");
        addCase.setView(dialayout);
        addCase.setCancelable(false);

        addCase.setPositiveButton(
                "Add",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                    }
                });

        addCase.setNegativeButton(
                "Cancel",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        add = addCase.create();

        EditCase.setTitle("Edit Case");
        EditCase.setView(editlayout);
        EditCase.setCancelable(false);

        EditCase.setPositiveButton(
                "Save",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                    }
                });

        EditCase.setNegativeButton(
                "Cancel",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        edit = EditCase.create();

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(position == parent.getItemIdAtPosition(position)){
                    CaseModel caseitem = (CaseModel) parent.getItemAtPosition(position);
                    Intent intent = new Intent(getActivity(), CaseDocuments.class);
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

    private void getClients() {
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
                    String client_id,name;
                    for(int i=0; i < list_clients.size(); i++){
                        client_id = list_clients.get(i).getClient_id();
                        name = list_clients.get(i).getClient().getFirst_name()+" "+list_clients.get(i).getClient().getLast_name();
                        spinnerArray.add(name);
                        hmClient.put(client_id,name);
                    }
                }else{
                    loading.setVisibility(View.GONE);
                    if(isAdded()) {
                        Log.d(TAG, "No clients found");
                    }
                }
            }
            @Override
            public void onFailure(Call<ListClient> call, Throwable t) {
                Toast.makeText(getActivity(), "Unable to list clients, please try again. " + t.getMessage() , Toast.LENGTH_SHORT).show();
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
                    String title, description,client_name;
                    title = addinputCaseTitle.getText().toString();
                    description = addinputCaseDescription.getText().toString();
                    client_name = spinner.getSelectedItem().toString();
                    for(String key: hmClient.keySet()) {
                        if(hmClient.get(key).equals(client_name)) {
                            add_client_id = key;
                        }
                    }
                    Log.d(TAG,"Name:"+add_client_id);
                    Log.d(TAG,"Name:"+client_name);
                    if(validateAddForm(title,description)){
                        addCase(title,add_client_id,description);
                        adapter.notifyDataSetChanged();
//                        FragmentTransaction ft = getFragmentManager().beginTransaction();
//                        ft.detach(MyCaseFragment.this).attach(MyCaseFragment.this).commit();
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
        final String case_id,title,date,description,client_name,client_email,client_phone,client_address,case_status;
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
                vIntent.putExtra("date",isoToDate(date));
                vIntent.putExtra("client_name",client_name);
                vIntent.putExtra("client_email",client_email);
                vIntent.putExtra("client_phone",client_phone);
                vIntent.putExtra("client_address",client_address);
                vIntent.putExtra("case_status",case_status);
                startActivity(vIntent);
                break;
            case R.id.Edit:
                title = caselist.get(info.position).getTitle();
                description = caselist.get(info.position).getCase_description();
                case_status = caselist.get(info.position).getStatus();
                editinputCaseTitle.setText(title);
                editinputCaseDescription.setText(description);
                statusSpinner.setSelection(getIndex(statusSpinner, case_status));
                edit.show();
                edit.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String title, status, description, case_id;
                        case_id = caselist.get(info.position).getCase_id();
                        title = editinputCaseTitle.getText().toString();
                        description = editinputCaseDescription.getText().toString();
                        status = statusSpinner.getSelectedItem().toString();
                        if(validateEditForm(title,description)){
                            editCase(case_id,title,description,status);
                            adapter.notifyDataSetChanged();
//                        FragmentTransaction ft = getFragmentManager().beginTransaction();
//                        ft.detach(MyCaseFragment.this).attach(MyCaseFragment.this).commit();
                        }
                    }
                });
                break;
            case R.id.Delete:
                AlertDialog.Builder ab = new AlertDialog.Builder(getActivity());
                ab.setTitle("Delete");
                ab.setMessage("Are you sure you want to delete " + caselist.get(info.position).getTitle());
                ab.setPositiveButton("Okay", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String cid = caselist.get(info.position).getCase_id();
                        caselist.remove(info.position);
                        adapter.notifyDataSetChanged();
                        deleteCase(cid);
                    }
                });
                ab.show();
                break;
        }
        return super.onContextItemSelected(item);
    }

    //private method of your class
    private int getIndex(Spinner spinner, String myString){
        for (int i=0;i<spinner.getCount();i++){
            if (spinner.getItemAtPosition(i).toString().equalsIgnoreCase(myString)){
                return i;
            }
        }
        return 0;
    }

    private void deleteCase(String case_id) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Case.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        Case service = retrofit.create(Case.class);
        final Call<CommonResponse> commonResponseCall = service.deleteCase(lawyer_id,new DeleteCase(case_id));
        commonResponseCall.enqueue(new Callback<CommonResponse>() {
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
                if(call.isCanceled()){
                    commonResponseCall.cancel();
                }
                Toast.makeText(getActivity(), "Unable to delete case, please try again.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void editCase(String case_id,String title, String description,String status) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Case.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        Case service = retrofit.create(Case.class);
        final Call<CommonResponse> commonResponseCall = service.editCase(lawyer_id,new EditCase(case_id,title,description, status));
        commonResponseCall.enqueue(new Callback<CommonResponse>() {
            @Override
            public void onResponse(Call<CommonResponse> call, Response<CommonResponse> response) {
                CommonResponse commonResponse = response.body();
                if(!commonResponse.isError()){
                    Toast.makeText(getActivity(), commonResponse.getMessage(), Toast.LENGTH_SHORT).show();
                    edit.dismiss();

                } else{
                    Toast.makeText(getActivity(), commonResponse.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<CommonResponse> call, Throwable t) {
                if(call.isCanceled()){
                    commonResponseCall.cancel();
                }
                Toast.makeText(getActivity(), "Unable to edit case, please try again.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void getAllCase(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Case.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        Case service = retrofit.create(Case.class);
        final Call<GetCase> getCaseCall = service.getCases(lawyer_id);
        getCaseCall.enqueue(new Callback<GetCase>() {
            @Override
            public void onResponse(Call<GetCase> call, Response<GetCase> response) {
                GetCase getCase = response.body();
                loading.setVisibility(View.GONE);
                if(isAdded() && !getCase.isError()){
                    ArrayList<Cases> cases = response.body().getCases();
                    String case_id, client_id,title,name,description,date,status,clientEmail, clientPhone,clientAddress, lawyerName, lawyerEmail, lawyerPhone, lawyerOffice;
                    for(int i=0; i < cases.size(); i++){
                        case_id = cases.get(i).getCase_id();
                        client_id = cases.get(i).getClient_id();
                        title = cases.get(i).getCase_title();
                        name = cases.get(i).getClient().getFirst_name()+ " " +cases.get(i).getClient().getLast_name();
                        date = cases.get(i).getCreated();
                        description = cases.get(i).getCase_description();
                        status = cases.get(i).getCase_status();
                        clientEmail = cases.get(i).getClient().getEmail();
                        clientPhone = cases.get(i).getClient().getPhone();
                        clientAddress = cases.get(i).getClient().getAddress();
                        lawyerName = cases.get(i).getLawyer().getFirst_name() +" "+cases.get(i).getLawyer().getLast_name();
                        lawyerEmail = cases.get(i).getLawyer().getEmail();
                        lawyerPhone = cases.get(i).getLawyer().getPhone();
                        lawyerOffice = cases.get(i).getLawyer().getOffice();
                        caselist.add(new CaseModel(case_id,client_id,title,name,date,description,status,clientEmail,clientPhone,clientAddress,lawyerName,lawyerEmail,lawyerPhone,lawyerOffice));
                    }
                    adapter = new CaseAdapter(getActivity(),caselist);
                    lv.setAdapter(adapter);
                    lv.setDivider(getActivity().getResources().getDrawable(R.drawable.transparentColor));
                    lv.setDividerHeight(20);
                    Toast.makeText(getActivity(), getCase.getMessage(), Toast.LENGTH_SHORT).show();
                }else{
                    loading.setVisibility(View.GONE);
                    if(isAdded()) {
                        Toast.makeText(getActivity(), "Found 0 case(s)", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<GetCase> call, Throwable t) {
                if(call.isCanceled()){
                    getCaseCall.cancel();
                }
                Toast.makeText(getActivity(), "Unable to get cases, please try again. " + t.getMessage() , Toast.LENGTH_SHORT).show();
            }
        });
    }

    private String isoToDate(String input) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            output = dateFormat.format(dateFormat.parse(input));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return output;
    }

    public void addCase(String title, String clientid, String description){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Case.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        Case service = retrofit.create(Case.class);
        final Call<CommonResponse> commonResponseCall = service.addCase(lawyer_id,new AddCase(title,clientid,description));
        commonResponseCall.enqueue(new Callback<CommonResponse>() {
            @Override
            public void onResponse(Call<CommonResponse> call, Response<CommonResponse> response) {
                CommonResponse commonResponse = response.body();
                if(isAdded() && !commonResponse.isError()){
                    Toast.makeText(getActivity(), commonResponse.getMessage(), Toast.LENGTH_SHORT).show();
                    add.dismiss();
                    addinputCaseDescription.setText("");
                    addinputCaseTitle.setText("");
                    spinner.setSelection(getIndex(spinner,"Select Client"));
                    caselist.clear();
                    lv.setAdapter(null);
                    loading.setVisibility(View.VISIBLE);
                    getAllCase();
//                    Fragment f = new MyCaseFragment();
//                    FragmentTransaction ft = getFragmentManager().beginTransaction();
//                    ft.detach(f).attach(f).commit();
//                    adapter.notifyDataSetChanged();
                } else{
                    if(isAdded()) {
                        Toast.makeText(getActivity(), commonResponse.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<CommonResponse> call, Throwable t) {
                if(call.isCanceled()){
                    commonResponseCall.cancel();
                }
                Toast.makeText(getActivity(), "Unable to add new case, please try again.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private boolean validateEditForm(String title, String description){
        boolean valid = true;
        if (TextUtils.isEmpty(description)) {
            editlayoutCaseDescription.setError("Required");
            editlayoutCaseDescription.requestFocus();
            valid = false;
        } else {
            editlayoutCaseDescription.setError(null);
        }

        if (TextUtils.isEmpty(title)) {
            editlayoutCaseTitle.setError("Required");
            editlayoutCaseTitle.requestFocus();
            valid = false;
        } else {
            editlayoutCaseTitle.setError(null);
        }
        return valid;
    }

    private boolean validateAddForm(String title, String description){
        boolean valid = true;
        if (TextUtils.isEmpty(description)) {
            addlayoutCaseDescription.setError("Required");
            addlayoutCaseDescription.requestFocus();
            valid = false;
        } else {
            addlayoutCaseDescription.setError(null);
        }

        if (TextUtils.isEmpty(title)) {
            addlayoutCaseTitle.setError("Required");
            addlayoutCaseTitle.requestFocus();
            valid = false;
        } else {
            addlayoutCaseTitle.setError(null);
        }
        return valid;
    }

}
