package org.kidzonshock.acase.acase.Client;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.paypal.android.sdk.payments.PayPalConfiguration;
import com.paypal.android.sdk.payments.PayPalPayment;
import com.paypal.android.sdk.payments.PayPalService;
import com.paypal.android.sdk.payments.PaymentActivity;
import com.paypal.android.sdk.payments.PaymentConfirmation;

import org.json.JSONException;
import org.json.JSONObject;
import org.kidzonshock.acase.acase.Config.Config;
import org.kidzonshock.acase.acase.Interfaces.Case;
import org.kidzonshock.acase.acase.Models.ClientListCase;
import org.kidzonshock.acase.acase.Models.CommonResponse;
import org.kidzonshock.acase.acase.Models.ListLawyer;
import org.kidzonshock.acase.acase.Models.ClientPaymentModel;
import org.kidzonshock.acase.acase.Models.PreferenceDataClient;
import org.kidzonshock.acase.acase.R;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.app.Activity.RESULT_OK;

public class PaymentFragment extends Fragment {

    private static final int PAYPAL_REQUEST_CODE = 123;

    private static PayPalConfiguration config = new PayPalConfiguration()
            .environment(PayPalConfiguration.ENVIRONMENT_SANDBOX)
            .clientId(Config.PAYPAL_CLIENT_ID);

    private final String TAG = "PaymentFragment";

    @Override
    public void onDestroyView() {
        getActivity().stopService(new Intent(getActivity(),PayPalService.class));
        super.onDestroyView();
    }

    TextInputLayout layoutClientPayment;
    TextInputEditText inputClientPayment;
    Spinner spinnerPayLawyer;
    ArrayList<String> spinnerLawyerArray;
    HashMap<String ,String> hmLawyer;

    ArrayAdapter<String> adapter;
    Button btnClientPayment;
    String amount = "",client_id,paymentId,lawyer_id;
    LinearLayout loading;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_payment,null);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        loading = view.findViewById(R.id.linlaHeaderProgress);
        loading.setVisibility(View.VISIBLE);
        hmLawyer = new HashMap<String,String>();
        client_id = PreferenceDataClient.getLoggedInClientid(getActivity());
        getLawyer();

        //start paypal service
        Intent intent = new Intent(getActivity(), PayPalService.class);
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, config);
        getActivity().startService(intent);

        layoutClientPayment = view.findViewById(R.id.layoutClientPayment);
        inputClientPayment = view.findViewById(R.id.inputClientPayment);
        btnClientPayment = view.findViewById(R.id.btnClientPayment);

        spinnerLawyerArray = new ArrayList<String>();
        spinnerLawyerArray.add("Select Lawyer");
        spinnerPayLawyer = view.findViewById(R.id.spinnerPayLawyer);
        adapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_item, spinnerLawyerArray);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerPayLawyer.setAdapter(adapter);

        btnClientPayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String lawyer_name;
                amount = inputClientPayment.getText().toString();
                lawyer_name = spinnerPayLawyer.getSelectedItem().toString();
                for(String key: hmLawyer.keySet()) {
                    if(hmLawyer.get(key).equals(lawyer_name)) {
                        lawyer_id = key;
                    }
                }
                if(validateForm(amount)){
                    proccessPayment();
                }
            }
        });
    }

    private void proccessPayment() {
        amount = inputClientPayment.getText().toString();
        PayPalPayment payPalPayment = new PayPalPayment(new BigDecimal(String.valueOf(amount)),"PHP",
                "Payment for "+spinnerPayLawyer.getSelectedItem().toString(), PayPalPayment.PAYMENT_INTENT_SALE);
        Intent intent = new Intent(getActivity(), PaymentActivity.class);
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION,config);
        intent.putExtra(PaymentActivity.EXTRA_PAYMENT,payPalPayment);
        startActivityForResult(intent,PAYPAL_REQUEST_CODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode,resultCode,data);
        if(requestCode == PAYPAL_REQUEST_CODE){
            if(resultCode == RESULT_OK){
                PaymentConfirmation confirmation = data.getParcelableExtra(PaymentActivity.EXTRA_RESULT_CONFIRMATION);
                if(confirmation != null){
                    try{
                        String paymentDetails = confirmation.toJSONObject().toString(4);

                        try {
                            JSONObject jsonObject = new JSONObject(paymentDetails);
                            showDetails(jsonObject.getJSONObject("response"),amount);
                        } catch (JSONException e){
                            e.printStackTrace();
                        }

                    } catch(JSONException e){
                        e.printStackTrace();
                    }
                }
            } else if(resultCode == Activity.RESULT_CANCELED){
                Toast.makeText(getActivity(), "Cancel", Toast.LENGTH_SHORT).show();
            }
        } else if( resultCode == PaymentActivity.RESULT_EXTRAS_INVALID){
            Toast.makeText(getActivity(), "Invalid", Toast.LENGTH_SHORT).show();
        }
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
                if( isAdded() && !listLawyer.isError()){
                    ArrayList<ClientListCase> list_lawyers = response.body().getList_lawyers();
                    String lawyer_id,name;
                    for(int i=0; i < list_lawyers.size(); i++){
                        lawyer_id = list_lawyers.get(i).getLawyer_id();
                        name = list_lawyers.get(i).getLawyer().getFirst_name()+" "+list_lawyers.get(i).getLawyer().getLast_name();
                        spinnerLawyerArray.add(name);
                        hmLawyer.put(lawyer_id,name);
                    }
                }else{
                    loading.setVisibility(View.GONE);
                    if(isAdded()) {
                        Toast.makeText(getActivity(), listLawyer.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }
            @Override
            public void onFailure(Call<ListLawyer> call, Throwable t) {
                Toast.makeText(getActivity(), "Unable to list lawyers, please try again. " + t.getMessage() , Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void addPayment(String lawyer_id,String paymentId,String amount){
        String method = "paypal";
        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(Case.BASE_URL)
                .build();
        Case service = retrofit.create(Case.class);
        Call<CommonResponse> commonResponseCall = service.clientPayment(client_id,new ClientPaymentModel(lawyer_id,paymentId,method,amount));
        commonResponseCall.enqueue(new Callback<CommonResponse>() {
            @Override
            public void onResponse(Call<CommonResponse> call, Response<CommonResponse> response) {
                CommonResponse resp = response.body();
                if(response.isSuccessful() && !resp.isError()){
                    Toast.makeText(getActivity(), "Payment success", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(getActivity(), "Unsuccessful payment, please try again.", Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<CommonResponse> call, Throwable t) {
                Toast.makeText(getActivity(), "Unable to pay, please try again.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void showDetails(JSONObject response, final String paymentAmount) {
        try {
            Log.d(TAG,"Transaction ID:" + response.getString("id"));
            Log.d(TAG,"amount:"+paymentAmount);
            paymentId = response.getString("id");
            AlertDialog.Builder ab = new AlertDialog.Builder(getActivity());
            ab.setTitle("Payment Successful!");
            ab.setMessage("Your lawyer will be delighted to received your payment !");
            ab.setPositiveButton("Okay", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    addPayment(lawyer_id,paymentId,paymentAmount);
                }
            });
            ab.show();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private boolean validateForm(String amount) {
        boolean valid = true;
        if (TextUtils.isEmpty(amount)) {
            layoutClientPayment.setError("Required");
            layoutClientPayment.requestFocus();
            valid = false;
        } else {
            layoutClientPayment.setError(null);
        }

        return valid;
    }

}
