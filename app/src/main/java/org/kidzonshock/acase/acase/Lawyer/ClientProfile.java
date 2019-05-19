package org.kidzonshock.acase.acase.Lawyer;

import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.provider.Telephony;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.maps.GoogleMap;

import org.kidzonshock.acase.acase.Client.LawyerProfile;
import org.kidzonshock.acase.acase.Models.PreferenceDataClient;
import org.kidzonshock.acase.acase.Models.PreferenceDataLawyer;
import org.kidzonshock.acase.acase.R;

public class ClientProfile extends AppCompatActivity {

    TextView txtName, txtEmail, txtPhone;
    Button btnCallClient,btnMessageClient;
    ImageView profile_pic;
    String client_id,lawyer_id,name,email,phone,office,prof_url,fid;

    private final AlphaAnimation btnClick = new AlphaAnimation(1F,0.8F);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_profile);

        assert getSupportActionBar() != null;
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Client Information");

        lawyer_id = PreferenceDataLawyer.getLoggedInLawyerid(ClientProfile.this);

        txtName = findViewById(R.id.clientName);
        txtEmail = findViewById(R.id.clientEmail);
        profile_pic = findViewById(R.id.clientProfile);

        btnCallClient = findViewById(R.id.btnCallClient);
        btnMessageClient = findViewById(R.id.btnMessageClient);

        Intent prev = getIntent();
        client_id = prev.getStringExtra("client_id");
        name = prev.getStringExtra("name");
        email = prev.getStringExtra("email");
        phone = prev.getStringExtra("phone");
        prof_url = prev.getStringExtra("profile_pic");

        txtName.setText(name);
        txtEmail.setText(email);

        RequestOptions options = new RequestOptions()
                .circleCrop()
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true)
                .placeholder(R.drawable.icons8_administrator_male_48_color)
                .error(R.mipmap.ic_launcher_round);

        Glide.with(this).load(prof_url).apply(options).into(profile_pic);

        btnMessageClient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.startAnimation(btnClick);
                sendSMS(phone);
            }
        });

        btnCallClient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.startAnimation(btnClick);
                dialPhoneNumber(phone);
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    public void dialPhoneNumber(String phoneNumber) {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:" + phoneNumber));
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    private void sendSMS(String phone) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) // At least KitKat
        {
            String defaultSmsPackageName = Telephony.Sms.getDefaultSmsPackage(this); // Need to change the build to API 19

//            Intent sendIntent = new Intent(Intent.ACTION_SEND);
            Intent sendIntent = new Intent(Intent.ACTION_SENDTO);
            sendIntent.setData(Uri.parse("smsto:"+phone));
//            sendIntent.setType("text/plain");
//            sendIntent.putExtra(Intent.EXTRA_TEXT, "text");

            if (defaultSmsPackageName != null)// Can be null in case that there is no default, then the user would be able to choose
            // any app that support this intent.
            {
                sendIntent.setPackage(defaultSmsPackageName);
            }
            startActivity(sendIntent);

        }
        else // For early versions, do what worked for you before.
        {
            Intent smsIntent = new Intent(android.content.Intent.ACTION_VIEW);
            smsIntent.setType("vnd.android-dir/mms-sms");
            smsIntent.putExtra("address",phone);
            smsIntent.putExtra("sms_body","");
            startActivity(smsIntent);
        }
    }
}
