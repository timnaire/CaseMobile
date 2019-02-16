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
import org.kidzonshock.acase.acase.Config.Config;
import org.kidzonshock.acase.acase.Lawyer.PaymentDetails;
import org.kidzonshock.acase.acase.R;

import java.math.BigDecimal;

import static android.app.Activity.RESULT_OK;

public class PaymentFragment extends Fragment {

    private static final int PAYPAL_REQUEST_CODE = 123;

    private static PayPalConfiguration config = new PayPalConfiguration()
            .environment(PayPalConfiguration.ENVIRONMENT_SANDBOX)
            .clientId(Config.PAYPAL_CLIENT_ID);

    @Override
    public void onDestroyView() {
        getActivity().stopService(new Intent(getActivity(),PayPalService.class));
        super.onDestroyView();
    }

    TextInputLayout layoutClientPayment;
    TextInputEditText inputClientPayment;

    Button btnClientPayment;
    String amount = "";
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_payment,null);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

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
                "Basic Plan", PayPalPayment.PAYMENT_INTENT_SALE);
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

                        Toast.makeText(getActivity(), "Amount: "+amount, Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getActivity(),PaymentDetails.class);
                        intent.putExtra("PaymentDetails",paymentDetails);
                        intent.putExtra("PaymentAmount", amount);
//                        startActivity(intent);

                        AlertDialog.Builder ab = new AlertDialog.Builder(getActivity());
                        ab.setTitle("Subscription Success");
                        ab.setMessage("Thank you for supporting us! \n You have now 50 more cases to create!");
                        ab.setPositiveButton("Okay", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(getActivity(), "Payment Success!", Toast.LENGTH_SHORT).show();
                            }
                        });
                        ab.show();

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

}
