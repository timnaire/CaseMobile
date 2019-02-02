package org.kidzonshock.acase.acase.Lawyer;

import android.content.DialogInterface;
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
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import org.kidzonshock.acase.acase.Interfaces.Case;
import org.kidzonshock.acase.acase.Models.AddCase;
import org.kidzonshock.acase.acase.Models.CaseModel;
import org.kidzonshock.acase.acase.Models.Cases;
import org.kidzonshock.acase.acase.Models.CommonResponse;
import org.kidzonshock.acase.acase.Models.GetCase;
import org.kidzonshock.acase.acase.Models.PreferenceData;
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

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        return inflater.inflate(R.layout.fragment_mycase, null);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getAllCase();
        lawyer_id = PreferenceData.getLoggedInLawyerid(getActivity());
        lv = view.findViewById(R.id.list_caseview);

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
    public void onResume() {
        getAllCase();
        super.onResume();
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
//                ArrayList<Client> clientlist = response.body().getCases();
                ArrayList<Cases> cases = response.body().getCases();
                for(int i=0; i < cases.size(); i++){
                    String title = cases.get(i).getCase_title();
                    String name = cases.get(i).getClient().getFirst_name()+ " " +cases.get(i).getClient().getLast_name();
                    String date = cases.get(i).getCreated();
                    String status = cases.get(i).getCase_status();
                    caselist.add(new CaseModel(title,name,date,status));
                }
                adapter = new CaseAdapter(getActivity(),caselist);
                lv.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<GetCase> call, Throwable t) {
                Toast.makeText(getActivity(), "Unable to get cases, please try again.", Toast.LENGTH_SHORT).show();
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
