package org.kidzonshock.acase.acase.Lawyer;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.paypal.android.sdk.payments.PayPalConfiguration;
import com.paypal.android.sdk.payments.PayPalPayment;
import com.paypal.android.sdk.payments.PayPalService;
import com.paypal.android.sdk.payments.PaymentActivity;
import com.paypal.android.sdk.payments.PaymentConfirmation;

import org.json.JSONException;
import org.kidzonshock.acase.acase.Config.Config;
import org.kidzonshock.acase.acase.R;

import java.math.BigDecimal;

import static android.app.Activity.RESULT_OK;

public class SubscriptionFragment extends Fragment {

    private static final int PAYPAL_REQUEST_CODE = 123;

    private static PayPalConfiguration config = new PayPalConfiguration()
            .environment(PayPalConfiguration.ENVIRONMENT_SANDBOX)
            .clientId(Config.PAYPAL_CLIENT_ID);

    TextView txtTitle1, txtPrice1, txtTitle2, txtPrice2;
    Button btnPlan1, btnPlan2;
    String amount;

    private final String TAG = "Subscription";
    @Override
    public void onDestroyView() {
        getActivity().stopService(new Intent(getActivity(),PayPalService.class));
        super.onDestroyView();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_subscription,null);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        txtTitle1 = view.findViewById(R.id.planTitle1);
        txtPrice1 = view.findViewById(R.id.planPrice1);
        btnPlan1 = view.findViewById(R.id.btnPlan1);

        txtTitle2 = view.findViewById(R.id.planTitle2);
        txtPrice2 = view.findViewById(R.id.planPrice2);
        btnPlan2 = view.findViewById(R.id.btnPlan2);

        //start paypal service
        Intent intent = new Intent(getActivity(), PayPalService.class);
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, config);
        getActivity().startService(intent);


        btnPlan1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                proccessPaymentPlan1();
            }
        });

        btnPlan2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                proccessPaymentPlan2();
            }
        });

    }

    private void proccessPaymentPlan1(){
        amount = "1000";
        PayPalPayment payPalPayment = new PayPalPayment(new BigDecimal(String.valueOf(amount)),"PHP",
                "Basic Plan", PayPalPayment.PAYMENT_INTENT_SALE);
        Intent intent = new Intent(getActivity(), PaymentActivity.class);
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION,config);
        intent.putExtra(PaymentActivity.EXTRA_PAYMENT,payPalPayment);
        startActivityForResult(intent,PAYPAL_REQUEST_CODE);
    }

    private void proccessPaymentPlan2(){
        amount = "3000";
        PayPalPayment payPalPayment = new PayPalPayment(new BigDecimal(String.valueOf(amount)),"PHP",
                "Premium Plan", PayPalPayment.PAYMENT_INTENT_SALE);
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
