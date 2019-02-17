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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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
import org.kidzonshock.acase.acase.Models.CommonResponse;
import org.kidzonshock.acase.acase.Models.PaymentModel;
import org.kidzonshock.acase.acase.Models.PreferenceDataClient;
import org.kidzonshock.acase.acase.R;

import java.math.BigDecimal;

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

    Button btnClientPayment;
    String amount = "",client_id,paymentId;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_payment,null);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        client_id = PreferenceDataClient.getLoggedInClientid(getActivity());
        //start paypal service
        Intent intent = new Intent(getActivity(), PayPalService.class);
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, config);
        getActivity().startService(intent);

        layoutClientPayment = view.findViewById(R.id.layoutClientPayment);
        inputClientPayment = view.findViewById(R.id.inputClientPayment);
        btnClientPayment = view.findViewById(R.id.btnClientPayment);

        btnClientPayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                proccessPayment();
            }
        });
    }

    private void proccessPayment() {
        amount = inputClientPayment.getText().toString();
        PayPalPayment payPalPayment = new PayPalPayment(new BigDecimal(String.valueOf(amount)),"PHP",
                "Payment for ", PayPalPayment.PAYMENT_INTENT_SALE);
        Intent intent = new Intent(getActivity(), PaymentActivity.class);
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION,config);
        intent.putExtra(PaymentActivity.EXTRA_PAYMENT,payPalPayment);
        startActivityForResult(intent,PAYPAL_REQUEST_CODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode,resultCode,data);
        if(requestCode == PAYPAL_REQUEST_CODE){
            if(resultCode == RESULT_OK){
                PaymentConfirmation confirmation = data.getParcelableExtra(PaymentActivity.EXTRA_RESULT_CONFIRMATION);
                if(confirmation != null){
                    try{
                        String paymentDetails = confirmation.toJSONObject().toString(4);

                        try {
                            JSONObject jsonObject = new JSONObject(paymentDetails);
                            paymentId = jsonObject.getJSONObject("response").getString("id");
                            addPayment(paymentId,amount);
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

    private void addPayment(String paymentId,String amount){
        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(Case.BASE_URL)
                .build();
        Case service = retrofit.create(Case.class);
        Call<CommonResponse> commonResponseCall = service.clientPayment(client_id,new PaymentModel(paymentId,"paypal",amount));
        commonResponseCall.enqueue(new Callback<CommonResponse>() {
            @Override
            public void onResponse(Call<CommonResponse> call, Response<CommonResponse> response) {
                CommonResponse resp = response.body();
                if(isAdded() && !resp.isError()){
                    Toast.makeText(getActivity(), resp.getMessage(), Toast.LENGTH_SHORT).show();
                }else{
                    if(isAdded()){
                        Toast.makeText(getActivity(), resp.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<CommonResponse> call, Throwable t) {
                Toast.makeText(getActivity(), "Unable to pay, please try again.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void showDetails(JSONObject response, String paymentAmount) {
        try {
            Log.d(TAG,"Transaction ID:" + response.getString("id"));
            Log.d(TAG,"amount:"+paymentAmount);
            AlertDialog.Builder ab = new AlertDialog.Builder(getActivity());
            ab.setTitle("Subscription Success");
            ab.setMessage("Thank you for supporting us! \n You can now create more cases !");
            ab.setPositiveButton("Okay", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Toast.makeText(getActivity(), "Payment Success!", Toast.LENGTH_SHORT).show();
                }
            });
            ab.show();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}
