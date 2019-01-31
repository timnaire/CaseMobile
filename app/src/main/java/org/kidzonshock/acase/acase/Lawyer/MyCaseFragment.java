package org.kidzonshock.acase.acase.Lawyer;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
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
import android.widget.Toast;

import org.kidzonshock.acase.acase.R;

public class MyCaseFragment extends Fragment {
    AlertDialog add;
    private static final String TAG = "MyCaseFragment";

    //    title
    TextInputLayout layoutCaseTitle;
    TextInputEditText inputCaseTitle;
    //    client id
    TextInputLayout layoutCaseClientid;
    TextInputEditText inputCaseClientid;
    //    description
    TextInputLayout layoutCaseDescription;
    TextInputEditText inputCaseDescription;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        return inflater.inflate(R.layout.fragment_mycase, null);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

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

        //    title


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
                        String title, clientid, description;
                        title = inputCaseTitle.getText().toString();
                        clientid = inputCaseClientid.getText().toString();
                        description = inputCaseDescription.getText().toString();
                        if(validateForm(title,clientid,description)){
                            addCase(title,clientid,description);
                        }
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

    public void addCase(String title, String clientid, String description){
        Toast.makeText(getActivity(), "Case Added!", Toast.LENGTH_SHORT).show();
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
        }
        return super.onOptionsItemSelected(item);
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
